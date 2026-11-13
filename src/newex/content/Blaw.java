package newex.content;

import arc.Core;
import arc.Events;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Interp;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType.ContentInitEvent;
import mindustry.gen.Call;
import mindustry.gen.Icon;
import mindustry.graphics.Layer;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;
import newex.NexNet;
import newex.PerfCore;

/**
 * Chuyển từ blaw.js. Pháo Blaw: 2 nòng (trái/phải) bắn luân phiên đạn Băng (đóng băng, xuyên) / Hỏa (nổ lan),
 * khuếch đại sát thương theo HP mục tiêu + bạo kích 80%/220%, nâng cấp MK2 (tầm xa hơn, sát thương nổ lan tăng,
 * +20% tốc nạp) hoặc MK2B (máu +150%, tầm ngắn, bắn chùm 20 viên theo đợt 2x10 kiểu súng hoa cải, có hồi chiêu
 * sau mỗi 2 đợt).
 */
public final class Blaw {
    private Blaw() {}

    private static final int[] REQ_MK2 = {4000, 4000, 0};   // copper, lead, titanium
    private static final int[] REQ_MK2B = {4000, 4000, 2000};

    private static Effect hitFx;
    private static BasicBulletType blueMk1, redMk1, blueMk2, redMk2, blueMk2b, redMk2b;
    private static Sound shootSoundCannon;
    private static arc.graphics.g2d.TextureRegion leftBarrelRegion, rightBarrelRegion, customBaseRegion;
    private static ItemTurret base;

    private static boolean en() {
        return Core.settings.getString("locale", "").startsWith("en");
    }

    private static String t(String enText, String viText) {
        return en() ? enText : viText;
    }

    public static void init() {
        hitFx = new Effect(40f, e -> {
            Draw.color(e.color);
            float r = 35f * Interp.pow2Out.apply(e.fin());
            Draw.alpha(0.35f * e.fout());
            Fill.circle(e.x, e.y, r);
            arc.graphics.g2d.Lines.stroke(2.5f * e.fout());
            arc.graphics.g2d.Lines.circle(e.x, e.y, 40f * Interp.pow2Out.apply(e.fin()));
            arc.graphics.g2d.Lines.stroke(1.5f * e.fout());
            arc.graphics.g2d.Lines.circle(e.x, e.y, 20f * Interp.pow2Out.apply(e.fin()));
            Draw.reset();
        });

        blueMk1 = mk(3.25f, 25f, 9f, 22f, "0031FFFF", 0f, 0f, mindustry.content.StatusEffects.freezing, 600f, true, 3);
        redMk1 = mk(3.25f, 25f, 9f, 22f, "FF0000FF", 40f, 24f, mindustry.content.StatusEffects.blasted, 80f, false, 0);
        blueMk2 = mk(3.64f, 50f, 10f, 24f, "0031FFFF", 0f, 0f, mindustry.content.StatusEffects.freezing, 600f, true, 3);
        redMk2 = mk(3.64f, 50f, 10f, 24f, "FF0000FF", 120f, 32f, mindustry.content.StatusEffects.blasted, 80f, false, 0);
        blueMk2b = mk(15.86f, 550f, 5f, 12f, "0031FFFF", 0f, 0f, mindustry.content.StatusEffects.freezing, 300f, true, 2);
        redMk2b = mk(15.86f, 550f, 5f, 12f, "FF5A00FF", 40f, 16f, mindustry.content.StatusEffects.blasted, 80f, false, 0);

        Events.on(ContentInitEvent.class, e -> setup());
    }

    private static BasicBulletType mk(float speed, float damage, float width, float height, String frontHex,
                                       float splashDmg, float splashRad, mindustry.type.StatusEffect status, float statusDur,
                                       boolean pierce, int pierceCap) {
        BasicBulletType b = new BasicBulletType();
        b.buildingDamageMultiplier = 0.01f;
        b.speed = speed; b.damage = damage; b.width = width; b.height = height; b.lifetime = 70f;
        b.frontColor = Color.valueOf(frontHex); b.backColor = Color.white;
        b.hitEffect = hitFx; b.despawnEffect = hitFx; b.hitColor = Color.valueOf(frontHex);
        if (splashDmg > 0f) { b.splashDamage = splashDmg; b.splashDamageRadius = splashRad; }
        b.status = status; b.statusDuration = statusDur;
        b.pierce = pierce; b.pierceCap = pierceCap;
        return b;
    }

