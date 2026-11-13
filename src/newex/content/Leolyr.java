package newex.content;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Call;
import mindustry.gen.LegsUnit;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;
import mindustry.world.Tile;
import newex.PerfCore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Chuyển từ leolyr.js. Đơn vị "leolyr": 2 pháo trái/phải tăng sát thương & tốc bắn theo cấp, tiến cấp bằng cách hấp thụ
 * Copper/Silicon (400 mỗi loại mỗi cấp, tối đa cấp 10); lướt (dash) theo 2 cách: AI tự lướt ngẫu nhiên khi di chuyển,
 * người chơi double-tap để lướt (ở cấp 10 double-tap chuyển sang chế độ "MK2": chạm 1 lần để đánh dấu điểm đến bất kỳ
 * trên màn hình, chạm lần 2 để lướt tới đó). Mỗi lần lướt tạo khiên tròn quanh thân + để lại 1 "khiên tĩnh" tại vị trí
 * cũ, cả hai chặn đạn địch trong bán kính, trừ máu khiên theo sát thương, có cơ hội phản đạn lại theo cấp.
 *
 * Ghi chú chuyển đổi: JS bản gốc dùng unit.weapons.get(i).shoot(this) để tự bắn tay và đồng bộ lướt của người chơi qua
 * lệnh chat client->server (/leolyr_dash x y). Mindustry-Java (bản này) không có API shoot() public cho Weapon và cả
 * project không dùng kênh lệnh chat riêng ở bất kỳ file nào khác (xem Elorix.java, Cin.java...), nên để nhất quán:
 * tốc bắn tăng theo cấp được áp dụng bằng cách trừ thêm vào WeaponMount.reload (giống Cin.java đọc/ghi mounts[i].reload),
 * để engine tự bắn khi reload <= 0 thay vì gọi tay; lướt chỉ chạy ở phía chủ (!Vars.net.client()), giống hệt quy ước
 * trong Elorix.java/Cin.java/BalashilonBlock.java cho các cơ chế ngẫu nhiên không nên chạy trùng ở client.
 */
public final class Leolyr {
    private Leolyr() {}

    private static final int MAX_LEVEL = 10;
    private static final int COPPER_NEEDED = 400;
    private static final int SILICON_NEEDED = 400;
    private static final long DOUBLE_TAP_MS = 250;

    private static BasicBulletType leftBullet, rightBullet;
    private static TextureRegion wing1Region, wing2Region;
    private static StatusEffect atkSpeedStatus;

    public static void init() {
        Events.on(ClientLoadEvent.class, e -> setup());
    }

    private static void setup() {
        leftBullet = new BasicBulletType();
        leftBullet.speed = 9f; leftBullet.damage = 15f; leftBullet.width = 6f; leftBullet.height = 11f; leftBullet.lifetime = 35f;

        rightBullet = new BasicBulletType();
        rightBullet.speed = 9f; rightBullet.damage = 65f; rightBullet.width = 6f; rightBullet.height = 11f; rightBullet.lifetime = 35f;

        wing1Region = PerfCore.region("newex-leolyr-wing1", "leolyr-wing1");
        wing2Region = PerfCore.region("newex-leolyr-wing2", "leolyr-wing2");

        atkSpeedStatus = Vars.content.getByName(ContentType.status, "newex-atkspeed");
        if (atkSpeedStatus == null) atkSpeedStatus = Vars.content.getByName(ContentType.status, "atkspeed");

        if (!Vars.headless && Vars.ui != null && Vars.ui.settings != null) {
            SettingsTable g = Vars.ui.settings.game;
            g.checkPref("leolyr-hex-shield", true, v -> {});
        }

        UnitType unit = Vars.content.getByName(ContentType.unit, "newex-leolyr");
        if (unit == null) unit = Vars.content.getByName(ContentType.unit, "leolyr");
        if (unit == null) return;

        if (unit.weapons.size >= 2) {
            unit.weapons.get(0).bullet = leftBullet;
            unit.weapons.get(1).bullet = rightBullet;
        }

        unit.constructor = LeolyrUnit::new;
    }

    // ------------------------------------------------------------------ tiện ích dùng chung

