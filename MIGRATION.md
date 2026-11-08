# Tiến độ chuyển NeoWex từ JS sang Java

Tổng: 97 file JS, 58648 dòng.

Đã xong: 7 file (1193 dòng).

Các file hjson/json, sprite, map, âm thanh **không phải JS nên giữ nguyên**.


## Giai đoạn 1 – nền tảng

| File JS | Dòng | Trạng thái |
|---|---:|---|
| net-sync.js | 316 | ✅ xong → NexNet.java |
| show-hp.js | 286 | ✅ xong → ShowHp.java |
| main.js | 283 | 🟡 một phần → NexMod (mới có: cài đặt mặc định + khởi tạo 7 hệ thống giai đoạn 1; còn lại: các dòng require, hành tinh asteroid) |
| match_timer.js | 252 | ✅ xong → MatchTimer.java |
| damage-display.js | 162 | ✅ xong → DamageDisplay.java |
| perf-core.js | 94 | ✅ xong → PerfCore.java |
| free-mode.js | 43 | ✅ xong → NexFree.java |
| buff-enemies.js | 40 | ✅ xong → BuffEnemies.java |

## Giai đoạn 2 – block (khoan, điện, sản xuất, tường…)

| File JS | Dòng | Trạng thái |
|---|---:|---|
| laser-brey.js | 476 | ⬜ chưa |
| uranilum-reactor.js | 442 | ⬜ chưa |
| flasallow-factory.js | 432 | ⬜ chưa |
| syrufpat-factory.js | 430 | ⬜ chưa |
| drexkou-drills.js | 418 | ⬜ chưa |
| comduik.js | 390 | ⬜ chưa |
| balashilon.js | 376 | ⬜ chưa |
| unit-reactor.js | 352 | ⬜ chưa |
| beryi-drills.js | 343 | ⬜ chưa |
| crynex.js | 281 | ⬜ chưa |
| generat-machine.js | 248 | ⬜ chưa |
| xeuw-conveyor.js | 212 | ⬜ chưa |
| wuex-conveyor.js | 211 | ⬜ chưa |
| laser-charger-uranilum.js | 181 | ⬜ chưa |
| copesblock.js | 142 | ⬜ chưa |
| redstone-wall.js | 104 | ⬜ chưa |
| korynd.js | 95 | ⬜ chưa |
| emeralift-wall.js | 69 | ⬜ chưa |
| obs-ore.js | 61 | ⬜ chưa |
| pinfyr.js | 44 | ⬜ chưa |
| cores.js | 12 | ⬜ chưa |

## Giai đoạn 2 – hệ thống dùng chung

| File JS | Dòng | Trạng thái |
|---|---:|---|
| antikei-logic.js | 496 | ⬜ chưa |
| sta.js | 462 | ⬜ chưa |
| element-system.js | 391 | ⬜ chưa |
| turret-system.js | 325 | ⬜ chưa |
| splash-fx.js | 100 | ⬜ chưa |
| fix-save.js | 83 | ⬜ chưa |
| planets.js | 33 | ⬜ chưa |

## Giai đoạn 3 – turret

| File JS | Dòng | Trạng thái |
|---|---:|---|
| thermion.js | 1867 | ⬜ chưa |
| reflecounum.js | 1542 | ⬜ chưa |
| swordoder.js | 1410 | ⬜ chưa |
| aether-spiral.js | 1137 | ⬜ chưa |
| galaxvorram.js | 1058 | ⬜ chưa |
| indeniter.js | 1028 | ⬜ chưa |
| emperical.js | 1013 | ⬜ chưa |
| dtg-soldern.js | 925 | ⬜ chưa |
| tyteryi.js | 922 | ⬜ chưa |
| emperfum.js | 873 | ⬜ chưa |
| nucleytor.js | 860 | ⬜ chưa |
| plasanod.js | 829 | ⬜ chưa |
| maxitoner.js | 813 | ⬜ chưa |
| forstarsilum.js | 741 | ⬜ chưa |
| overyr.js | 740 | ⬜ chưa |
| dor.js | 726 | ⬜ chưa |
| lyvervon-system.js | 721 | ⬜ chưa |
| hitekalum.js | 719 | ⬜ chưa |
| nethyr.js | 713 | ⬜ chưa |
| lazash.js | 678 | ⬜ chưa |
| lavunder.js | 666 | ⬜ chưa |
| holyder-system.js | 650 | ⬜ chưa |
| endyr.js | 649 | ⬜ chưa |
| flazerd.js | 645 | ⬜ chưa |
| rangtaturs.js | 625 | ⬜ chưa |
| persefer.js | 622 | ⬜ chưa |
| ravendes.js | 604 | ⬜ chưa |
| blixalum.js | 573 | ⬜ chưa |
| reguilater.js | 555 | ⬜ chưa |
| tenytisyr.js | 553 | ⬜ chưa |
| vendicum.js | 545 | ⬜ chưa |
| erysidus.js | 533 | ⬜ chưa |
| xylaon.js | 515 | ⬜ chưa |
| heliyron.js | 511 | ⬜ chưa |
| tankani4k.js | 511 | ⬜ chưa |
| blaw.js | 499 | ⬜ chưa |
| repulsyron.js | 459 | ⬜ chưa |
| drone-launcher.js | 454 | ⬜ chưa |
| tankani500.js | 447 | ⬜ chưa |
| sonicor.js | 436 | ⬜ chưa |
| tankani2k.js | 435 | ⬜ chưa |
| buffetles.js | 413 | ⬜ chưa |
| therdum.js | 366 | ⬜ chưa |
| tyber.js | 356 | ⬜ chưa |
| laytery.js | 281 | ⬜ chưa |
| zoj.js | 98 | ⬜ chưa |

## Giai đoạn 3 – đơn vị & drone

| File JS | Dòng | Trạng thái |
|---|---:|---|
| cargo-drone.js | 1814 | ⬜ chưa |
| cos.js | 1222 | ⬜ chưa |
| suv-27.js | 666 | ⬜ chưa |
| leolyr.js | 486 | ⬜ chưa |
| elorix.js | 415 | ⬜ chưa |
| leolyr-summoner.js | 334 | ⬜ chưa |
| cin.js | 327 | ⬜ chưa |
| erysidus-drone.js | 160 | ⬜ chưa |
| explayder.js | 75 | ⬜ chưa |

## Giai đoạn 4 – giao diện & chế độ chơi

| File JS | Dòng | Trạng thái |
|---|---:|---|
| newmode-pvp.js | 7993 | ⬜ chưa |
| loadout-selection.js | 1508 | ⬜ chưa |
| survivor-skills.js | 1493 | ⬜ chưa |
| shop.js | 465 | ⬜ chưa |
| newmode-survival.js | 388 | ⬜ chưa |
| newmode.js | 301 | ⬜ chưa |
