package newex;

import arc.Core;
import arc.Events;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.ServerLoadEvent;
import mindustry.mod.Mod;

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
