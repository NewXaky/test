package newex.content;

import arc.Core;
import arc.Events;
import arc.audio.Sound;
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
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Call;
import mindustry.gen.LegsUnit;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.entities.units.WeaponMount;
import mindustry.world.Tile;
import newex.PerfCore;

import java.util.ArrayList;
import java.util.List;

/** Chuyển từ elorix.js. Đơn vị Elorix: shotgun đa nguyên tố, lướt (dash), giáp mỏng, tiến cấp bằng cách hấp thụ Copper/Titanium. */
public final class Elorix {
    private Elorix() {}

    private static Effect acidCorrosionEffect;
    private static BasicBulletType[] bullets;
    private static TextureRegion wing1Region, wing2Region, thinArmorRegion;
    private static Sound minalSound;
    private static Weapon elorixWeapon;

    private static BasicBulletType mk(float speed, float damage, float width, float height, String front, String back,
                                      float trailW, int trailLen, float splashDmg, float splashRad, float knock, float statusDur,
                                      Effect fx, StatusEffect status) {
        BasicBulletType b = new BasicBulletType();
        b.speed = speed; b.damage = damage; b.width = width; b.height = height; b.lifetime = 38f;
        b.frontColor = Color.valueOf(front);
        b.backColor = Color.valueOf(back);
        b.trailColor = Color.valueOf(back);
        b.trailWidth = trailW; b.trailLength = trailLen;
        b.splashDamage = splashDmg; b.splashDamageRadius = splashRad;
        b.knockback = knock; b.statusDuration = statusDur;
        b.hitEffect = fx; b.despawnEffect = fx; b.status = status;
        return b;
    }

    public static void init() {
        Events.on(ClientLoadEvent.class, e -> setup());
    }

    private static void setup() {
        acidCorrosionEffect = new Effect(30f, e -> {
            Draw.color(PerfCore.color("#a3e635"), PerfCore.color("#65a30d"), e.fin());
            Lines.stroke(e.fout() * 2f);
            Mathf.rand.setSeed(e.id);
            for (int i = 0; i < 5; i++) {
                float len = Mathf.rand.random(2f, 14f) * e.fin();
                float angle = Mathf.rand.random(360f);
                float size = Mathf.rand.random(1f, 4f) * e.fout();
                Lines.circle(e.x + Angles.trnsx(angle, len), e.y + Angles.trnsy(angle, len), size);
            }
        });

        bullets = new BasicBulletType[]{
            mk(8.5f, 15f, 7f, 18f, "#ffab40", "#ff6d00", 1.5f, 5, 12f, 24f, 1.2f, 140f, Fx.blastExplosion, StatusEffects.blasted),
            mk(8.5f, 15f, 7f, 18f, "#ffa726", "#f57c00", 1.5f, 5, 12f, 24f, 0.5f, 140f, Fx.melting, StatusEffects.melting),
            mk(8.5f, 15f, 7f, 18f, "#ff7043", "#d84315", 1.5f, 5, 12f, 24f, 0.5f, 140f, Fx.fire, StatusEffects.burning),
            mk(8.5f, 15f, 7f, 18f, "#29b6f6", "#0288d1", 1.5f, 5, 12f, 24f, 0.5f, 140f, Fx.freezing, StatusEffects.freezing),
            mk(8.5f, 15f, 7f, 18f, "#e1bee7", "#ba68c8", 1.5f, 5, 12f, 24f, 0.5f, 140f, Fx.lightning, StatusEffects.shocked),
            mk(8.7f, 13f, 6.5f, 17f, "#60a5fa", "#2563eb", 1.4f, 5, 10f, 24f, 0.6f, 180f, Fx.freezing, StatusEffects.wet),
            mk(8.3f, 16f, 7f, 17f, "#bef264", "#65a30d", 1.5f, 6, 14f, 26f, 0.3f, 160f, acidCorrosionEffect, StatusEffects.corroded)
        };

        wing1Region = PerfCore.region("newex-elorix-wing1", "elorix-wing1");
        wing2Region = PerfCore.region("newex-elorix-wing2", "elorix-wing2");
        thinArmorRegion = PerfCore.region("newex-thin-armor", "thin-armor");

        UnitType unit = Vars.content.getByName(ContentType.unit, "newex-elorix");
        if (unit == null) unit = Vars.content.getByName(ContentType.unit, "elorix");
        if (unit == null) return;

        minalSound = Vars.tree.loadSound("minal");

        BasicBulletType none = new BasicBulletType() {
            @Override public void draw(mindustry.gen.Bullet b) {}
        };
        none.speed = 0f; none.damage = 0f; none.lifetime = 0f; none.width = 0f; none.height = 0f;
        none.shootEffect = Fx.none; none.smokeEffect = Fx.none; none.hitEffect = Fx.none; none.despawnEffect = Fx.none;

        elorixWeapon = new Weapon("elorix-weapon") {
            @Override public void draw(mindustry.gen.Unit u, WeaponMount mount) {}
            @Override public void drawOutline(mindustry.gen.Unit u, WeaponMount mount) {}
        };
        elorixWeapon.reload = 180f;
        elorixWeapon.x = 0f;
        elorixWeapon.y = 0f;
        elorixWeapon.shootSound = Sounds.none;
        elorixWeapon.rotate = true;
        elorixWeapon.mirror = false;
        elorixWeapon.top = false;
        elorixWeapon.bullet = none;

        unit.weapons.clear();
        unit.weapons.add(elorixWeapon);
        unit.constructor = ElorixUnit::new;
    }

