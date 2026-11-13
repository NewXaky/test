package newex.content;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.entities.Effect;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import newex.NexNet;

/** Chuyển từ leolyr-summoner.js. Kén triệu hồi Elorix / Leolyr / VUS-27 (đặt sẵn trong content hjson). */
public final class LeolyrSummoner {
    private LeolyrSummoner() {}

    private static Effect orbitalLockOnEffect, satelliteImpactEffect;

    private static boolean en() {
        String l = Core.settings.getString("locale", "");
        return l != null && l.startsWith("en");
    }

    private static String t(String enText, String viText) { return en() ? enText : viText; }

    private static void drawEllipseWave(float cx, float cy, float rx, float ry, float rotDeg) {
        int points = 24;
        float rot = rotDeg * Mathf.degRad;
        float cos = (float) Math.cos(rot), sin = (float) Math.sin(rot);
        float lastX = cx + rx * cos, lastY = cy + rx * sin;
        for (int i = 1; i <= points; i++) {
            float a = (i * 360f / points) * Mathf.degRad;
            float lx = (float) Math.cos(a) * rx, ly = (float) Math.sin(a) * ry;
            float nx = cx + (lx * cos - ly * sin), ny = cy + (lx * sin + ly * cos);
            Lines.line(lastX, lastY, nx, ny);
            lastX = nx; lastY = ny;
        }
    }

    public static void init() {
        final Color purple = Color.valueOf("c084fc");
        final Color pink = Color.valueOf("e879f9");

        orbitalLockOnEffect = new Effect(40f, e -> {
            float progress = e.data instanceof Float ? (Float) e.data : 1f;
            Draw.z(Layer.effect + 2f);
            Color c = purple.cpy().lerp(pink, 1f - progress);

            Lines.stroke(1.2f, c);
            Lines.circle(e.x, e.y, 24f * progress);
            Lines.circle(e.x, e.y, 12f * progress);

            float size = 14f + 16f * progress, len = 6f;
            Lines.stroke(1.6f, c);
            Lines.line(e.x + size, e.y + size, e.x + size - len, e.y + size);
            Lines.line(e.x + size, e.y + size, e.x + size, e.y + size - len);
            Lines.line(e.x - size, e.y + size, e.x - size + len, e.y + size);
            Lines.line(e.x - size, e.y + size, e.x - size, e.y + size - len);
            Lines.line(e.x + size, e.y - size, e.x + size - len, e.y - size);
            Lines.line(e.x + size, e.y - size, e.x + size, e.y - size + len);
            Lines.line(e.x - size, e.y - size, e.x - size + len, e.y - size);
            Lines.line(e.x - size, e.y - size, e.x - size, e.y - size + len);

            Lines.stroke(1f, c);
            Lines.line(e.x - 4f, e.y, e.x + 4f, e.y);
            Lines.line(e.x, e.y - 4f, e.x, e.y + 4f);

            Lines.stroke((0.4f + 0.7f * (1f - progress)) * progress, c);
            Lines.line(e.x - 140f, e.y + 250f, e.x, e.y);
            Draw.reset();
        });

        satelliteImpactEffect = new Effect(60f, e -> {
            Draw.z(Layer.effect + 3f);
            float f = e.fin();
            float alpha = 1f - Interp.pow3Out.apply(f);

            Lines.stroke(18f * alpha, purple);
            Lines.line(e.x - 140f, e.y + 250f, e.x, e.y);
            Lines.stroke(8f * alpha, Color.white);
            Lines.line(e.x - 140f, e.y + 250f, e.x, e.y);

            Lines.stroke(3.5f * (1f - f), pink);
            Lines.circle(e.x, e.y, 8f + 55f * f);

            Draw.color(Color.white, purple, f);
            Rand rand = new Rand(e.id);
            for (int i = 0; i < 18; i++) {
                float angle = rand.random(360f);
                float dist = rand.random(2f, 6f) * f * 10f;
                Fill.circle(e.x + Angles.trnsx(angle, dist), e.y + Angles.trnsy(angle, dist), 2.5f * (1f - f));
            }

            float laserAngle = 33f;
            float mx = -140f * Interp.pow2Out.apply(f), my = 250f * Interp.pow2Out.apply(f);
            float r1 = 4f + 48f * Interp.pow3Out.apply(f);

            Lines.stroke(2.5f * (1f - f), purple.cpy().mul(1f - f));
            drawEllipseWave(e.x + mx, e.y + my, r1, r1 * 0.45f, laserAngle);

            float r2 = r1 * (2f / 3f);
            Lines.stroke(1.8f * (1f - f), pink.cpy().mul(1f - f));
            drawEllipseWave(e.x + mx * 0.7f, e.y + my * 0.7f, r2, r2 * 0.45f, laserAngle);
            Draw.reset();
        });

        Events.on(ClientLoadEvent.class, e -> {
            Block b = Vars.content.getByName(ContentType.block, "newex-leolyr-spawner");
            if (b == null) b = Vars.content.getByName(ContentType.block, "leolyr-spawner");
            if (b == null) return;

            b.configurable = true;
            b.update = true;
            b.hasItems = true;
            b.itemCapacity = 10000;
            b.acceptsItems = true;
            b.buildType = SummonerBuild::new;
        });
    }

