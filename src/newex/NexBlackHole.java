package newex;

import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import newex.graphics.NexGraphics;
import newex.graphics.NexRenderer;

/**
 * Cổng vào đơn giản cho lỗ đen. Toàn bộ pipeline (chụp khung hình, shaders/blackhole.frag, lõi đen đè lên glow trắng)
 * nay nằm trong newex.graphics.NexRenderer (bản port của WarHammer MainRenderer), dùng chung buffer với thấu kính lồi.
 *
 * Dùng: NexBlackHole.add(x, y, bánKínhLõi, bánKínhNgoài, alpha, độMạnh) MỖI khung hình trong draw() của đạn/block.
 */
public final class NexBlackHole {
    private NexBlackHole() {}

    /** Số lỗ đen tối đa cùng lúc. */
    public static final int MAX = 512;
    /** Layer vẽ lõi đen: mọi thứ có layer thấp hơn sẽ bị lõi này che. Muốn glow hiện trên lõi, vẽ glow ở layer > CORE_Z. */
    public static final float CORE_Z = Layer.effect + 0.03f;
    /** Lõi đen vẽ rộng hơn bán kính lõi của shader bao nhiêu lần (WarHammer dùng 1.5). */
    public static float coreScale = 1.5f;

    /** Khởi tạo toàn bộ đồ họa (NexMod gọi hàm này sẵn). */
    public static void init() {
        NexGraphics.init();
    }

    /** Thêm 1 lỗ đen cho khung hình hiện tại (gọi mỗi frame trong draw). */
    public static void add(float x, float y, float inRadius, float outRadius, float alpha, float strength) {
        NexRenderer.addBlackHole(x, y, inRadius, outRadius, alpha, strength);
    }

    public static void add(float x, float y, float inRadius, float outRadius, float alpha) {
        add(x, y, inRadius, outRadius, alpha, 1f);
    }

    public static void add(float x, float y, float inRadius, float outRadius) {
        add(x, y, inRadius, outRadius, 1f, 1f);
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