    // ------------------------------------------------------------------ tiện ích

    /** {x, y, hitWall(0/1)} */
    private static float[] findSafeDashTarget(mindustry.gen.Unit unit, float sx, float sy, float tx, float ty) {
        float angle = Angles.angle(sx, sy, tx, ty);
        float maxDist = Mathf.dst(sx, sy, tx, ty);
        float safePadding = (unit != null ? unit.hitSize / 2f : 12f) + 10f;
        float step = 4f, traveled = 0f;

        while (traveled < maxDist) {
            float nx = sx + Angles.trnsx(angle, traveled + step);
            float ny = sy + Angles.trnsy(angle, traveled + step);
            Tile tile = Vars.world.tileWorld(nx, ny);
            if (tile != null && (tile.solid() || !tile.passable())) {
                return new float[]{nx - Angles.trnsx(angle, safePadding), ny - Angles.trnsy(angle, safePadding), 1f};
            }
            traveled += step;
        }
        return new float[]{tx, ty, 0f};
    }

    private static void drawStar4C(float x, float y, float radius, float rotation) {
        for (int i = 0; i < 4; i++) {
            float angle = rotation + i * 90f;
            float x1 = x + Angles.trnsx(angle, radius), y1 = y + Angles.trnsy(angle, radius);
            Lines.line(x1, y1, x + Angles.trnsx(angle + 45f, radius * 0.3f), y + Angles.trnsy(angle + 45f, radius * 0.3f));
            Lines.line(x1, y1, x + Angles.trnsx(angle - 45f, radius * 0.3f), y + Angles.trnsy(angle - 45f, radius * 0.3f));
        }
    }

    // ------------------------------------------------------------------ đơn vị

    public static class ElorixUnit extends LegsUnit {
        int level = 0;
        final int maxLevel = 10;
        int copperAbsorbed = 0, titaniumAbsorbed = 0;
        float customReloadTimer = 0f;
        int burstQueue = 0;
        float burstTimer = 0f;
        float dashCooldown = 0f, maxCooldownRecord = 360f, dashDelayTimer = 0f;
        float dashTargetX = 0f, dashTargetY = 0f;
        float thinArmorTimer = 0f, armorRecoil = 0f;
        final List<Float> starSpeeds = new ArrayList<>();

        private void fireShotgunBurst(WeaponMount mount) {
            float statMultiplier = 1f + level * 0.05f;
            float wx = x + Angles.trnsx(rotation, mount.weapon.x, mount.weapon.y);
            float wy = y + Angles.trnsy(rotation, mount.weapon.x, mount.weapon.y);
            float baseAngle = rotation + mount.rotation;

            armorRecoil = 6f;
            if (minalSound != null) Call.soundAt(minalSound, wx, wy, 1f, 1f);

            for (int i = 0; i < 35; i++) {
                float spread = (float) (Math.random() - 0.5) * 12f;
                BasicBulletType sel = bullets[(int) Math.floor(Math.random() * bullets.length)];
                Call.createBullet(sel, team, wx, wy, baseAngle + spread, sel.damage * statMultiplier,
                    1f + (float) (Math.random() - 0.5) * 0.2f, 1f);
            }
        }