    public static class SummonerBuild extends Building {
        boolean summoning = false;
        float summonTimer = 0f;
        String selectedUnit = "newex-elorix";

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.get(item) < block.itemCapacity;
        }

        /** {copper, silicon} */
        int[] getRequirements() {
            if (selectedUnit.contains("elorix")) return new int[]{4000, 500};
            if (selectedUnit.contains("vus") || selectedUnit.contains("suv")) return new int[]{3500, 600};
            return new int[]{2000, 300};
        }

        private void startSummon() {
            summoning = true;
            summonTimer = 300f;
        }

        @Override
        public void buildConfiguration(Table table) {
            table.clear();
            if (summoning) return;
            table.row();

            table.button(Icon.ok, Styles.cleari, 40f, () -> {
                Building core = team.core();
                if (core == null) {
                    Vars.ui.showInfo(t("[scarlet]Core not found![]", "[scarlet]Không tìm thấy nhà chính (Core)![]"));
                    return;
                }
                int[] req = getRequirements();
                if (!core.items.has(Items.copper, req[0]) || !core.items.has(Items.silicon, req[1])) {
                    Vars.ui.showInfo(t(
                        "[scarlet]Not enough resources in Core![]\nRequired: [accent]" + req[0] + " Copper[] and [accent]" + req[1] + " Silicon[] in Core.",
                        "[scarlet]Không đủ tài nguyên trong Lõi![]\nCần có: [accent]" + req[0] + " Copper[] và [accent]" + req[1] + " Silicon[] trong Core."));
                    return;
                }
                NexNet.take(core, Items.copper, req[0]);
                NexNet.take(core, Items.silicon, req[1]);
                startSummon();
                NexNet.fx(Fx.shieldApply, x, y, 0f, purple());
                deselect();
            }).size(50f, 40f).tooltip(t("Manual Summon (Consume Core resources & Wait 5s)", "Triệu hồi thủ công (Rút tài nguyên từ Lõi Core & Chờ 5s)"));

            table.button(Icon.add, Styles.cleari, 40f, this::openSelectDialog)
                .size(50f, 40f).tooltip(t("Select unit & View stats", "Chọn đơn vị triệu hồi & Xem phân tích thông số"));
        }

        private static Color purple() { return Color.valueOf("c084fc"); }

