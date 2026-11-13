package newex;

import arc.Core;
import arc.Events;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.ServerLoadEvent;
import mindustry.mod.Mod;
import newex.content.Antikei;
import newex.content.BalashilonBlock;
import newex.content.BeryiDrills;
import newex.content.Cin;
import newex.content.Comduik;
import newex.content.CopesBlock;
import newex.content.Crynex;
import newex.content.DrexkouDrills;
import newex.content.ElementSystem;
import newex.content.EmeraliftWall;
import newex.content.ErysidusDrone;
import newex.content.ExplayderAbility;
import newex.content.ExzoryCore;
import newex.content.FixSave;
import newex.content.FlasallowFactory;
import newex.content.GeneratMachine;
import newex.content.LaserBrey;
import newex.content.Laytery;
import newex.content.LaserChargerUranilum;
import newex.content.Korynd;
import newex.content.Buffetles;
import newex.content.Elorix;
import newex.content.LeolyrSummoner;
import newex.content.NewMode;
import newex.content.Sonicor;
import newex.content.ObsOre;
import newex.content.Survival;
import newex.content.Therdum;
import newex.content.Pinfyr;
import newex.content.RedstoneWall;
import newex.content.SplashFx;
import newex.content.Sta;
import newex.content.DroneLauncher;
import newex.content.Tankani;
import newex.content.Tankani4k;
import newex.content.SyrufpatFactory;
import newex.content.TurretSystem;
import newex.content.Tyber;
import newex.content.UnitReactor;
import newex.content.UranilumReactor;
import newex.content.WuexConveyor;
import newex.content.XeuwConveyor;
import newex.content.ZorynexPlanet;
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
        FixSave.init();
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
        UnitReactor.init();
        BalashilonBlock.init();
        LaserBrey.init();
        Antikei.init();
        ZorynexPlanet.init();
        ElementSystem.init();
        Sta.init();
        ErysidusDrone.init();
        Cin.init();
        Tyber.init();
        NewMode.init();
        Sonicor.init();
        Elorix.init();
        Buffetles.init();
        Survival.init();
        LeolyrSummoner.init();
        Tankani.init();
        Tankani4k.init();
        DroneLauncher.init();

        // SplashFx phải khởi tạo SAU CÙNG (JS gốc require splash-fx cuối main.js).
        SplashFx.init();
    }

    /** Game gọi hàm này khi nạp nội dung: tạo các block/đạn được định nghĩa bằng code (hjson sẽ bổ sung thuộc tính sau). */
    @Override
    public void loadContent() {
        ExzoryCore.create();
        Zoj.create();
        BalashilonBlock.create();
        TurretSystem.create();
        Sta.create();
        ErysidusDrone.create();
        Laytery.create();
        Cin.create();
        Therdum.create();
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
