package newex;

import arc.Core;
import arc.Events;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.ServerLoadEvent;
import mindustry.mod.Mod;
import newex.content.BeryiDrills;
import newex.content.Comduik;
import newex.content.CopesBlock;
import newex.content.Crynex;
import newex.content.DrexkouDrills;
import newex.content.EmeraliftWall;
import newex.content.ExplayderAbility;
import newex.content.ExzoryCore;
import newex.content.FlasallowFactory;
import newex.content.GeneratMachine;
import newex.content.LaserChargerUranilum;
import newex.content.Korynd;
import newex.content.ObsOre;
import newex.content.Pinfyr;
import newex.content.RedstoneWall;
import newex.content.SyrufpatFactory;
import newex.content.UranilumReactor;
import newex.content.WuexConveyor;
import newex.content.XeuwConveyor;
import newex.content.Zoj;

/**
 * Lớp chính của mod NeoWex (thay scripts/main.js).
 * Mỗi hệ thống nằm trong 1 lớp riêng và được khởi tạo ở đây theo đúng thứ tự cũ của main.js.
 */
public class NexMod extends Mod {
    static final String NEX_DEFAULTS_VERSION = "beta0.8.11xyak";
    static final String NEX_DEFAULTS_KEY = "newex-defaults-applied-version";

    public NexMod() {
        Events.on(ClientLoadEvent.class, e -> applyNexDefaultsIfNeeded());
        Events.on(ServerLoadEvent.class, e -> applyNexDefaultsIfNeeded());

        // Giai đoạn 1: hệ thống nền tảng (thứ tự như main.js)
        MatchTimer.init();
        PerfCore.init();
        NexNet.init();
        NexFree.init();
        ShowHp.init();
        DamageDisplay.init();
        BuffEnemies.init();

        // Giai đoạn 2/3: nội dung (block, pháo, đơn vị)
        ObsOre.init();
        Pinfyr.init();
        EmeraliftWall.init();
        RedstoneWall.init();
        Korynd.init();
        Zoj.init();
        ExplayderAbility.init();
        CopesBlock.init();
        WuexConveyor.init();
        XeuwConveyor.init();
        GeneratMachine.init();
        LaserChargerUranilum.init();
        Crynex.init();
        Comduik.init();
        BeryiDrills.init();
        DrexkouDrills.init();
        UranilumReactor.init();
        FlasallowFactory.init();
        SyrufpatFactory.init();
    }

    /** Game gọi hàm này khi nạp nội dung: tạo các block/đạn được định nghĩa bằng code (hjson sẽ bổ sung thuộc tính sau). */
    @Override
    public void loadContent() {
        ExzoryCore.create();
        Zoj.create();
    }

    static void applyNexDefaultsIfNeeded() {
        String applied = Core.settings.getString(NEX_DEFAULTS_KEY, "");
        if (NEX_DEFAULTS_VERSION.equals(applied)) return;
        Core.settings.put("newex-gacha-enabled", false);
        Core.settings.put("newex-max-turrets", 9999);
        Core.settings.put("newex-hp-per-wave-percent", 10);
        Core.settings.put(NEX_DEFAULTS_KEY, NEX_DEFAULTS_VERSION);
        Core.settings.forceSave();
    }
}
