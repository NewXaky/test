package newex;

import arc.Core;
import arc.Events;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.gl.FrameBuffer;
import arc.graphics.gl.Shader;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.Trigger;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;

/**
 * Cơ chế lỗ đen (lấy ý tưởng từ WarHammer, MainRenderer.endDistortion).
 *
 * Cách hoạt động mỗi khung hình:
 *  1) Layer.floor - 8: bắt đầu chụp toàn cảnh vào FrameBuffer.
 *  2) Layer.effect + 0.03 (CORE_Z): dừng chụp, vặn méo ảnh quanh từng lỗ đen bằng shaders/blackhole.frag,
 *     vẽ ảnh đã méo lên màn hình, rồi vẽ LÕI ĐEN ĐẶC lên trên cùng.
 *
 * Vì lõi đen được vẽ SAU và ở layer CAO HƠN mọi thứ vẽ bên dưới (đạn, hiệu ứng, glow trắng...), nó đè lên chúng và
 * biến vùng đó thành đen tuyền. Muốn glow vẫn hiện trên lõi, vẽ glow ở layer > CORE_Z.
 *
 * Dùng: NexBlackHole.add(x, y, bánKínhLõi, bánKínhNgoài, alpha, độMạnh) MỖI khung hình trong draw() của đạn/block.
 */
public final class NexBlackHole {
    private NexBlackHole() {}

    /** Số lỗ đen tối đa cùng lúc (khớp MAX_COUNT trong blackhole.frag). */
    public static final int MAX = 16;
    /** Layer vẽ lõi đen: mọi thứ có layer thấp hơn sẽ bị lõi này che. */
    public static final float CORE_Z = Layer.effect + 0.03f;
    /** Lõi đen vẽ rộng hơn bán kính lõi của shader bao nhiêu lần (WarHammer dùng 1.5). */
    public static float coreScale = 1.5f;

    private static final Blending premultiplied = new Blending(Gl.one, Gl.oneMinusSrcAlpha, Gl.one, Gl.oneMinusSrcAlpha);

    private static final float[] hx = new float[MAX], hy = new float[MAX], hin = new float[MAX],
        hout = new float[MAX], halpha = new float[MAX], hstr = new float[MAX];
    private static final float[] posUniform = new float[MAX * 4], strUniform = new float[MAX * 4];
    private static int count = 0;

    private static FrameBuffer buffer, buffer2;
    private static HoleShader shader;
    private static boolean capturing;

    public static void init() {
        if (Vars.headless) return;
        Events.on(ClientLoadEvent.class, e -> load());
        Events.run(Trigger.draw, NexBlackHole::queue);
    }

    private static void load() {
        try {
            buffer = new FrameBuffer();
            buffer2 = new FrameBuffer();
            shader = new HoleShader();
        } catch (Throwable t) {
            shader = null;
            Log.err("NexBlackHole: không nạp được shader lỗ đen (cần shaders/blackhole.frag).", t);
        }
    }

    /** Thêm 1 lỗ đen cho khung hình hiện tại (gọi mỗi frame trong draw). */
    public static void add(float x, float y, float inRadius, float outRadius, float alpha, float strength) {
        if (Vars.headless || shader == null || count >= MAX || inRadius > outRadius || outRadius <= 0f) return;
        hx[count] = x; hy[count] = y; hin[count] = inRadius; hout[count] = outRadius;
        halpha[count] = alpha; hstr[count] = Math.max(strength, 0.0001f);
        count++;
    }

    public static void add(float x, float y, float inRadius, float outRadius, float alpha) {
        add(x, y, inRadius, outRadius, alpha, 1f);
    }

    // ------------------------------------------------------------------ vẽ

    private static void queue() {
        if (shader == null || Core.settings.getBool("pixelate")) {
            count = 0;   // pixelate: không hỗ trợ chụp ảnh, bỏ lỗ đen
            return;
        }
        Draw.draw(Layer.floor - 8f, NexBlackHole::begin);
        Draw.draw(CORE_Z, NexBlackHole::end);
    }

    private static void begin() {
        capturing = false;
        if (count == 0) return;
        buffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
        if (!buffer.isBound()) {
            buffer.begin(Color.clear);
            capturing = true;
        }
    }