    private static void setup() {
        shootSoundCannon = Vars.tree.loadSound("cannon-shot-1");
        leftBarrelRegion = PerfCore.region("newex-blaw-at", "blaw-at");
        rightBarrelRegion = PerfCore.region("newex-blaw-ap", "blaw-ap");
        customBaseRegion = PerfCore.region("newex-reinforced-block-3", "reinforced-block-3");

        Block blk = Vars.content.getByName(ContentType.block, "newex-blaw");
        if (blk == null) blk = Vars.content.getByName(ContentType.block, "blaw");
        if (!(blk instanceof ItemTurret)) return;
        base = (ItemTurret) blk;

        base.configurable = true;
        base.shootSound = shootSoundCannon;
        base.ammoTypes.put(Items.titanium, blueMk1);
        base.config(Integer.class, (BlawBuild tile, Integer value) -> {
            if (tile != null) tile.setTier(value);
        });
        base.buildType = () -> new BlawBuild(base);
    }

    public static class BlawBuild extends ItemTurret.ItemTurretBuild {
        int tierState = 0;
        boolean barrelSide = false;
        float shotgunTimer = 0f;
        int shotCount = 0;
        float recoilLeft = 0f, recoilRight = 0f;

        public BlawBuild(ItemTurret outer) {
            outer.super();
        }

        int getTier() { return tierState; }

        void setTier(int val) {
            tierState = val;
            health = (val == 2) ? 1425f : 950f;
            maxHealth = health;
            shotCount = 0;
        }

        @Override
        public float range() {
            int tier = getTier();
            return tier == 1 ? 663f : (tier == 2 ? 309.4f : 442f);
        }

        @Override
        public void updateTile() {
            super.updateTile();
            int tier = getTier();

            if (tier == 2 && shotgunTimer > 0f) shotgunTimer -= Time.delta;

            if (recoilLeft > 0f) recoilLeft = Math.max(0f, recoilLeft - Time.delta * 0.1f);
            if (recoilRight > 0f) recoilRight = Math.max(0f, recoilRight - Time.delta * 0.1f);

            if (isShooting() && hasAmmo()) {
                float randomBoost = Mathf.random(0f, 2f);
                reloadCounter += Time.delta * randomBoost * efficiency;
                if (tier == 1) reloadCounter += Time.delta * 0.2f * efficiency;
            }
        }

        @Override
        public void shoot(BulletType type) {
            int tier = getTier();
            if (tier == 2 && shotgunTimer > 0f) return;

            float damageMultiplier = 1f;
            float targetHp = target instanceof mindustry.gen.Healthc ? ((mindustry.gen.Healthc) target).health() : 0f;
            if (target != null && targetHp > 100f) {
                float factor = tier == 2 ? 0.05f : (tier == 1 ? 0.02f : 0.01f);
                damageMultiplier += Mathf.floor((targetHp - 100f) / 100f) * factor;
            }
            if (tier == 1 && target != null && targetHp > 1000f) {
                damageMultiplier += Mathf.floor((targetHp - 1000f) / 1000f) * 0.01f;
            }

            if (Mathf.chance(0.80)) damageMultiplier *= 2.20f;

            barrelSide = !barrelSide;
            if (barrelSide) recoilLeft = 1f; else recoilRight = 1f;

            float angleRad = (rotation + (barrelSide ? 90f : -90f)) * Mathf.degRad;
            float spawnX = x + Mathf.cos(angleRad) * 6f;
            float spawnY = y + Mathf.sin(angleRad) * 6f;

            if (tier == 2) {
                float dmg = 550f * damageMultiplier;
                BasicBulletType sel = barrelSide ? blueMk2b : redMk2b;
                if (shootSoundCannon != null) shootSoundCannon.at(x, y, Mathf.random(0.9f, 1.1f));

                for (int i = 0; i < 10; i++) {
                    float spd = Mathf.random(1.5f, 3.5f);
                    float spread = Mathf.range(15f);
                    Call.createBullet(sel, team, spawnX, spawnY, rotation + spread, dmg, spd, 1f);
                }

                shotCount++;
                if (shotCount >= 2) { shotgunTimer = 180f; shotCount = 0; }
            } else {
                BasicBulletType sel = tier == 1
                    ? (barrelSide ? blueMk2 : redMk2)
                    : (barrelSide ? blueMk1 : redMk1);
                float dmg = sel.damage * damageMultiplier;
                Call.createBullet(sel, team, spawnX, spawnY, rotation, dmg, sel.speed, 1f);
                if (shootSoundCannon != null) shootSoundCannon.at(x, y, Mathf.random(0.9f, 1.1f));
            }

            useAmmo();
        }

