# Tiến độ chuyển NeoWex từ JS sang Java

Tổng: 97 file JS, 58648 dòng.

Đã xong: 66/98 file trong bảng (kèm main.js phần hành tinh trong NexMod). Còn lại 32 file (~37 850 dòng). Mọi file Java đã được biên dịch thử bằng javac --release 8 với server-release.jar của Mindustry v157.4 (không lỗi). reflecounum.js, thermion.js đang xử lý riêng; newmode-pvp.js (8645 dòng) làm từng phần.

Các file hjson/json, sprite, map, âm thanh **không phải JS nên giữ nguyên**.

Quy ước: hệ thống nền ở `newex/`, block-pháo-đơn vị ở `newex/content/`.


## Giai đoạn 1 – nền tảng

| File JS | Dòng | Trạng thái |
|---|---:|---|
| net-sync.js | 316 | ✅ xong → NexNet.java |
| show-hp.js | 286 | ✅ xong → ShowHp.java |
| main.js | 283 | 🟡 một phần → NexMod (đã có: cài đặt mặc định, khởi tạo hệ thống, loadContent; còn: hành tinh asteroid) |
| match_timer.js | 252 | ✅ xong → MatchTimer.java |
| damage-display.js | 162 | ✅ xong → DamageDisplay.java |
| perf-core.js | 94 | ✅ xong → PerfCore.java |
| free-mode.js | 43 | ✅ xong → NexFree.java |
| buff-enemies.js | 40 | ✅ xong → BuffEnemies.java |

## Giai đoạn 2 – block (khoan, điện, sản xuất, tường…)

| File JS | Dòng | Trạng thái |
|---|---:|---|
| laser-brey.js | 476 | ✅ xong → content/LaserBrey.java |
| uranilum-reactor.js | 442 | ✅ xong → content/UranilumReactor.java |
| flasallow-factory.js | 432 | ✅ xong → content/FlasallowFactory + FactoryFx.java |
| syrufpat-factory.js | 430 | ✅ xong → content/SyrufpatFactory + FactoryFx.java |
| drexkou-drills.js | 418 | ✅ xong → content/DrexkouDrills + DrillUtil.java |
| comduik.js | 390 | ✅ xong → content/Comduik.java |
| balashilon.js | 376 | ✅ xong → content/BalashilonBlock.java |
| unit-reactor.js | 352 | ✅ xong → content/UnitReactor.java |
| beryi-drills.js | 343 | ✅ xong → content/BeryiDrills + DrillUtil + HiddenOres.java |
| crynex.js | 281 | ✅ xong → content/Crynex.java |
| generat-machine.js | 248 | ✅ xong → content/GeneratMachine.java |
| xeuw-conveyor.js | 212 | ✅ xong → content/XeuwConveyor + GateBuild.java |
| wuex-conveyor.js | 211 | ✅ xong → content/WuexConveyor + GateBuild.java |
| laser-charger-uranilum.js | 181 | ✅ xong → content/LaserChargerUranilum.java |
| copesblock.js | 142 | ✅ xong → content/CopesBlock.java |
| redstone-wall.js | 104 | ✅ xong → content/RedstoneWall.java |
| korynd.js | 95 | ✅ xong → content/Korynd.java |
| emeralift-wall.js | 69 | ✅ xong → content/EmeraliftWall.java |
| obs-ore.js | 61 | ✅ xong → content/ObsOre.java |
| pinfyr.js | 44 | ✅ xong → content/Pinfyr.java |
| cores.js | 12 | ✅ xong → content/ExzoryCore.java |

## Giai đoạn 2 – hệ thống dùng chung

| File JS | Dòng | Trạng thái |
|---|---:|---|
| antikei-logic.js | 496 | ✅ xong → content/Antikei.java |
| sta.js | 462 | ✅ xong → content/Sta + PerkTurret.java |
| element-system.js | 391 | ✅ xong → content/ElementSystem + AuraTurret.java |
| turret-system.js | 325 | ✅ xong → content/TurretSystem.java |
| splash-fx.js | 100 | ✅ xong → content/SplashFx.java |
| fix-save.js | 83 | ✅ xong → content/FixSave + LeveledUnit.java |
| planets.js | 33 | ✅ xong → content/ZorynexPlanet.java |

## Giai đoạn 3 – turret