        @Override
        public void update() {
            super.update();

            if (Vars.net.client()) return;

            if (Vars.player == null || Vars.player.unit() != this) {
                float shootRange = 8.5f * 38f;
                mindustry.gen.Teamc target = Units.closestTarget(team, x, y, shootRange, u -> u.checkTarget(true, true), b -> true);
                if (target != null) {
                    rotation = Mathf.slerpDelta(rotation, angleTo(target), 0.15f);
                    isShooting = true;
                } else {
                    isShooting = false;
                }
            }

            float speedMultiplier = 1f + level * 0.05f;
            if (customReloadTimer > 0f) customReloadTimer -= speedMultiplier * Time.delta;

            if (isShooting && customReloadTimer <= 0f && burstQueue == 0) {
                burstQueue = 2;
                burstTimer = 0f;
                customReloadTimer = elorixWeapon.reload;
            }

            if (burstQueue > 0 && mounts != null && mounts.length > 0) {
                WeaponMount m = mounts[0];
                if (m != null) {
                    if (burstTimer <= 0f) {
                        fireShotgunBurst(m);
                        burstQueue--;
                        burstTimer = 8f;
                    } else {
                        burstTimer -= Time.delta;
                    }
                }
            }

            if (level > 0 && vel.len() > 0.01f) vel.scl(1f + 0.05f * Time.delta);

            if (dashCooldown > 0f) dashCooldown -= Time.delta;
            if (thinArmorTimer > 0f) thinArmorTimer -= Time.delta;
            if (armorRecoil > 0f) armorRecoil = Mathf.lerpDelta(armorRecoil, 0f, 0.15f);

            if (vel.len() > 0.05f && dashCooldown <= 0f && dashDelayTimer <= 0f) {
                float dashDistance = 15f * 8f;
                float moveAngle = vel.angle();
                float[] safe = findSafeDashTarget(this, x, y,
                    x + Angles.trnsx(moveAngle, dashDistance), y + Angles.trnsy(moveAngle, dashDistance));
                float actual = Mathf.dst(x, y, safe[0], safe[1]);

                if (actual > hitSize / 2f + 6f) {
                    dashDelayTimer = 42f;
                    dashTargetX = safe[0];
                    dashTargetY = safe[1];
                    Call.effect(Fx.shieldApply, x, y, 0f, Color.sky);

                    float baseCooldown = 360f * (1f - level * 0.05f);
                    maxCooldownRecord = baseCooldown;
                    dashCooldown = baseCooldown;
                }
            }

            if (dashDelayTimer > 0f) {
                dashDelayTimer -= Time.delta;
                vel.set(0f, 0f);

                if (dashDelayTimer <= 0f) {
                    set(dashTargetX, dashTargetY);
                    Call.effect(Fx.spawnShockwave, x, y, 0f, Color.white);

                    if (Sta.deot != null) apply(Sta.deot, 300f);
                    thinArmorTimer = 300f;
                    Sta.deotLastHealth.put(id, health);
                }
            }

            // Tiến cấp: hấp thụ Copper & Titanium từ kho cá nhân
            final int copperNeeded = 500, titaniumNeeded = 200;
            if (level < maxLevel && stack != null) {
                if (copperAbsorbed < copperNeeded && stack.item == Items.copper && stack.amount > 0) {
                    int c = Math.min(2, stack.amount);
                    stack.amount -= c;
                    copperAbsorbed += c;
                } else if (titaniumAbsorbed < titaniumNeeded && stack.item == Items.titanium && stack.amount > 0) {
                    int c = Math.min(2, stack.amount);
                    stack.amount -= c;
                    titaniumAbsorbed += c;
                }

                if (copperAbsorbed >= copperNeeded && titaniumAbsorbed >= titaniumNeeded) {
                    copperAbsorbed = 0;
                    titaniumAbsorbed = 0;
                    level++;
                    Call.effect(Fx.upgradeCore, x, y, 0f, Color.white);
                    Call.effect(Fx.shockwave, x, y, 0f, Color.white);
                }
            }
        }

        private float starSpeed(int i) {
            while (starSpeeds.size() <= level) starSpeeds.add(1.2f + (float) Math.random() * 2.3f);
            return i < starSpeeds.size() ? starSpeeds.get(i) : 2f;
        }

        @Override
        public void draw() {
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
            float upward = 8f * dashProgress, side = 14f + 10f * dashProgress;
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

            if (thinArmorTimer > 0f && thinArmorRegion != null && Core.atlas.isFound(thinArmorRegion)) {
                float progress = 1f - thinArmorTimer / 300f;
                float scale = 1f;
                if (progress < 0.1f) scale = progress / 0.1f;
                else if (progress > 0.9f) scale = (1f - progress) / 0.1f;
                float alpha = Math.min(scale, 1f);

                Draw.z(Layer.flyingUnit + 0.01f);
                Draw.color(Color.white, alpha);
                float w = thinArmorRegion.width * Draw.scl * scale * 1.2f;
                float h = thinArmorRegion.height * Draw.scl * scale * 1.2f;
                Draw.rect(thinArmorRegion, x - Angles.trnsx(rotation, armorRecoil), y - Angles.trnsy(rotation, armorRecoil), w, h, rotation);
                Draw.reset();
            }

            super.draw();
        }
    }
}