        @Override
        public void draw() {
            if (customBaseRegion != null && customBaseRegion.found()) {
                Draw.rect(customBaseRegion, x, y);
            } else {
                Draw.rect(base.baseRegion, x, y);
            }

            float angleRad = rotation * Mathf.degRad;
            float cos = Mathf.cos(angleRad), sin = Mathf.sin(angleRad);
            float recoilLen = 4f;

            float rxLeft = x - cos * (recoilLeft * recoilLen), ryLeft = y - sin * (recoilLeft * recoilLen);
            float rxRight = x - cos * (recoilRight * recoilLen), ryRight = y - sin * (recoilRight * recoilLen);

            if (leftBarrelRegion != null && leftBarrelRegion.found()) Draw.rect(leftBarrelRegion, rxLeft, ryLeft, rotation - 90f);
            if (rightBarrelRegion != null && rightBarrelRegion.found()) Draw.rect(rightBarrelRegion, rxRight, ryRight, rotation - 90f);
            if (base.region != null && base.region.found()) Draw.rect(base.region, x, y, rotation - 90f);

            float sideRad = (rotation + 90f) * Mathf.degRad;
            float sideCos = Mathf.cos(sideRad) * 6f, sideSin = Mathf.sin(sideRad) * 6f;

            float bxLeft = (x - cos * (recoilLeft * recoilLen)) + sideCos - cos * 3f;
            float byLeft = (y - sin * (recoilLeft * recoilLen)) + sideSin - sin * 3f;
            float bxRight = (x - cos * (recoilRight * recoilLen)) - sideCos - cos * 3f;
            float byRight = (y - sin * (recoilRight * recoilLen)) - sideSin - sin * 3f;

            Draw.z(Layer.effect + 1f);
            float zoomLeft = 1.5f * (1f + recoilLeft * 1.5f);
            Draw.color(PerfCore.color("0031FFFF"), 0.35f);
            Fill.circle(bxLeft, byLeft, zoomLeft * 2f);
            Draw.color(Color.white);
            Fill.circle(bxLeft, byLeft, zoomLeft);

            float zoomRight = 1.5f * (1f + recoilRight * 1.5f);
            Draw.color(PerfCore.color("FF3B00FF"), 0.35f);
            Fill.circle(bxRight, byRight, zoomRight * 2f);
            Draw.color(Color.white);
            Fill.circle(bxRight, byRight, zoomRight);
            Draw.reset();
        }

        @Override
        public Object config() {
            return getTier();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.b(getTier());
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            setTier(read.b());
        }

        // ------------------------------------------------------------- giao diện nâng cấp

        private static String col(int have, int need) {
            return have >= need ? "[green]" : "[red]";
        }

        private String requirementText() {
            CoreBuild core = team.core();
            if (core == null) return t("[red]Team Core not found![]", "[red]Không tìm thấy Lõi Đội![]");
            int cu = core.items.get(Items.copper), le = core.items.get(Items.lead), ti = core.items.get(Items.titanium);
            return t("[yellow]CORE VAULT RESOURCE REQUIREMENTS:[]\n", "[yellow]YÊU CẦU TÀI NGUYÊN KHO LÕI:[]\n")
                + t("[cyan]MK2 Configuration Path[]\n", "[cyan]Nhánh Cấu Hình MK2[]\n")
                + t(" • Copper: ", " • Đồng: ") + col(cu, REQ_MK2[0]) + cu + "[] / " + REQ_MK2[0] + "\n"
                + t(" • Lead: ", " • Chì: ") + col(le, REQ_MK2[1]) + le + "[] / " + REQ_MK2[1] + "\n"
                + t("[purple]MK2B Variant Path[]\n", "[purple]Nhánh Biến Thể MK2B[]\n")
                + t(" • Copper: ", " • Đồng: ") + col(cu, REQ_MK2B[0]) + cu + "[] / " + REQ_MK2B[0] + "\n"
                + t(" • Lead: ", " • Chì: ") + col(le, REQ_MK2B[1]) + le + "[] / " + REQ_MK2B[1] + "\n"
                + t(" • Titanium: ", " • Titan: ") + col(ti, REQ_MK2B[2]) + ti + "[] / " + REQ_MK2B[2];
        }