        private void openSelectDialog() {
            BaseDialog dialog = new BaseDialog(t("Summoning Cocoon System", "Hệ Thống Kén Triệu Hồi"));
            dialog.cont.add(t("[yellow]AVAILABLE UNITS FOR SUMMONING:[]", "[yellow]DANH SÁCH ĐƠN VỊ CÓ THỂ TRIỆU HỒI:[]")).row();
            dialog.cont.add().height(10f).row();

            Table card = new Table();
            card.background(Styles.black6);
            card.margin(10f);

            unitEntry(card, dialog,
                t("[orange]⚡ SELECT SUMMON: ELORIX UNIT[]", "[orange]⚡ CHỌN TRIỆU HỒI: ELORIX UNIT[]"), "newex-elorix",
                t("[orange]Target set: Elorix[]", "[orange]Đã cài đặt mục tiêu: Elorix[]"),
                t("[gold]📊 ELORIX ANALYSIS DATA:[]\n• [accent]Summon Cost:[] [white]4000 Copper[] + [white]500 Silicon[]\n• [accent]Leveling:[] Automatically absorbs Copper & Titanium from inventory to upgrade (Max Lv10).\n• [pink]Shotgun Firepower:[] Fires multi-elemental spread shots for high area damage.\n• [sky]Dash & Shield:[] Double tap to dash through obstacles, gaining [Thin Armor] for 5s.",
                  "[gold]📊 DỮ LIỆU PHÂN TÍCH THỰC THỂ ELORIX:[]\n• [accent]Chi phí triệu hồi:[] [white]4000 Copper[] + [white]500 Silicon[]\n• [accent]Tăng tiến cấp độ:[] Tự động hút Copper & Titanium từ kho cá nhân để nâng cấp (Max Lv10).\n• [pink]Hỏa lực Shotgun:[] Bắn chùm đạn đa nguyên tố xả diện rộng cực mạnh.\n• [sky]Lướt & Tạo Giáp:[] Nhấp đúp để lướt dẹp chướng ngại vật, nhận hiệu ứng giáp [Thin Armor] phòng thủ 5s."));
            card.add().height(16f).row();

            unitEntry(card, dialog,
                t("[cyan]🤖 SELECT SUMMON: LEOLYR UNIT[]", "[cyan]🤖 CHỌN TRIỆU HỒI: LEOLYR UNIT[]"), "newex-leolyr",
                t("[cyan]Target set: Leolyr[]", "[cyan]Đã cài đặt mục tiêu: Leolyr[]"),
                t("[gold]📊 LEOLYR ANALYSIS DATA:[]\n• [accent]Summon Cost:[] [white]2000 Copper[] + [white]300 Silicon[]\n• [accent]Evolution System:[] Absorbs Copper and Silicon from inventory (Max Lv10).\n• [pink]Dual Weapon:[] Alternating fire, increasing attack speed with level.\n• [sky]Dash & Shield Core:[] Dashing deploys a static shield core lasting 10 seconds.",
                  "[gold]📊 DỮ LIỆU PHÂN TÍCH THỰC THỂ LEOLYR:[]\n• [accent]Chi phí triệu hồi:[] [white]2000 Copper[] + [white]300 Silicon[]\n• [accent]Hệ thống Tiến hóa:[] Hấp thụ Đồng và Silicon trực tiếp từ kho đồ (Tối đa Cấp 10).\n• [pink]Vũ khí kép:[] Bắn luân phiên, tăng tiến tốc độ xả đạn theo level.\n• [sky]Lướt & Tạo Lõi Khiên:[] Lướt tạo lõi khiên tĩnh tồn tại 10 giây."));
            card.add().height(16f).row();

            unitEntry(card, dialog,
                t("[purple]👾 SELECT SUMMON: VUS-27 UNIT[]", "[purple]👾 CHỌN TRIỆU HỒI: VUS-27 UNIT[]"), "newex-vus-27",
                t("[purple]Target set: VUS-27[]", "[purple]Đã cài đặt mục tiêu: VUS-27[]"),
                t("[gold]📊 VUS-27 ANALYSIS DATA:[]\n• [accent]Summon Cost:[] [white]3500 Copper[] + [white]600 Silicon[]\n• [accent]Transformation:[] Double Tap to switch between VUS-27 and SUV-27.\n• [pink]VUS-27 Firepower:[] Laser Cannon (160 Dmg, 240 range) & Overdrive buffing fire rate by 1500%.\n• [sky]SUV-27 Mechanics:[] Accelerates over time (+200% Speed), stopping fires a 20-degree shotgun spread.",
                  "[gold]📊 DỮ LIỆU PHÂN TÍCH THỰC THỂ VUS-27:[]\n• [accent]Chi phí triệu hồi:[] [white]3500 Copper[] + [white]600 Silicon[]\n• [accent]Khả năng Biến hình:[] Nhấp đúp (Double Tap) để chuyển đổi qua lại giữa VUS-27 và SUV-27.\n• [pink]Hỏa lực VUS-27:[] Pháo Laser (160 Dmg, 240 dài) & Overdrive buff 1500% tốc bắn.\n• [sky]Cơ chế SUV-27:[] Càng bay càng nhanh (+200% Tốc độ), dừng đột ngột xả Shotgun 20 độ."));

            ScrollPane scroll = new ScrollPane(card);
            scroll.setScrollingDisabled(true, false);
            dialog.cont.add(scroll).maxHeight(400f).width(340f);
            dialog.addCloseButton();
            dialog.show();
        }

        private void unitEntry(Table card, BaseDialog dialog, String btn, String unitId, String toast, String desc) {
            card.button(btn, () -> {
                selectedUnit = unitId;
                Vars.ui.showInfo(toast);
                dialog.hide();
            }).size(300f, 42f).row();
            card.add().height(6f).row();
            card.add(desc).width(320f).get().setWrap(true);
            card.getCells().peek().get().setAlignment(Align.left);
            card.row();
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (!summoning) {
                int[] req = getRequirements();
                if (items.has(Items.copper, req[0]) && items.has(Items.silicon, req[1])) {
                    items.remove(Items.copper, req[0]);
                    items.remove(Items.silicon, req[1]);
                    startSummon();
                    Fx.shieldApply.at(x, y, 0f, purple());
                }
            }

            if (summoning) {
                summonTimer -= Time.delta;
                float progress = Math.max(0f, summonTimer / 300f);

                if (Mathf.chance(0.65)) orbitalLockOnEffect.at(x, y, 0f, Float.valueOf(progress));

                if (summonTimer <= 0f) {
                    summoning = false;

                    UnitType ut = Vars.content.getByName(ContentType.unit, selectedUnit);
                    if (ut == null && selectedUnit.startsWith("newex-")) {
                        ut = Vars.content.getByName(ContentType.unit, selectedUnit.replace("newex-", ""));
                    }
                    if (ut != null) {
                        Unit unit = ut.create(team);
                        unit.set(x, y);
                        unit.add();
                    }

                    satelliteImpactEffect.at(x, y);
                    Fx.smokeCloud.at(x, y);
                    Fx.spawnShockwave.at(x, y);
                    Effect.shake(7f, 7f, x, y);

                    tile.setAir();
                }
            }
        }
    }
}