    /** Dò đường thẳng tới đích, lùi lại trước vật cản rắn gần nhất (bước 4 ô, lùi thêm 6px khi đụng tường). */
    private static float[] findSafeDashTarget(float sx, float sy, float tx, float ty) {
        float dist = Mathf.dst(sx, sy, tx, ty);
        float angle = Angles.angle(sx, sy, tx, ty);

        Tile endTile = Vars.world.tileWorld(tx, ty);
        boolean endIsWall = endTile != null && endTile.solid() && endTile.build == null;
        if (!endIsWall) return new float[]{tx, ty};

        float step = 4f, traveled = 0f;
        while (traveled < dist) {
            traveled += step;
            if (traveled > dist) traveled = dist;
            float cx = sx + Angles.trnsx(angle, traveled);
            float cy = sy + Angles.trnsy(angle, traveled);
            Tile tile = Vars.world.tileWorld(cx, cy);
            boolean isWall = tile != null && tile.solid() && tile.build == null;
            if (isWall) {
                float safe = Math.max(0f, traveled - step - 6f);
                return new float[]{sx + Angles.trnsx(angle, safe), sy + Angles.trnsy(angle, safe)};
            }
        }
        return new float[]{tx, ty};
    }

    private static void drawStar4C(float x, float y, float radius, float rotation) {
        for (int i = 0; i < 4; i++) {
            float angle = rotation + i * 90f;
            float x1 = x + Angles.trnsx(angle, radius), y1 = y + Angles.trnsy(angle, radius);
            float x2 = x + Angles.trnsx(angle + 45f, radius * 0.3f), y2 = y + Angles.trnsy(angle + 45f, radius * 0.3f);
            Lines.line(x1, y1, x2, y2);
            float x3 = x + Angles.trnsx(angle - 45f, radius * 0.3f), y3 = y + Angles.trnsy(angle - 45f, radius * 0.3f);
            Lines.line(x1, y1, x3, y3);
        }
    }