        private boolean canPay(CoreBuild core, int[] req) {
            return core != null && core.items.get(Items.copper) >= req[0] && core.items.get(Items.lead) >= req[1]
                && core.items.get(Items.titanium) >= req[2];
        }

        private void pay(CoreBuild core, int[] req) {
            NexNet.take(core, Items.copper, req[0]);
            NexNet.take(core, Items.lead, req[1]);
            if (req[2] > 0) NexNet.take(core, Items.titanium, req[2]);
        }

        private Table branch(String title, String desc, String btnText, int[] req, int target, BaseDialog dialog) {
            Table b = new Table();
            b.background(Styles.black6);
            b.margin(12);
            b.add(title).row();
            b.add(desc).width(340).get().setWrap(true);
            ((arc.scene.ui.Label) b.getCells().peek().get()).setAlignment(Align.left);
            b.row();
            b.button(btnText, () -> {
                CoreBuild core = team.core();
                if (canPay(core, req)) {
                    pay(core, req);
                    NexNet.fx(target == 1 ? Fx.upgradeCore : Fx.bigShockwave, x, y);
                    NexNet.fx(Fx.mineHuge, x, y);
                    NexNet.shake(5f, 5f, x, y);
                    NexNet.configure(this, Integer.valueOf(target));
                    dialog.hide();
                    deselect();
                } else {
                    Vars.ui.showInfo(target == 1
                        ? t("[red]Not enough resources for MK2 path![]", "[red]Không đủ tài nguyên cho nhánh MK2![]")
                        : t("[red]Not enough resources for MK2B path![]", "[red]Không đủ tài nguyên cho nhánh MK2B![]"));
                }
            }).size(180, 38);
            return b;
        }

        private void openUpgradeDialog() {
            BaseDialog dialog = new BaseDialog(t("Turret Upgrade Center", "Trung tâm nâng cấp pháo"));
            dialog.cont.add(requirementText()).width(360).get().setWrap(true);
            ((arc.scene.ui.Label) dialog.cont.getCells().peek().get()).setAlignment(Align.left);
            dialog.cont.row();
            dialog.cont.add().height(10).row();

            Table branches = new Table();
            Table b1 = branch("[cyan]===(MK2)===[]",
                t("[white]• Range: [green]663 px[]\n• Fire barrel splash DMG: [green]+50%[]\n• Reload speed: [green]+20%[]\n"
                    + "• Critical: [green]80% Rate | +120% Crit DMG[]\n• [orange]Enhanced Frostfire:[] Ice barrel freezes and pierces 3 targets\n"
                    + "• Fire barrel deals 120 splash DMG\n• Greatly amplifies DMG based on target HP",
                  "[white]• Tầm bắn: [green]663 px[]\n• Sát thương nổ nòng Hỏa: [green]+50%[]\n• Tốc độ nạp đạn: [green]+20%[]\n"
                    + "• Bạo kích: [green]80% Tỉ lệ | +120% ST Bạo kích[]\n• [orange]Cải Tiến Băng Hỏa:[] Nòng Băng đóng băng và xuyên 3 mục tiêu\n"
                    + "• Nòng Hỏa gây nổ lan 120 DMG\n• Tăng mạnh khả năng khuếch đại sát thương theo HP đối thủ"),
                t("[green]ACTIVATE MK2[]", "[green]KÍCH HOẠT MK2[]"), REQ_MK2, 1, dialog);

            Table b2 = branch("[purple]===(MK2B)===[]",
                t("[white]• Health: [green]+150%[] (1,425 HP)\n• Range: [orange]309.4 px[]\n• Base Damage: [green]550 DMG[] / bullet\n"
                    + "• Critical: [green]80% Rate | +120% Crit DMG[]\n• [orange]Shotgun Melee:[] Fires a spread burst of 20 ultimate Frostfire bullets\n"
                    + "• Optimized for close-range Boss killing with Burst-Cooldown mechanics",
                  "[white]• Máu cấu trúc: [green]+150%[] (1,425 HP)\n• Tầm bắn: [orange]309.4 px[]\n• Sát thương gốc: [green]550 DMG[] / viên\n"
                    + "• Bạo kích: [green]80% Tỉ lệ | +120% ST Bạo kích[]\n• [orange]Cận Chiến Shotgun:[] Bắn tỏa chùm 20 viên đạn hỗn hợp Băng Hỏa cực đại\n"
                    + "• Tối ưu diệt Boss tầm gần kèm cơ chế Xả-Hồi"),
                t("[orange]ACTIVATE MK2B[]", "[orange]KÍCH HOẠT MK2B[]"), REQ_MK2B, 2, dialog);

            branches.add(b1).width(340).row();
            branches.add().height(12).row();
            branches.add(b2).width(340);

            ScrollPane scroll = new ScrollPane(branches);
            scroll.setScrollingDisabled(true, false);
            dialog.cont.add(scroll).maxHeight(400);
            dialog.addCloseButton();
            dialog.show();
        }