| File JS | Dòng | Trạng thái |
|---|---:|---|
| laytery.js | 281 | ✅ xong → content/Laytery.java |
| zoj.js | 98 | ✅ xong → content/Zoj.java |
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
| ravendes.js | 604 | ✅ xong → content/Ravendes.java (chưa biên dịch thử) |
| blixalum.js | 573 | ✅ xong → content/Blixalum.java |
| reguilater.js | 555 | ✅ xong → content/Reguilater.java |
| tenytisyr.js | 553 | ✅ xong → content/Tenytisyr.java (chưa biên dịch thử) |
| vendicum.js | 545 | ✅ xong → content/Vendicum.java |
| erysidus.js | 533 | ✅ xong → content/Erysidus.java |
| xylaon.js | 515 | ✅ xong → content/Xylaon.java |
| heliyron.js | 511 | ✅ xong → content/Heliyron.java |
| tankani4k.js | 511 | ✅ xong → content/Tankani4k.java |
| blaw.js | 499 | ✅ xong → content/Blaw.java |
| repulsyron.js | 459 | ✅ xong → content/Repulsyron.java |
| drone-launcher.js | 454 | ✅ xong → content/DroneLauncher.java |
| tankani500.js | 447 | ✅ xong → content/Tankani.java |
| sonicor.js | 436 | ✅ xong → content/Sonicor.java |
| tankani2k.js | 435 | ✅ xong → content/Tankani.java |
| buffetles.js | 413 | ✅ xong → content/Buffetles.java |
| therdum.js | 366 | ✅ xong → content/Therdum.java |
| tyber.js | 356 | ✅ xong → content/Tyber.java |

## Giai đoạn 3 – đơn vị & drone

| File JS | Dòng | Trạng thái |
|---|---:|---|
| cin.js | 327 | ✅ xong → content/Cin.java |
| erysidus-drone.js | 160 | ✅ xong → content/ErysidusDrone.java |
| explayder.js | 75 | ✅ xong → content/ExplayderAbility.java |
| cargo-drone.js | 1814 | ⬜ chưa |
| cos.js | 1222 | ⬜ chưa |
| suv-27.js | 666 | ⬜ chưa |
| leolyr.js | 486 | ✅ xong → content/Leolyr.java |
| elorix.js | 415 | ✅ xong → content/Elorix.java |
| leolyr-summoner.js | 334 | ✅ xong → content/LeolyrSummoner.java |

## Giai đoạn 4 – giao diện & chế độ chơi

| File JS | Dòng | Trạng thái |
|---|---:|---|
| newmode-pvp.js | 7993 | ⬜ chưa |
| loadout-selection.js | 1508 | ⬜ chưa |
| survivor-skills.js | 1493 | ⬜ chưa |
| shop.js | 465 | ✅ xong → content/Shop.java (module, gọi từ loadout-selection) |
| newmode-survival.js | 388 | ✅ xong → content/Survival.java |
| newmode.js | 301 | ✅ xong → content/NewMode.java |
| archueng-pvp.js | 378 | ✅ xong → content/ArchuengPvp.java |

## Bài học – code lỗi cần né (cập nhật mỗi lần gặp lỗi mới)

Mỗi mục: lỗi gặp → vì sao → cách đúng. Đọc trước khi chuyển file mới.