    private static void end() {
        if (!capturing) { count = 0; return; }
        capturing = false;
        buffer.end();

        buffer2.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
        buffer2.begin(Color.clear);
        shader.fill();
        blit(buffer, shader);        // ảnh đã vặn méo -> buffer2
        buffer2.end();
        blit(buffer2, Shaders.screenspace);   // buffer2 -> màn hình

        // LÕI ĐEN: vẽ sau cùng, ở CORE_Z, đè lên mọi thứ bên dưới (kể cả glow trắng).
        for (int i = 0; i < count; i++) {
            Draw.color(Tmp.c2.set(Color.black).a(halpha[i]));
            Fill.circle(hx[i], hy[i], hin[i] * coreScale);
        }
        Draw.color();
        count = 0;
    }

    /** FrameBuffer đã lưu màu nhân sẵn alpha nên phải blit bằng (one, 1 - srcAlpha), nếu không glow bị tối đi. */
    private static void blit(FrameBuffer source, Shader s) {
        Draw.flush();
        premultiplied.apply();
        try {
            source.blit(s);
        } finally {
            Blending.normal.apply();
        }
    }

    private static final class HoleShader extends Shader {
        int n;

        HoleShader() {
            super(Core.files.internal("shaders/screenspace.vert"), Vars.tree.get("shaders/blackhole.frag"));
        }

        void fill() {
            n = count;
            for (int i = 0; i < n; i++) {
                posUniform[i * 4] = hx[i];
                posUniform[i * 4 + 1] = hy[i];
                posUniform[i * 4 + 2] = hin[i];
                posUniform[i * 4 + 3] = hout[i];
                strUniform[i * 4] = hstr[i];
            }
        }

        @Override
        public void apply() {
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2f, Core.camera.position.y - Core.camera.height / 2f);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformi("u_blackholecount", n);
            setUniform4fv("u_blackholes", posUniform, 0, n * 4);
            setUniform4fv("u_blackholeStrengths", strUniform, 0, n * 4);
        }
    }

    // ------------------------------------------------------------------ đạn lỗ đen dùng sẵn

    /**
     * Đạn đứng yên tại chỗ, hiện lỗ đen, hút kẻ địch vào và gây sát thương trong vùng lõi.
     * Dùng thử: ammoTypes.put(Items.xxx, new NexBlackHole.BlackHoleBulletType(24f, 90f)); rồi chỉnh damage/lifetime.
     */
    public static class BlackHoleBulletType extends BulletType {
        public float inRad, outRad;
        public float fadeTime = 72f;
        public float pullForce = 0.35f;
        public float damageInterval = 12f;

        public BlackHoleBulletType(float inRad, float outRad) {
            this.inRad = inRad;
            this.outRad = outRad;
            speed = 0f;
            damage = 10f;
            lifetime = 300f;
            keepVelocity = false;
            collides = collidesGround = collidesAir = false;
            absorbable = hittable = false;
            despawnEffect = hitEffect = Fx.none;
        }

        private float grow(Bullet b) {
            float in = b.time <= b.lifetime - fadeTime ? Math.min(b.time / 60f, 1f) : (b.lifetime - b.time) / fadeTime;
            return Interp.fastSlow.apply(Mathf.clamp(in));
        }

        @Override
        public void draw(Bullet b) {
            float in = grow(b);
            NexBlackHole.add(b.x, b.y, inRad * 0.5f * in, outRad * 1.3f * in, Math.min(1f, in + 0.1f), 1f);
            super.draw(b);
        }

        @Override
        public void drawLight(Bullet b) {
            // không phát sáng
        }

        @Override
        public void update(Bullet b) {
            super.update(b);
            final float in = grow(b);

            Units.nearbyEnemies(b.team, b.x, b.y, outRad, u -> {
                if (u == null || !u.targetable(b.team)) return;
                float d = Math.max(u.dst(b), 1f);
                float f = (1f - Math.min(d / outRad, 1f)) * pullForce * in * Time.delta;
                u.vel.add(Tmp.v1.set(b.x - u.x, b.y - u.y).nor().scl(f));
                u.vel.limit(5f);
            });

            if (damage > 0f && b.timer(3, damageInterval)) {
                Units.nearbyEnemies(b.team, b.x, b.y, inRad, u -> {
                    if (u != null && u.targetable(b.team)) u.damage(damage * b.damageMultiplier());
                });
            }
        }
    }
}