        private String statsText(int tier) {
            if (tier == 0) {
                return t("[gold]⚡ BASE SPECS (MK1) ⚡[]\n[lightgray]Structure Health:[] [green]950 HP[]\n"
                    + "[lightgray]Effective Range:[] [orange]442 pixels[]\n[lightgray]Base Damage:[] [white]25 DMG / bullet[]\n"
                    + "[lightgray]Crit Chance:[] [yellow]80%[]\n[lightgray]Crit Damage:[] [orange]220% (120% bonus)[]\n\n"
                    + "[cyan]⚡ SPECIAL SKILL MECHANICS:[]\n• Dual Frostfire Barrels: Alternates firing left barrel (Freezing + Pierce 3) and right barrel (80 Splash DMG).\n"
                    + "• Damage Amplification: Adds +1% DMG for every 100 target HP when HP > 100.\n• Random firing speed pulse from 0% to 200%.",
                  "[gold]⚡ THÔNG SỐ CƠ BẢN (MK1) ⚡[]\n[lightgray]Máu cấu trúc:[] [green]950 HP[]\n"
                    + "[lightgray]Tầm bắn hiệu dụng:[] [orange]442 pixel[]\n[lightgray]Sát thương gốc:[] [white]25 DMG / viên[]\n"
                    + "[lightgray]Tỉ lệ bạo kích:[] [yellow]80%[]\n[lightgray]Sát thương bạo kích:[] [orange]220% (120% bonus)[]\n\n"
                    + "[cyan]⚡ CƠ CHẾ KỸ NĂNG ĐẶC BIỆT:[]\n• Băng Hỏa Song Nòng: Luân phiên bắn nòng trái (Đóng băng + Xuyên 3 mục tiêu) và nòng phải (Nổ lan 80 DMG).\n"
                    + "• Khuếch đại sát thương: Tăng +1% DMG cho mỗi 100 HP của mục tiêu khi HP > 100.\n• Xung nhịp tốc độ bắn ngẫu nhiên từ 0% đến 200%.");
            } else if (tier == 1) {
                return t("[cyan]⚡ UPGRADE SPECS MK2 ⚡[]\n[lightgray]Structure Health:[] [green]950 HP[]\n"
                    + "[lightgray]Effective Range:[] [orange]663 pixels[]\n[lightgray]Base Damage:[] [white]50 DMG / bullet[]\n"
                    + "[lightgray]Crit Chance:[] [yellow]80%[]\n[lightgray]Crit Damage:[] [orange]220% (120% bonus)[]\n\n"
                    + "[cyan]⚡ SPECIAL SKILL MECHANICS:[]\n• Enhanced Frostfire: Fire barrel splash DMG increased to 120 DMG / 32px.\n"
                    + "• Boss Executer Damage: Adds +2% DMG per 100 target HP, plus +1% DMG per 1,000 HP when HP > 1,000.\n"
                    + "• Flat +20% reload speed combined with random pulse.",
                  "[cyan]⚡ THÔNG SỐ NÂNG CẤP MK2 ⚡[]\n[lightgray]Máu cấu trúc:[] [green]950 HP[]\n"
                    + "[lightgray]Tầm bắn hiệu dụng:[] [orange]663 pixel[]\n[lightgray]Sát thương gốc:[] [white]50 DMG / viên[]\n"
                    + "[lightgray]Tỉ lệ bạo kích:[] [yellow]80%[]\n[lightgray]Sát thương bạo kích:[] [orange]220% (120% bonus)[]\n\n"
                    + "[cyan]⚡ CƠ CHẾ KỸ NĂNG ĐẶC BIỆT:[]\n• Băng Hỏa Tăng Cường: Nổ lan nòng Hỏa tăng lên 120 DMG / 32px.\n"
                    + "• Sát thương Trảm Tướng: Tăng +2% DMG cho mỗi 100 HP mục tiêu, cộng thêm +1% DMG mỗi 1,000 HP khi HP > 1,000.\n"
                    + "• Tăng cố định +20% tốc độ nạp đạn kết hợp xung nhịp ngẫu nhiên.");
            } else {
                return t("[purple]⚡ UPGRADE SPECS MK2B ⚡[]\n[lightgray]Structure Health:[] [green]1,425 HP (+150%)[]\n"
                    + "[lightgray]Effective Range:[] [red]309.4 pixels[]\n[lightgray]Base Damage:[] [white]550 DMG / bullet[]\n"
                    + "[lightgray]Crit Chance:[] [yellow]80%[]\n[lightgray]Crit Damage:[] [orange]220% (120% bonus)[]\n\n"
                    + "[purple]🔥 SPECIAL SKILL MECHANICS:[]\n• Shotgun Melee: Fires a burst of 20 mixed Frostfire bullets (2 waves x 10 bullets).\n"
                    + "• Boss Killer Damage: Adds +5% DMG for every 100 target HP when HP > 100.\n• Burst-Cooldown: Enters a 3-second cooldown after every 2 burst salvos.",
                  "[purple]⚡ THÔNG SỐ NÂNG CẤP MK2B ⚡[]\n[lightgray]Máu cấu trúc:[] [green]1,425 HP (+150%)[]\n"
                    + "[lightgray]Tầm bắn hiệu dụng:[] [red]309.4 pixel[]\n[lightgray]Sát thương gốc:[] [white]550 DMG / viên[]\n"
                    + "[lightgray]Tỉ lệ bạo kích:[] [yellow]80%[]\n[lightgray]Sát thương bạo kích:[] [orange]220% (120% bonus)[]\n\n"
                    + "[purple]🔥 CƠ CHẾ KỸ NĂNG ĐẶC BIỆT:[]\n• Cận chiến Shotgun: Bắn tỏa chùm 20 viên đạn hỗn hợp Băng Hỏa (2 đợt x 10 viên).\n"
                    + "• Sát thương Diệt Boss: Tăng +5% DMG cho mỗi 100 HP của mục tiêu khi HP > 100.\n"
                    + "• Cơ chế Xả-Hồi: Đi vào thời gian hồi chiêu 3 giây sau mỗi 2 đợt xả chùm đạn.");
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            table.clear();
            table.row();
            int tier = getTier();

            if (tier == 0) {
                table.button(Icon.upOpen, Styles.cleari, 40, this::openUpgradeDialog)
                    .size(50, 40).tooltip(t("Upgrade turret", "Nâng cấp tháp pháo lên"));
            } else {
                int ft = tier;
                table.button(Icon.lock, Styles.cleari, 40, () -> Vars.ui.showInfo(ft == 1
                    ? t("[cyan]CURRENTLY ACTIVE IN BLAW MK2 CONFIG![]", "[cyan]ĐANG HOẠT ĐỘNG Ở CẤU HÌNH BLAW MK2![]")
                    : t("[purple]CURRENTLY ACTIVE IN BLAW MK2B CONFIG![]", "[purple]ĐANG HOẠT ĐỘNG Ở CẤU HÌNH BLAW MK2B![]")))
                    .size(50, 40).tooltip(t("Upgrade turret", "Nâng cấp tháp pháo"));
            }

            table.button(Icon.info, Styles.cleari, 40, () -> {
                String title = t(" Turret Specs \"Blaw\": ", " Thông số pháo \"Blaw\": ")
                    + (tier == 0 ? "[yellow](MK1)[]" : tier == 1 ? t("[cyan]UPGRADE SPECS MK2[]", "[cyan]THÔNG SỐ NÂNG CẤP MK2[]")
                        : t("[purple]UPGRADE SPECS MK2B[]", "[purple]THÔNG SỐ NÂNG CẤP MK2B[]"));
                BaseDialog dialog = new BaseDialog(title);
                Table info = new Table();
                info.add(statsText(tier)).width(360).get().setWrap(true);
                ((arc.scene.ui.Label) info.getCells().peek().get()).setAlignment(Align.left);
                ScrollPane scroll = new ScrollPane(info);
                scroll.setScrollingDisabled(true, false);
                dialog.cont.add(scroll).maxHeight(400);
                dialog.addCloseButton();
                dialog.show();
            }).size(50, 40).tooltip(t("Turret Upgrade Center", "Trung tâm nâng cấp pháo"));
        }
    }
}