1. **`cannot find symbol: baseRegion`** (Blixalum). Turret/ItemTurret ở v157 KHÔNG có trường `baseRegion`; JS `block.baseRegion` chỉ "chạy" vì Rhino bỏ qua. → Tự giữ `TextureRegion customBase = PerfCore.region("reinforced-block-" + size)` rồi chỉ vẽ khi `found()`.
2. **Rhino chọn overload khác Java.** `Effect.at(x, y, rot, Color)` gán `e.color`, KHÔNG gán `e.data`; JS đọc `e.data || màuMặcĐịnh` nên luôn ra màu mặc định (Blixalum muzzle, Reguilater hit). → Đã giữ đúng hành vi cũ. Muốn đổi màu theo tier thì gọi `.at(x, y, rot, (Object) color)`.
3. **`BulletType.create(owner, team, x, y, angle, a, b)` (7 tham số) là `velocityScl, lifetimeScl`, không phải sát thương.** JS truyền hệ số sát thương vào đó (laser MK2B của Blixalum, laser phụ của Reguilater) nên thực tế không nhân sát thương. → Giữ nguyên; nếu muốn nhân sát thương dùng bản 9 tham số `create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data)`.
4. **`Call.createBullet(type, team, x, y, angle, damage, velocityScl, lifetimeScl)`**: JS truyền `bullet.speed` vào `velocityScl` (đạn nhanh gấp `speed` lần). → Giữ nguyên, ghi chú trong code.
5. **`Units.closestTarget(...)` trả `Teamc`**, không có `.dead`/`.damage` như JS. → Ép sang `Healthc` rồi dùng `dead()`, `damage()`, `health()`, `maxHealth()`; toạ độ dùng `getX()/getY()`.
6. **`canControl` là phương thức của `ControlBlock`**, không phải trường của block. `reguilater.canControl = false` trong JS vô tác dụng. → `@Override public boolean canControl() { return false; }` trong lớp Build.
7. **Lớp Build lồng nhau** (`ItemTurretBuild`, `PowerTurretBuild`) → constructor phải `outer.super();`, và `buildType = () -> new XBuild(block)`.
8. **Ngôn ngữ**: mỗi file JS tự định nghĩa một kiểu kiểm tra ngôn ngữ khác nhau (`isVietnamese` = "không phải en", `isEnglish` = "locale bắt đầu bằng en", `vn()` = "bắt đầu bằng vi"). Chép đúng hàm của từng file, đừng gộp chung kẻo đổi ngôn ngữ mặc định.
9. **Cách kiểm tra không cần chờ GitHub Actions**: `javac -nowarn -encoding UTF-8 --release 8 -cp server-release.jar -d out $(find src -name '*.java')` (server-release.jar lấy từ GitHub Releases v157.4).
10. **`this.isShooting` (không có ngoặc) trong JS luôn truthy** (là đối tượng hàm, không phải boolean) → điều kiện `isShooting && hasAmmo() && isActive()` của Tenytisyr thực ra chỉ còn `hasAmmo() && isActive()`. → Java gọi `isShooting()` thật (gần như tương đương); luôn soát JS tìm tên hàm quên `()`.
11. **`create(owner, team, x, y, angle, -1, randSpeed)` (7 tham số) = `velocityScl=-1, lifetimeScl=randSpeed`** (Ravendes MK2B shotgun): vận tốc ×(-1) nên đạn bay NGƯỢC hướng nòng. → Giữ nguyên hành vi cũ, có chú thích trong `fireShotgunBurst()`. Muốn sửa: dùng bản 9 tham số `create(owner, team, x, y, angle, -1f, randScl, 1f, null)`. (Cùng gốc với bài học 3.)
12. **`Lines.circle(Color)` không tồn tại** (Tenytisyr, hiệu ứng nổ MK2B: `Lines.circle(ringColors[i])`). Rhino chỉ báo lỗi lúc chạy; Java báo lỗi biên dịch. → Bỏ dòng đó, giữ `Draw.color(...)` + `Lines.circle(x, y, r)`.
13. **`block.basePrefix` không tồn tại trên Turret v157** (nó thuộc `DrawTurret`). `PerfCore.region(block.basePrefix + size)` ra `"undefined4"` → không tìm thấy → luôn chạy `super.draw()`. → Java gọi thẳng `super.draw()` rồi vẽ nòng, khỏi tạo chuỗi tên giả.
14. **Biến cục bộ bị lambda sửa**: Java không cho lambda gán lại biến cục bộ (JS `rx += x` trong `randLenVectors`). → Dùng mảng 1 phần tử `final float[] pos = {x, y}`.
15. **Ghi đè `shouldShoot()` nên khai báo `public`** (nếu lớp cha là protected thì public vẫn hợp lệ, ngược lại thì lỗi biên dịch). Ghi chú: `isGalileoJS` của JS → `instanceof LeveledUnit` trong Java.
16. **Các file Tenytisyr/Ravendes được viết trong môi trường không có Mindustry jar** nên CHƯA biên dịch. Chạy lệnh ở mục 9 trước khi push.


## Thứ tự làm tiếp (dễ/nhanh → khó/lâu)
1. persefer.js (622 dòng)
2. rangtaturs.js (625 dòng)
3. flazerd.js (645 dòng)
4. endyr.js (649 dòng)
5. holyder-system.js (650 dòng)
6. lavunder.js (666 dòng)
7. suv-27.js (666 dòng)
8. lazash.js (678 dòng)
9. nethyr.js (713 dòng)
10. hitekalum.js (719 dòng)
11. lyvervon-system.js (721 dòng)
12. dor.js (726 dòng)
13. overyr.js (740 dòng)
14. forstarsilum.js (741 dòng)
15. maxitoner.js (813 dòng)
16. plasanod.js (829 dòng)
17. nucleytor.js (860 dòng)
18. emperfum.js (873 dòng)
19. tyteryi.js (922 dòng)
20. dtg-soldern.js (925 dòng)
21. emperical.js (1013 dòng)
22. indeniter.js (1028 dòng)
23. galaxvorram.js (1058 dòng)
24. aether-spiral.js (1137 dòng)
25. cos.js (1222 dòng)
26. swordoder.js (1410 dòng)
27. survivor-skills.js (1493 dòng)
28. loadout-selection.js (1508 dòng)
29. reflecounum.js (1542 dòng)
30. cargo-drone.js (1814 dòng)
31. thermion.js (1867 dòng)
32. newmode-pvp.js (7993 dòng)