    /** Vẽ khiên dạng lưới lục giác lấp đầy hình tròn tâm (cx,cy) bán kính rad, alpha giảm dần ra biên. */
    private static void drawHexShield(float cx, float cy, float rad, float hexSize, float baseAlpha) {
        float hSpacing = hexSize * 1.5f;
        float vSpacing = hexSize * Mathf.sqrt3;
        int minX = (int) Math.floor((cx - rad) / hSpacing) - 1;
        int maxX = (int) Math.ceil((cx + rad) / hSpacing) + 1;
        int minY = (int) Math.floor((cy - rad) / vSpacing) - 1;
        int maxY = (int) Math.ceil((cy + rad) / vSpacing) + 1;

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                float checkX = i * hSpacing;
                float checkY = j * vSpacing + ((i % 2 == 0) ? 0f : vSpacing / 2f);
                float d = Mathf.dst(cx, cy, checkX, checkY);
                if (Math.floor(d) < rad - 1) {
                    float edgeFade = 1f - (d / rad);
                    Draw.color(Color.sky, baseAlpha * edgeFade);
                    Lines.stroke(0.75f);
                    Lines.poly(checkX, checkY, 6, hexSize);
                }
            }
        }
    }

    /** Một "khiên tĩnh" để lại tại vị trí cũ sau khi lướt. */
    private static final class ShieldBubble {
        float x, y, rad, hp, lifetime;
        ShieldBubble(float x, float y, float rad, float hp, float lifetime) {
            this.x = x; this.y = y; this.rad = rad; this.hp = hp; this.lifetime = lifetime;
        }
    }

    // ------------------------------------------------------------------ đơn vị

    public static class LeolyrUnit extends LegsUnit {
        int level = 0;
        int copperAbsorbed = 0, siliconAbsorbed = 0;
        int shotParity = 0;
        float dashCooldown = 0f, maxCooldownRecord = 300f;
        float shieldHealth = 0f;
        final float hexSize = 5.5f;
        boolean ignoreNextDamage = false;
        final List<ShieldBubble> staticShields = new ArrayList<>();
        final List<Float> starSpeeds = new ArrayList<>();

        long lastTapTimeMs = 0;
        boolean wasTouchedPrev = false;
        boolean isMarkedMk2 = false;
        float mk2TargetX = 0f, mk2TargetY = 0f;

        private float shieldRadius() {
            return (7f * 8f) * (1f + level * 0.10f);
        }

        private void doDash(float targetX, float targetY) {
            if (dashCooldown > 0f) return;

            float currentMaxShield = 100f + level * 250f;
            float currentShieldRadius = shieldRadius();
            float cooldownReduction = 1f - level * 0.05f;
            float maxDashCooldown = 300f * cooldownReduction;

            float[] safe = findSafeDashTarget(x, y, targetX, targetY);
            float oldX = x, oldY = y;

            set(safe[0], safe[1]);
            Call.effect(Fx.spawnShockwave, x, y, 0f, Color.white);

            maxCooldownRecord = maxDashCooldown;
            dashCooldown = maxDashCooldown;
            shieldHealth = currentMaxShield;

            if (atkSpeedStatus != null) apply(atkSpeedStatus, 300f);

            staticShields.add(new ShieldBubble(oldX, oldY, currentShieldRadius, currentMaxShield * 1.5f, 600f));
        }

        @Override
        public void damage(float amount) {
            if (ignoreNextDamage) {
                ignoreNextDamage = false;
                return;
            }
            super.damage(amount);
        }

        @Override
        public void update() {
            super.update();

            if (dashCooldown > 0f) dashCooldown -= Time.delta;

            if (!Vars.net.client()) {
                float currentShieldRadius = shieldRadius();

                if (shieldHealth > 0f) {
                    float rad = currentShieldRadius;
                    mindustry.gen.Groups.bullet.intersect(x - rad - 16f, y - rad - 16f, (rad + 16f) * 2f, (rad + 16f) * 2f, b -> {
                        if (b.team != team && b.type != null && b.type.damage > 0f) {
                            float dst = Mathf.dst(x, y, b.x, b.y);
                            if (dst <= rad + 4f) {
                                shieldHealth -= b.damage;
                                ignoreNextDamage = true;
                                Fx.shieldBreak.at(b.x, b.y, 5f, Color.sky);
                                if (Mathf.chance(level * 0.01f)) {
                                    b.team = team;
                                    b.vel.rotate(180f);
                                } else {
                                    b.remove();
                                }
                                if (shieldHealth <= 0f) {
                                    shieldHealth = 0f;
                                    Fx.shieldBreak.at(x, y, currentShieldRadius, Color.sky);
                                }
                            }
                        }
                    });
                }
                ignoreNextDamage = false;

                if (!staticShields.isEmpty()) {
                    Iterator<ShieldBubble> it = staticShields.iterator();
                    while (it.hasNext()) {
                        ShieldBubble s = it.next();
                        s.lifetime -= Time.delta;
                        if (s.hp > 0f && s.lifetime > 0f) {
                            mindustry.gen.Groups.bullet.intersect(s.x - s.rad - 16f, s.y - s.rad - 16f, (s.rad + 16f) * 2f, (s.rad + 16f) * 2f, b -> {
                                if (b.team != team && b.type != null && b.type.damage > 0f) {
                                    float dst = Mathf.dst(s.x, s.y, b.x, b.y);
                                    if (dst <= s.rad + 4f) {
                                        s.hp -= b.damage;
                                        Fx.shieldBreak.at(b.x, b.y, 5f, Color.sky);
                                        if (Mathf.chance(level * 0.01f)) {
                                            b.team = team;
                                            b.vel.rotate(180f);
                                        } else {
                                            b.remove();
                                        }
                                    }
                                }
                            });
                        }
                        if (s.lifetime <= 0f || s.hp <= 0f) it.remove();
                    }
                }

                if (level < MAX_LEVEL && stack != null) {
                    if (copperAbsorbed < COPPER_NEEDED && stack.item == Items.copper && stack.amount > 0) {
                        int c = Math.min(2, stack.amount);
                        stack.amount -= c;
                        copperAbsorbed += c;
                    } else if (siliconAbsorbed < SILICON_NEEDED && stack.item == Items.silicon && stack.amount > 0) {
                        int c = Math.min(2, stack.amount);
                        stack.amount -= c;
                        siliconAbsorbed += c;
                    }
                    if (copperAbsorbed >= COPPER_NEEDED && siliconAbsorbed >= SILICON_NEEDED) {
                        copperAbsorbed = 0;
                        siliconAbsorbed = 0;
                        level++;
                        Call.effect(Fx.upgradeCore, x, y, 0f, Color.white);
                        Call.effect(Fx.shockwave, x, y, 0f, Color.white);
                    }
                }

                if ((Vars.player == null || Vars.player.unit() != this) && vel.len() > 0.1f && dashCooldown <= 0f && Mathf.chance(0.02)) {
                    float dashDistance = 80f + level * 40f;
                    float angle = vel.angle();
                    doDash(x + Angles.trnsx(angle, dashDistance), y + Angles.trnsy(angle, dashDistance));
                }
            }

            if (Vars.player != null && Vars.player.unit() == this) {
                boolean touchedNow = Core.input.isTouched();
                if (touchedNow && !wasTouchedPrev) {
                    long now = Time.millis();
                    if (now - lastTapTimeMs < DOUBLE_TAP_MS) {
                        if (level >= MAX_LEVEL) {
                            if (!isMarkedMk2 && dashCooldown <= 0f) {
                                mk2TargetX = Vars.player.mouseX;
                                mk2TargetY = Vars.player.mouseY;
                                isMarkedMk2 = true;
                                Fx.shieldBreak.at(mk2TargetX, mk2TargetY, hexSize * 2f, Color.sky);
                                Fx.shieldApply.at(mk2TargetX, mk2TargetY, 0f, Color.sky);
                            } else if (isMarkedMk2 && dashCooldown <= 0f) {
                                doDash(mk2TargetX, mk2TargetY);
                                isMarkedMk2 = false;
                            }
                        } else if (dashCooldown <= 0f) {
                            float dashDistance = 80f + level * 40f;
                            float angle = vel.len() > 0.1f ? vel.angle() : rotation;
                            doDash(x + Angles.trnsx(angle, dashDistance), y + Angles.trnsy(angle, dashDistance));
                        }
                    }
                    lastTapTimeMs = now;
                }
                wasTouchedPrev = touchedNow;
            }

            // tốc bắn & sát thương theo cấp: engine tự bắn khi mount.reload <= 0 (xem ghi chú đầu file)
            float reloadBoost = (1f + level * 2f - 1f) * Time.delta;
            float damageMultiplier = 1f + level * 0.20f;
            leftBullet.damage = 15f * damageMultiplier;
            rightBullet.damage = 65f * damageMultiplier;

            if (!Vars.net.client() && isShooting && mounts != null && mounts.length >= 2) {
                WeaponMount left = mounts[0], right = mounts[1];
                if (shotParity == 0 && left.reload > 0f) left.reload -= reloadBoost;
                else if (right.reload > 0f) right.reload -= reloadBoost;
                if (left.reload <= 0f && shotParity == 0) shotParity = 1;
                else if (right.reload <= 0f && shotParity == 1) shotParity = 0;
            }
        }

        private float starSpeed(int i) {
            while (starSpeeds.size() <= level) starSpeeds.add(1.2f + (float) Math.random() * 2.3f);
            return starSpeeds.get(Math.min(i, starSpeeds.size() - 1));
        }

        @Override
        public void draw() {
            boolean showHex = Core.settings.getBool("leolyr-hex-shield", true);

            Draw.z(Layer.flyingUnit - 2f);
            Lines.stroke(1.2f);
            int totalStars = level + 1;
            float baseRadius = hitSize * 0.65f;
            for (int i = 0; i < totalStars; i++) {
                float pulse = ((float) Math.sin(Time.time / 5f + i) + 1f) / 2f;
                Draw.color(Color.white.cpy().lerp(Color.blue, pulse));
                float speed = starSpeed(i);
                int dir = (i % 2 == 0) ? 1 : -1;
                float orbitAngle = Time.time * speed * dir + i * (360f / totalStars);
                drawStar4C(x + Angles.trnsx(orbitAngle, baseRadius + i * 2f), y + Angles.trnsy(orbitAngle, baseRadius + i * 2f),
                    1.5f, Time.time * (speed * 1.8f) * dir);
            }

            float dashProgress = 0f;
            boolean retracting = false;
            if (dashCooldown > 0f) {
                float activeFrames = maxCooldownRecord - dashCooldown;
                if (activeFrames < 40f) dashProgress = 1f;
                else { dashProgress = dashCooldown / maxCooldownRecord; retracting = true; }
            }

            Draw.z(Layer.flyingUnit - 0.001f);
            Draw.color();
            float upward = 8f * dashProgress;
            float side = 14f + 10f * dashProgress;
            float w1X = x, w1Y = y, w2X = x, w2Y = y;
            if (wing1Region != null && wing2Region != null) {
                w1X = x + Angles.trnsx(rotation + 90f, side) + Angles.trnsx(rotation, upward);
                w1Y = y + Angles.trnsy(rotation + 90f, side) + Angles.trnsy(rotation, upward);
                w2X = x + Angles.trnsx(rotation - 90f, side) + Angles.trnsx(rotation, upward);
                w2Y = y + Angles.trnsy(rotation - 90f, side) + Angles.trnsy(rotation, upward);
                Draw.rect(wing1Region, w1X, w1Y, rotation);
                Draw.rect(wing2Region, w2X, w2Y, rotation);
            }

            if (retracting) {
                Draw.z(Layer.flyingUnit - 0.002f);
                float pulse = ((float) Math.sin(Time.time / 1.5f) + 1f) / 2f;
                float ox = x + Angles.trnsx(rotation, -4f), oy = y + Angles.trnsy(rotation, -4f);
                float f = 0.45f;
                float t1x = Mathf.lerp(ox, w1X, f), t1y = Mathf.lerp(oy, w1Y, f);
                float t2x = Mathf.lerp(ox, w2X, f), t2y = Mathf.lerp(oy, w2Y, f);
                Lines.stroke(1.4f + pulse * 0.8f); Draw.color(Color.sky);
                Lines.line(ox, oy, t1x, t1y); Lines.line(ox, oy, t2x, t2y);
                Lines.stroke(0.4f + pulse * 0.3f); Draw.color(Color.white);
                Lines.line(ox, oy, t1x, t1y); Lines.line(ox, oy, t2x, t2y);
                float ball = 1.5f + pulse;
                Draw.color(Color.sky); Fill.circle(ox, oy, ball + 0.8f);
                Draw.color(Color.white); Fill.circle(ox, oy, ball * 0.6f);
                Draw.color();
            }

            if (isMarkedMk2 && Vars.player != null && Vars.player.unit() == this && level >= MAX_LEVEL) {
                Draw.z(Layer.effect);
                Draw.color(Color.sky, 0.4f + Mathf.absin(Time.time, 3f, 0.2f));
                Lines.stroke(1f);
                Lines.poly(mk2TargetX, mk2TargetY, 6, hexSize * 1.8f, Time.time * 2f);
                Lines.circle(mk2TargetX, mk2TargetY, 4f);
                Draw.reset();
            }

            super.draw();

            float dynamicRadius = shieldRadius();
            if (shieldHealth > 0f) {
                Draw.z(Layer.effect);
                if (showHex) {
                    float alpha = 0.28f + Mathf.absin(Time.time, 4f, 0.08f);
                    drawHexShield(x, y, dynamicRadius, hexSize, alpha);
                }
                Draw.color(Color.sky, 0.75f); Lines.stroke(1.3f);
                Lines.circle(x, y, dynamicRadius);
                Draw.reset();
            }

            for (ShieldBubble s : staticShields) {
                if (s.hp > 0f && s.lifetime > 0f) {
                    Draw.z(Layer.effect);
                    if (showHex) drawHexShield(s.x, s.y, s.rad, hexSize, 0.22f);
                    Draw.color(Color.sky, 0.60f); Lines.stroke(1.1f);
                    Lines.circle(s.x, s.y, s.rad);
                    Draw.reset();
                }
            }
        }
    }
}
