# Tiến độ chuyển NeoWex từ JS sang Java

Tổng: 98 file JS, ~58800 dòng (thêm flarmed.js).

Đã xong: 91/99 file trong bảng (kèm main.js phần hành tinh trong NexMod). Còn lại 8 file (~19 000 dòng). Các file cũ đã được biên dịch thử bằng javac --release 8 với server-release.jar của Mindustry v157.4 (không lỗi); **Cập nhật: toàn bộ `src/` (gồm cả Nucleytor, Emperfum, Tyteryi và DtgSoldern mới) đã biên dịch thật với server-release.jar v157.4, 0 lỗi** (sandbox tải được jar từ GitHub Releases, xem bài 81). reflecounum.js, thermion.js đang xử lý riêng; newmode-pvp.js (dòng thực tế đã tăng lên ~8833, xem ghi chú ở mục lộ trình) làm từng phần.

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
| flarmed.js | 157 | ✅ xong → content/Flarmed.java (chưa biên dịch thật, xem bài 93) |
| laytery.js | 281 | ✅ xong → content/Laytery.java |
| zoj.js | 98 | ✅ xong → content/Zoj.java |
| thermion.js | 1867 | ⬜ chưa |
| reflecounum.js | 1542 | ⬜ chưa |
| swordoder.js | 1410 | ⬜ chưa |
| aether-spiral.js | 1136 | ✅ xong → content/AetherSpiral.java (chưa biên dịch thật, bài 93/94) |
| galaxvorram.js | 1057 | ✅ xong → content/Galaxvorram.java (block `new PowerTurret("galaxvorram")`, hjson không có `type:`; Galaxvorram.create() trong NexMod sau Indeniter; biên dịch với jar v157.4, 0 lỗi; bài 89–90) |
| indeniter.js | 1027 | ✅ xong → content/Indeniter.java (block `new ItemTurret("indeniter")`, hjson không có `type:`; Indeniter.create() trong NexMod sau DtgSoldern; biên dịch với jar v157.4, 0 lỗi; bài 85–88) |
| emperical.js | 1012 | ✅ xong → content/Emperical.java (hjson có `type: ItemTurret` nên GIỮ hjson, Java vá ở ContentInitEvent; Emperical.init() trong NexMod sau Hitekalum; biên dịch với jar v157.4, 0 lỗi; bài 82–84) |
| dtg-soldern.js | 925 | ✅ xong → content/DtgSoldern.java (block `new ItemTurret("dtg-soldern")`, hjson không có `type:` nên giữ nguyên; DtgSoldern.create() trong NexMod sau Tyteryi; đã biên dịch với jar v157.4, 0 lỗi; xem bài 79–80) |
| tyteryi.js | 922 | ✅ xong → content/Tyteryi.java (block `new ItemTurret("tyteryi")`, giữ hjson không `type:`; Tyteryi.create() trong NexMod sau Emperfum; mới kiểm tra cú pháp, chưa biên dịch với jar; xem bài 76–78) |
| emperfum.js | 873 | ✅ xong → content/Emperfum.java (block `new ItemTurret("emperfum")` nên hjson giữ nguyên không `type:`; Emperfum.create() trong NexMod.loadContent sau Blixalum; mới kiểm tra cú pháp, chưa biên dịch với jar; xem bài 70–73) |
| nucleytor.js | 860 | ✅ xong → content/Nucleytor.java (3 bullet dùng chung lớp `NucleytorBullet`; cơ chế true-damage qua `activeBullets`; chưa biên dịch với jar) |
| plasanod.js | 829 | ✅ xong → content/Plasanod.java (ContinuousTurret; Plasanod.create() trong NexMod.loadContent SAU Sta.create(); hjson không có `type:` nên giữ; chưa biên dịch với jar) |
| maxitoner.js | 813 | ✅ xong → content/Maxitoner.java (Maxitoner.create() trong NexMod.loadContent; hjson KHÔNG có `type:` cấp cao nhất nên giữ nguyên; kiểm tra cú pháp OK, chưa biên dịch với jar) |
| forstarsilum.js | 741 | ✅ xong → content/Forstarsilum.java (block do hjson tạo, `type: PowerTurret` giữ nguyên; Forstarsilum.init() trong NexMod; chưa biên dịch với jar) |
| overyr.js | 740 | ✅ xong → content/Overyr.java (dựng từ Nethyr.java; Overyr.init() trong NexMod; giữ `type:` trong hjson; chưa biên dịch với jar) |
| dor.js | 726 | ✅ xong → content/Dor.java (Dor.create() trong NexMod; chưa biên dịch với jar, đã qua kiểm tra cú pháp) |
| lyvervon-system.js | 721 | ✅ xong → content/Lyvervon.java (Lyvervon.create() trong NexMod) |
| hitekalum.js | 719 | ✅ xong → content/Hitekalum.java (Hitekalum.init() trong NexMod) |
| nethyr.js | 713 | ✅ xong → content/Nethyr.java (dựng từ Endyr.java; Nethyr.init() trong NexMod) |
| lazash.js | 678 | ✅ xong → content/Lazash.java (đã biên dịch với server-release.jar v157.4, không lỗi) |
| lavunder.js | 666 | ✅ xong → content/Lavunder.java (hjson không có `type:`; chưa biên dịch với jar) |
| holyder-system.js | 650 | ✅ xong → content/Holyder.java (hjson không có `type:` nên giữ nguyên; chưa biên dịch với jar) |
| endyr.js | 649 | ✅ xong → content/Endyr.java (block do endyr.hjson tạo; Java chỉ vá ở ContentInitEvent nên GIỮ `type:` trong hjson; chưa biên dịch với jar) |
| flazerd.js | 645 | ✅ xong → content/Flazerd.java (chưa biên dịch với jar Mindustry) |
| rangtaturs.js | 625 | ✅ xong → content/Rangtaturs.java (chưa biên dịch với jar Mindustry; đã qua kiểm tra cú pháp javac) |
| persefer.js | 622 | ✅ xong → content/Persefer.java (chưa biên dịch thử) |
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
| suv-27.js | 666 | ✅ xong → content/Suv27.java (đơn vị do hjson tạo; Java vá ở ClientLoadEvent; chưa biên dịch với jar) |
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
| archueng-pvp.js | 406 | ✅ xong → content/ArchuengPvp.java (đã đồng bộ JS mới 14/11: mobile 4 lần bấm, xem bài 92) |

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
15. **`shouldShoot()` KHÔNG tồn tại trên `Turret.TurretBuild` v157** (Ravendes: `cannot find symbol` + `does not override`). JS override hàm không có thì Rhino bỏ qua, nên hàm đó chưa từng chạy. → Xóa override, giữ đúng hành vi thật (MK2B vẫn bắn thường + shotgun). Muốn tắt bắn thường ở MK2B thì phải chặn trong `shoot(BulletType)` (tier==2 → return). Tổng quát: trước khi `@Override`, kiểm tra hàm có thật trong v157; `@Override` lỗi = JS gốc đang chạy ngầm không tác dụng. `isGalileoJS` của JS → `instanceof LeveledUnit`.
16. **Các file Tenytisyr/Ravendes được viết trong môi trường không có Mindustry jar** nên CHƯA biên dịch. Chạy lệnh ở mục 9 trước khi push.
17. **Persefer: `this.isShooting` thiếu ngoặc đổi hành vi rõ rệt.** JS luôn truthy nên halo hiện liên tục và `continuousShootTime` chỉ tăng (kể cả không bắn → đạn Star tới 100% sau 22s đứng yên). Bản Java gọi `isShooting()` thật nên halo/đạn Star chỉ tăng khi đang bắn. Nếu muốn y hệt bản cũ: thay bằng `if (true)`.
18. **hjson có `ammoTypes` + JS `ammoTypes.clear()/put()` lúc ContentInitEvent**: hjson nạp SAU `loadContent()` nên sẽ ghi đè đạn đặt trong code. → Đặt lại đạn ở `Events.on(ContentInitEvent.class, ...)` (Persefer.init()).
19. **`hit(b, x, y)` ghi đè không gọi `super`** (Persefer): mất splash/âm thanh/lightning mặc định của BulletType. Java giữ nguyên (không gọi super). `hitEffect.at(x, y, rot, Color)` là overload gán `e.color` (bài học 2).
20. **Rhino ưu tiên overload `float` hơn `int` cho số literal.** `Mathf.rand.random(2, 14)` / `random(360)` / `Mathf.range(8)` trong JS luôn chạy bản float (liên tục). → Java phải viết `2f, 14f`, `360f`, `8f`; viết `int` sẽ ra số nguyên rời rạc, sai hiệu ứng.
21. **Ghép nguyên văn khối chuỗi song ngữ bằng script** (`splice.py`: skeleton Java có dòng `//@@JS a b`, script chép dòng a..b của file JS vào) để khỏi gõ lại vài KB tiếng Việt/Anh (tiết kiệm token, không sai chính tả). Chỉ cần đổi: `let x` → `String x`; `this.health` → `Math.round(health)`, `this.block.range` → `Math.round(((ItemTurret) block).range)` (trong lớp Build, `block` có kiểu `Block` nên không có `range`/`reload`...: phải ép sang `ItemTurret`; lỗi `cannot find symbol: variable range` ở Rangtaturs) (vì nối `float` vào chuỗi Java ra `"950.0"`, JS ra `"950"`); `this.block.size` → `block.size`. Dùng Python để in/cắt dòng UTF-8 (`cut -c`, `head -c` làm hỏng ký tự đa byte và làm tool báo lỗi).
22. **Kiểm tra cú pháp khi không có jar Mindustry**: `java -m jdk.compiler/com.sun.tools.javac.Main -proc:none -nowarn -encoding UTF-8 -d cls file.java 2>&1 | grep error: | grep -v -E "cannot find symbol|does not exist"`. Lỗi "does not override", "lambda not expected", "non-static variable super" chỉ là hệ quả của việc không tìm được lớp cha → bỏ qua; chỉ lo lỗi kiểu `expected`, `illegal start`, `unclosed`. Vẫn phải chạy lệnh ở mục 9 trước khi push.
23. **hjson ghi đè health/reload/range/ammoTypes đặt trong code** (Rangtaturs: hjson đặt `health 950, reload 20, range 352` và `ammoTypes: titanium` đạn giả `damage 1`). Java đặt giá trị như JS rồi để hjson ghi đè sau (cùng thứ tự với JS) nên KHÔNG cần ContentInitEvent như bài học 18. **Cảnh báo hành vi cũ**: laser lấy `peekAmmo().damage × 2.8/3.1/3.1`, mà đạn nào cũng `damage = 1` nên laser thực tế chỉ ~2.8–3.1 sát thương (+hiệu ứng trạng thái). Đã giữ nguyên; muốn laser mạnh thật (45/50/65) thì đặt `baseDmg` cố định thay vì lấy từ đạn.
24. **Trường trong JS nhưng chưa chắc có ở v157** (`chargeEffect` của LaserBulletType): Rhino bỏ qua im lặng, Java có thể lỗi biên dịch. Pháo không dùng `shoot.firstShotDelay` nên hiệu ứng này không bao giờ phát → bỏ, có chú thích trong code. Trước khi gán trường lạ, grep xem file Java đã biên dịch được nào dùng chưa (vd `squareSprite` đã có ở Blixalum nên an toàn).
25. **Override JS không tồn tại ở v157** (Flazerd `destroyed()`; trước đó `shouldShoot` bài 15): kiểm tra tên hàm thật; nếu mục đích là dọn bộ nhớ thì dùng `onRemoved()`. Map `ObjectMap` theo id pháo trong JS → gộp thành 1 lớp `State` trong `HashMap<Integer,State>`.
26. **`Units.closestTarget(team,x,y,range,unitPred,tilePred)` với `tilePred = t -> true` có thể trả về Building** (laser Flazerd vẫn đánh được công trình); dùng `Teamc.id()/getX()/getY()` và cast `Healthc` để `damage()` (mở rộng bài 5).
27. **`Mathf.sin(x)` nhận radian; `Mathf.PI` là float**: thay `Math.PI` khi truyền vào `Mathf.sin`.
29. **Hai kiểu block, hai cách xử lý hjson.** (a) Java tạo block bằng `new XTurret("x")` → hjson cùng tên phải BỎ `type:` cấp cao nhất (bài 28). (b) hjson tạo block (có `type: ItemTurret`), JS chỉ vá bằng `Vars.content.getByName(...)` lúc `ContentInitEvent` (endyr) → Java cũng chỉ vá ở `ContentInitEvent` (đăng ký trong `init()`), KHÔNG `new`, và GIỮ nguyên hjson. Nhìn đầu file JS: có `extend(ItemTurret, "name"...)` là (a), có `getByName(...)` là (b).
    Phụ: `configured()` JS kiểm tra `typeof value === "number"` luôn sai với `java.lang.Integer` nên chưa từng chạy; `config()` của Endyr trả mặt nạ ô trang bị KHÔNG có bit 15 nên khi copy/dán bản thiết kế sẽ bị hiểu là toạ độ bắn (giữ nguyên hành vi cũ). `global.NexReflect.isProtected` → `Endyr.reflectProtected` (Boolf) để nối khi chuyển reflecounum.js.
30. **JS gọi sai kiểu/ghi thuộc tính vô hiệu (Holyder).** `turnToTarget(currentTarget)` truyền đối tượng vào hàm nhận `float` → Java phải truyền góc `Angles.angle(x,y,tx,ty)`. `this.recoil = 1.0` trên Build không có tác dụng (Rhino chỉ tạo thuộc tính JS) → bỏ. `region.found` (thiếu `()`) luôn truthy → Java dùng `found()`. `Vars.ui.getLanguage` không tồn tại → bỏ nhánh. `font.getData().setScale(0.12)` mà không trả lại làm nhỏ chữ outline toàn game → Java lưu `scaleX` cũ và khôi phục. `updateTile()` JS KHÔNG gọi `super$updateTile` nên Java cũng không gọi (giữ nguyên). `target` của Turret là `Posc`/`Teamc`, không có `dead`: dùng helper `isDead(Object)` = `o instanceof Healthc && ((Healthc) o).dead()`.
31. **Lavunder (cùng họ Flazerd).** `String.valueOf(Math.floor(x))` trong JS gọi bản `double` nên chữ hiện "12.0" → Java giữ `String.valueOf(Math.floor(x))`, KHÔNG ép `(int)`. `x / y` trong JS là chia thực: `(2 / 60)` = 0.0333, Java phải viết `2f / 60f` (nếu viết `2 / 60` ra 0). Lavunder KHÔNG có `write/read` nên cấp MK2/MK2B mất khi lưu-nạp (hành vi cũ, cố ý KHÔNG thêm vì sẽ làm hỏng save cũ do lệch số byte). Hiệu ứng có dữ liệu: `new Effect(life, clip, e -> ...)` + `e.data instanceof Lớp` thay cho object literal `{t, seed}`; `Mathf.randomSeed(long)` nhận id dạng `long` (`turretId * 131L`).
32. **Đơn vị (unit) có logic riêng (suv-27).** JS `uType.constructor = function(){ return extend(UnitEntity, {...}) }` → Java: lớp con `class SuvUnit extends UnitEntity` / `VusUnit extends LegsUnit` + `uType.constructor = SuvUnit::new` (đặt ở `ClientLoadEvent`, như JS nên máy chủ headless không có kỹ năng). Không đăng ký `EntityMapping` để `classId` kế thừa như bản JS (lưu/nạp y hệt). Logic dùng chung viết thành hàm tĩnh `updateLogic/drawLogic(Unit self, ...)`, mỗi lớp con chỉ gọi `super.update()` rồi hàm đó. Dữ liệu theo đơn vị: `HashMap<Integer,UnitData>` thay cho object `{}` (JS `delete map[id]` → `remove`). UI: `(x).toFixed(1)` → `Strings.fixed(x, 1)` (không phụ thuộc locale); `visibility = boolp(fn)` → `elem.visibility = Lớp::hàm`; `extend(Element,{draw})`/`extend(InputListener,{...})` → lớp con ẩn danh có `@Override`. hjson của units/suv-27/* giữ nguyên (không `new` đơn vị trong Java).

33. **Lỗi biên dịch thật từ CI (Suv27, Lavunder).** (a) `BulletType.copy()` trả `BulletType` — không có `frontColor/backColor` (chỉ `BasicBulletType`): phải `instanceof` + ép kiểu. (b) `Unit.added` không phải trường công khai → dùng `isAdded()`. (c) `Sounds.laserbig` không tồn tại: trong JS lời gọi nằm trong `try{}catch{}` nên lỗi bị nuốt và âm thanh chưa từng phát → Java bỏ lời gọi. Quy tắc: tên trường/âm thanh/hiệu ứng lấy từ JS mà javac không tìm thấy thì tra lại, nếu JS bọc try/catch thì rất có thể nó chưa từng chạy.

28. *(khôi phục – bài gốc bị thiếu)* Block do Java tạo bằng `new XTurret("x")` thì hjson cùng tên phải BỎ `type:` cấp cao nhất (xem bài 29a).
34. **Lazash – trường/hàm JS không có ở v157.** `targetBuildings` (Turret không có) và `powerCapacity` bị Rhino bỏ qua → Java không gán. hjson nạp SAU nên ghi đè `range 105`, `targetAir/Ground true`; chỉ `range()` của lớp Build (10605) thực sự quyết định tầm bắn.
35. **`this.isShooting` thiếu `()` lần nữa (Lazash).** JS luôn truthy → khi người chơi điều khiển, pháo (và cả chuỗi pháo kề sát) bắn liên tục không cần bấm. Java gọi `isShooting()` thật (cùng hướng bài 10, 17). Muốn y hệt JS cũ: thay bằng `true`.
36. **Dữ liệu hiệu ứng object literal `{len, widthMult}` / `{x, y}`** → lớp nhỏ `SlashData` / `Vec2`, đọc bằng `e.data instanceof ...`. Số truyền làm data (`vChargeFx.at(x, y, rot, range())`) phải ép `(Object) Float.valueOf(..)` để không rơi vào overload `Color`; đọc lại bằng `((Number) e.data).floatValue()`. `e.data.len` trong JS kiểm tra truthy → Java `len != 0f`.
37. **`config(Integer.class, (LazashBuild t, Integer v) -> ...)`** thay `packCons2`; `typeof b.setSlaveCommand === "function"` → `b instanceof LazashBuild`. Lớp Build lồng trong lớp con của PowerTurret thì `buildType = LazashBuild::new` là đủ (không cần `outer.super()`).
38. **Công cụ**: `splice.py` nay hỗ trợ marker `@@T:n@@` (lấy nguyên biểu thức `t("vi","en")` bắt đầu từ dòng n của JS, cân ngoặc, an toàn UTF-8). JDK 21 trong sandbox KHÔNG hỗ trợ `--release 8`: bỏ cờ này khi kiểm tra cục bộ, CI (Java 17 + Gradle) vẫn giữ `sourceCompatibility 1.8`. Dùng `java -m jdk.compiler/com.sun.tools.javac.Main` vì không có lệnh `javac`.
39. **Nethyr – file cùng họ thì tái dùng Java đã chạy được.** Nethyr và Endyr dùng chung hệ 4 ô trang bị/UI/handleEquipConfig; dựng Nethyr từ Endyr.java rồi chỉ thay phần khác (đạn xoay `Orb`, `updateTile`, `draw`) rẻ token hơn viết lại. Khác biệt cần nhớ: Nethyr kẹp điểm chọn vào trong tầm (`range()`), `burstQueue = 1` (Endyr là 3, có `burstIntervalTimer`), sát thương nhân ngẫu nhiên 0.5–2.0 và kết liễu so cả `hpRatioBeforeHit`.
40. **Object literal làm trạng thái (`orbitingBullets.push({...})`)** → lớp `Orb` + `ArrayList`; `state: "orbit"/"launch"` → `boolean launch`. Vòng lặp ngược + `splice(i,1)` → `orbs.remove(i)` (thêm đạn mới vào cuối trong vòng lặp vẫn an toàn vì duyệt ngược).
41. **Biến bị gán trong callback JS (`targetEnemyFound = true`, `chosenTarget = u`)** → Java lambda không sửa được biến cục bộ: dùng mảng 1 phần tử `boolean[]`/`Unit[]`.
42. **`global.NexReflect.isProtected`** của Nethyr dùng chung `Endyr.reflectProtected` (gán một lần khi chuyển reflecounum).
43. **Hitekalum – lỗi hiển thị của JS được giữ nguyên (parity), đã ghi chú trong code:** `microLightningEffect.at((x1+x2)/2, (x2+x2)/2, ...)` (toạ độ y dùng x2) và `Fill.circle(e.x + x, y + y, ...)` trong `mk3ExplosionFX` (`y + y`). Muốn sửa: đổi thành `(y1+y2)/2` và `e.y + py`.
44. **`config()` không khớp `setPerkData`:** `config()` trả `A*100+B*10+C` nhưng `setPerkData` đọc mốc 10/20/30 → sao chép/dựng lại block sẽ ra phúc lợi sai (ví dụ 123 → C = 93). Giữ nguyên; sửa đúng là trả `10+A, 20+B, 30+C` rồi cấu hình từng giá trị.
45. **`typeof other.damage === "function"` trên Bullet là setter `damage(float)`** (Rhino coi field+method cùng tên là function) → JS thực chất đặt sát thương đạn = 90% máu pháo, KHÔNG phải gây sát thương lên kẻ địch như mô tả "phản 15% sát thương". Java giữ `other.damage(maxHealth * 0.9f)`. Ngoài ra `Fx.spark` không tồn tại ở v157 (JS ném TypeError mỗi lần trúng đạn khi có phúc lợi 1B) → Java dùng `Fx.sparkShoot`. Bài chung: hàm/hiệu ứng JS gọi trên thuộc tính `undefined` chỉ lỗi lúc chạy; Java báo ngay lúc biên dịch – đọc kỹ các lỗi `cannot find symbol` vì chúng lộ ra bug cũ.
46. **`this.healthMultiplier = mult` không có tác dụng:** `Building` v157 không có trường này (chỉ `Unit`), JS chỉ tạo thuộc tính rác trên wrapper → "+200% máu" của phúc lợi 1B/3C/4C chưa bao giờ hoạt động. Java bỏ gán (parity). Muốn làm thật: override `maxHealth()`/nhân `health` khi nhận sát thương.
47. **`target` của Turret là `Posc` (có thể là công trình), không phải Unit:** JS gọi `hasEffect/apply` lên công trình sẽ lỗi; Java kiểm tra `instanceof Unit`, công trình chỉ nhận sát thương thường (`Healthc.damage`). `target.dead` → `isDead(Posc)`; `target.x` → `getX()`. Tool: `splice2.py` thêm marker `@@E:n@@` (nguyên biểu thức `isEn() ? vi : en`, n là số dòng hoặc `~đoạn-chữ`) và `@@S:n@@` (một chuỗi) – dùng cho các file viết `isEn() ? ... :` thay vì `t(vi,en)`. `Func<Integer,String>` (không phải `Intf`) cho lambda int→String.
48. **Lyvervon – hjson ghi đè giá trị Java và trường `final`:** hjson nạp sau Java nên `size: 2`, `health`, `range`, `consumes: power: 2` thắng giá trị JS (`size = 4`, 100/60…); chuỗi UI ghi "4x4" nên sai so với thực tế (size 2). `block.consPower.usage = ...` trong `setTier` là no-op vì `usage` là `final` (Rhino lặng lẽ bỏ qua; Java không cho biên dịch) → điện tiêu thụ MK2/MK2B chưa từng đổi như mô tả. `powerCapacity` cũng không tồn tại ở v157 (cả Lazash). Java bỏ các dòng đó, giữ parity.
49. **`setTier` đổi `block.range/reload` của block dùng chung:** đổi tầng một pháo làm đổi tầm bắn mọi pháo Lyvervon (JS và Java như nhau). Muốn đúng nên override `range()` theo từng Build như Hitekalum.
50. **Chuỗi `if` lồng 4 tầng + closure (`applySubCharge`) → vòng `for` + `Cons<Unit>`**, biến chốt đầu lượt (`currentSubBonus`) khai báo `final`; nhánh "extra" chỉ chạy khi đủ 4 mắt xích (`chain == 4`). `Units.closestTarget` trả `Teamc` (đơn vị hoặc công trình): dùng `getX()/getY()` và `Healthc.damage` thay cho `.x/.damage` của JS; `ObjectMap` khoá `id` → `IntFloatMap`.
51. **`baseRegion.found` thiếu `()` (JS) → Java dùng `found()` thật** (sprite `lyvervon-base` có sẵn nên kết quả không đổi). `Math.random()` giữ nguyên (không đổi sang `Mathf.random`). Tool: `splice2.py` nay có marker `@@R:a-b@@` (chép nguyên văn dòng a..b khi đã là Java hợp lệ – rất hợp với khối chuỗi `+` dài) và nhận cả `en ? A : B`.

52. **Dor – `this.isShooting` thiếu `()` lần 4 (updateTile, 2 nhánh).** JS luôn truthy nên khi cấp MK2B/MK2B1/MK3B đang xả loạt thì `reloadCounter += reload` mỗi tick kể cả khi KHÔNG bắn; nhánh Cuồng Bạo cũng tăng reload không cần bắn. Java gọi `isShooting()` thật (cùng hướng bài 10/17/35). Muốn y hệt JS cũ: bỏ `isShooting() &&`.
53. **Dor – hjson ghi đè.** `dor.hjson` (không có `type:`, đúng bài 29a) đặt `size 2, reload 20, range 300, health 950` và `ammoTypes: copper` → thắng giá trị trong code (`size 3, reload 40`, đạn chì); `range()` của lớp Build mới quyết định tầm bắn thật. Java dùng `ammoTypes.put(Items.lead, ...)` thay `ammo(...)`. `chargeEffect: Fx.lancerLaserCharge` của laser bỏ (bài 24). `Mathf.range(35)` → `35f` (bài 20). Số lẻ dạng float (`burstTimer`...) đổi sang `int` chỉ ở bộ đếm nguyên (burstTimer, burstShotsFired), timer dùng `Time.delta` giữ `float`.
54. **Công cụ**: `grep -n` / `cut` trên file tiếng Việt có thể làm tool báo "not valid UTF-8" → in dòng bằng Python (`.encode('ascii','replace')` nếu chỉ cần xem số dòng). Marker `@@E:n@@` của `splice2.py` chạy tốt cho cả `x.add(isEn() ? A : B)`, `.tooltip(...)`, `let t = isEn() ? ...;`, `new BaseDialog(isEn() ? A : B)` → tiết kiệm ~8 KB chuỗi tiếng Việt/Anh mỗi file kiểu này.

55. **Overyr (họ Endyr/Nethyr) – sinh file bằng script thay vì gõ lại.** `gen_overyr.py`: chép Nethyr.java, đổi tên (`Nethyr`→`Overyr`), đổi hằng (tầm 380→760, vùng 30→10 ô) rồi thay khối `Orb/updateTile/fireAtTarget`. Chỉ ~150 dòng mới thay vì 700. Đã đối chiếu mọi chuỗi literal của JS có trong Java (script so sánh tập chuỗi).
56. **`directBeamLaser.create(this, team, x, y, aimAngle, subBeamDmg, 1.0)` (7 tham số)** = `velocityScl=subBeamDmg, lifetimeScl=1` (bài 3) → laser luôn 150 sát thương gốc, KHÔNG nhân buff trang bị; chỉ mưa laser/đạn rơi (`Damage.damage`, `spawnSkyPhysicalBullet`) dùng `subBeamDmg`. Giữ nguyên.
57. **Khác Nethyr:** Overyr KHÔNG kẹp điểm chọn vào tầm khi click (Nethyr có), nhưng `handleEquipConfig` vẫn kiểm tra `dst <= range()` nên điểm ngoài tầm bị bỏ. `b.state` chỉ có `"sky_fall"` nên bỏ. `Time.run(delay, runnable)` → `Time.run(float, () -> ...)`, nhớ `final` cho biến bắt trong lambda. `Lines.stroke(float, Color)` tồn tại nên giữ.

58. **Forstarsilum – `!this.isShooting` thiếu `()` (lần 5): buff KHÔNG BAO GIỜ suy giảm trong JS cũ** (`!hàm` luôn false), dù UI ghi "tự suy giảm khi không bắn". Java gọi `isShooting()` thật nên buff giảm khi ngừng bắn (đúng mô tả). Muốn y hệt JS cũ: bỏ nhánh suy giảm. Cân bằng game thay đổi rõ: MK1 trước đây đạt 100% buff rồi giữ mãi.
59. **Forstarsilum – chi tiết khác.** `armorPiercing: 0.80` không phải trường BulletType v157 (Rhino bỏ qua) → không gán. `entity.armor` chỉ có ở `Unit`: Java kiểm tra `instanceof Unit`; đạn Ult mỗi lần trúng nhân giáp x0.5 VĨNH VIỄN lên đơn vị (giữ nguyên). `entity.damagePierce/maxHealth/dead` → ép `Healthc`. `ultBullet.create(..., 1.0, 1.0)` là (velocityScl, lifetimeScl) (bài 3). `block.basePrefix` không tồn tại → `super.draw()` (bài 13). `addBar` dùng `(Building e) -> new Bar((Prov<CharSequence>) ..., (Prov<Color>) ..., (Floatp) ...)`; `Math.floor` → `Mathf.floor` (int) để chữ in "12" thay vì "12.0" (khác bài 31 vì JS in số nguyên đúng ở đây). `ParticleEffect`: `extend(ParticleEffect,{...})` → `new ParticleEffect()` rồi gán trường (`lifetime` thuộc Effect).
60. **Công cụ:** `splice2.py` nay nhận `english ? A : B` (biến cục bộ `english`) và `@@R:a-b@@` rất hợp khi khai báo `static final class Req {titanium,...}` để chép nguyên văn `reqMK2.titanium` trong khối chuỗi.

61. **Maxitoner – `this.isShooting` thiếu `()` (lần 6).** JS luôn truthy nên phúc lợi 1C (tự bắn 3 đạn phụ mỗi 1s) chạy cả khi KHÔNG bắn. Java gọi `isShooting()` thật nên chỉ tự bắn khi đang bắn. Muốn y hệt JS cũ: bỏ `isShooting() &&`.
62. **Maxitoner – `this.reloadTime = ...` không có tác dụng:** Build v157 không có trường `reloadTime` (Rhino chỉ tạo thuộc tính rác) → tốc bắn của phúc lợi 2A/3A/1B/2B ("+80%", "+120%", "-50%"...) CHƯA BAO GIỜ hoạt động. Java bỏ dòng này (parity). Muốn làm thật: override `baseReloadSpeed()` trong lớp Build, trả `efficiency * reloadMult`. Cũng như vậy `maxitoner.shootEffect` không có trên Turret v157 → bỏ (bài 24).
63. **Sửa `b.type` của đạn dùng chung (Maxitoner 3C):** `b.type.pierce/pierceCap/homingPower` đổi thẳng lên đối tượng BulletType dùng chung nên sau lần đầu 3C bắn, MỌI viên đạn Maxitoner đều xuyên/đuổi vĩnh viễn (JS và Java như nhau, giữ nguyên). Muốn đúng nên tạo bản `copy()` riêng cho 3C.
64. **Cách làm nhanh/tiết kiệm token cho file có hộp phúc lợi A/B/C:** gộp 3 khối UI gần giống nhau thành 1 hàm `perkBox(...)` + chép chuỗi bằng `splice2.py`. Mẹo: `sed 's/isEnglish()/isEn()/g'` trên bản JS tạm để marker `@@E:n@@` nhận ra hàm kiểm tra ngôn ngữ; marker `@@R:a-b@@` chép nguyên cả khối `if (isEn()) { if (perkA == 1) txtA = "..." ... }` vì nó đã là Java hợp lệ (chỉ cần tự khai báo `String txtA = "";`).

65. **Lỗi biên dịch thật từ CI (Maxitoner): `StatusEffect.update` ở v157 là `update(Unit unit, StatusEntry entry)`**, KHÔNG phải `(Unit, float time)` (cũ). JS `update(unit, time)` + `super$update(unit, time)` chạy được vì Rhino không kiểm tra kiểu tham số. → Java dùng `StatusEntry` (`import mindustry.entities.units.StatusEntry`). Quy tắc: khi override hàm của StatusEffect/BulletType/Block, tra lại chữ ký v157 thay vì chép theo tên tham số trong JS; `@Override` báo "does not override" nghĩa là sai chữ ký (hoặc hàm không tồn tại, bài 15/25).

66. **Trường giả không có thật trong BulletType (Nucleytor): `workspace: true`.** Không file JS nào khác trong mod dùng trường này và nó không khớp API thật của `BulletType` ở v157 — gần như chắc chắn là rác do tác giả gõ nhầm (có thể định viết `pierceBuilding` hay tên khác), Rhino chỉ âm thầm bỏ qua nên chưa từng có tác dụng (cùng dạng lỗi với bài 24/45). → Java bỏ hẳn khi gán trường cho `BasicBulletType`. Quy tắc chung: trước khi gán 1 trường lạ chưa từng thấy ở file khác, `grep` toàn bộ `scripts/*.js` xem có file nào khác dùng không — nếu chỉ 1 file dùng và không khớp tên API quen thuộc, nhiều khả năng là rác.
67. **Quy ước đặt tên vùng ảnh (atlas region) của mod Java "newex" (áp dụng từ Elorix/Holyder/Forstarsilum/Blaw... nhưng chưa ghi thành bài học riêng):** khi biên dịch thành mod Java, sprite được đóng gói kèm tiền tố tên mod (`"newex-" + tên-file-sprite-không-đuôi`), KHÁC với lúc chạy bằng JS/Rhino (khi đó `PerfCore.region(name)` JS tra thẳng tên file không tiền tố vẫn ra). Vì vậy mọi `PerfCore.region(...)` tra sprite riêng của mod (không phải sprite gốc Mindustry) trong Java PHẢI dùng bản 2 tham số `PerfCore.region("newex-" + tên, tên)` (ưu tiên có tiền tố, dự phòng không tiền tố) để chạy đúng cả khi đóng gói lẫn khi dev chạy rời. Nucleytor cũng áp dụng quy ước này cho `nucleytor-body/wing1/wing2/por1/por2/arr1/arr2/cored1/cored2`.
68. **Cơ chế "zero sát thương va chạm rồi tự gây true damage từng tick" (Nucleytor) vô hiệu hoá luôn cờ xuyên giáp (`pierce`/`pierceCap`) của chính BulletType.** `handleBullet` đặt `bullet.damage = 0` rồi mỗi tick trong `updateTile()` dùng `Groups.unit.intersect` quét quanh toạ độ đạn, cộng dồn `trueDmg` và gọi `b.remove()` NGAY khi trúng mục tiêu hợp lệ đầu tiên. Do đó dù hjson/JS khai `pierce: true, pierceCap: 3/5` (MK1/MK2), viên đạn trong thực tế chỉ gây sát thương thật đúng 1 lần trước khi tự xoá — cờ pierce chỉ còn ảnh hưởng tới việc engine có cho đạn "xuyên qua" về mặt va chạm gốc (không bị chặn bởi hitbox trước khi tới lượt quét thủ công) và số lần `hitEffect` hiển thị, KHÔNG làm tăng số mục tiêu nhận true damage. Giữ nguyên hành vi cũ khi chuyển sang Java (không "sửa" thành gây sát thương nhiều mục tiêu dù trông giống xuyên giáp). Áp dụng lại được cho `reflecounum.js`/`thermion.js` nếu hai file đó dùng chung pattern `activeBullets`/`handleBullet` kiểu này.
69. **`Units.nearbyBuildings`/`Groups.unit.intersect` nhận lambda kiểu `Cons<Building>`/`Cons<Unit>` trực tiếp, KHÔNG cần bọc `packCons(...)` như JS** (JS phải bọc vì Rhino không tự chuyển function thành interface Java); Java viết thẳng `Units.nearbyBuildings(x, y, r, b -> { ... })`. Khối `try { ... } catch (err) {}` bọc quanh vòng buff pháo đồng minh trong JS (nucleytor.js) chỉ để nuốt lỗi runtime do thiếu kiểm tra kiểu (ví dụ gọi nhầm trường trên `Building` không có) — khi viết lại bằng Java có kiểm tra kiểu tĩnh (ép `Turret.TurretBuild`) thì không còn cần try/catch (bỏ, tương tự tinh thần bài 32/45).

70. **Emperfum – `hit()`/`despawned()` ghi đè KHÔNG gọi super nên `splashDamage` của MỌI đạn (micro/sub/cluster/main MK2, MK2B) chưa từng gây sát thương nổ** (mô tả UI ghi "500 thô + 750 nổ" là sai so với thực tế). Java giữ nguyên (bài 19). Thêm: `Bullet.remove()` gọi `despawned()` khi `b.hit == false`, mà `hit()` ghi đè không đặt `b.hit = true` → đạn chính MK1/MK2/MK2B khi trúng sẽ nổ chùm 2 lần (hit + despawned), và `hitTile` còn gọi `super.hitTile → hit()` rồi nổ lần 3. Giữ nguyên parity. Muốn sửa: gọi `super.hit(b, x, y)` hoặc đặt cờ `b.hit = true` trước `b.remove()`.
71. **`emperfumMuzzleEffect.at(x, y, rot, Color)` (bài 2 lần nữa)**: `e.data || mặcĐịnh` luôn ra tím chính vì overload này gán `e.color`. Ngược lại `starDespawnEffect` đọc `e.color || tím` → `e.color` mặc định là trắng (không bao giờ null) nên sao nổ luôn trắng nếu hiệu ứng gọi `.at(x, y)`; Java giữ `e.color != null ? e.color : tím`.
72. **`this.recoil` trong Build (Emperfum draw)**: `TurretBuild` v157 không có trường `recoil` (chỉ block có) → JS đọc `undefined`, `!= null` sai nên `recoilOffset` luôn 0. Java gán thẳng `0f` kèm chú thích (cùng họ bài 30). `this.reloadCounter` thì có thật; `emperfum.reload` → `((ItemTurret) block).reload` (bài 21).
73. **Một lớp đạn tham số hoá thay hai bản sao (ground/air, tier)**: JS nhân bản 10 đối tượng `extend(BasicBulletType,{...})` gần y hệt; Java dùng `MicroBullet(boolean air)`, `SubBullet(boolean air)`, `ClusterBullet(boolean air)`, `MainBullet(int tier)` → ~300 dòng thay vì ~320 dòng JS lặp, dễ soát lệch số. Lệnh `Mathf.range(15)`/`Mathf.random(10, 20)` đổi thành `15f`/`10f, 20f` (bài 20). Khối chuỗi UI chép bằng `splice.py` marker `//@@JS a b` (đổi `let` đầu dòng → `String`) nên không gõ lại ~6 KB tiếng Việt/Anh; đối chiếu 125 chuỗi literal JS đều có trong Java.
74. **Lỗi CI thật (Nucleytor): `circleCheck` không phải trường của BasicBulletType v157** (cùng dạng rác như `workspace`, bài 66; Rhino bỏ qua im lặng). → Bỏ. Mỗi lần thêm trường lạ lấy từ object literal JS đều phải nghi ngờ.
75. **Lỗi CI thật (Nucleytor): `armor()` không tồn tại trên `Building`** (armor là trường của `Block`; chỉ `Unit` có `armor()`). `@Override armor() { return super$armor() + 20 }` của JS chưa từng chạy → giáp +20 khi khuếch đại KHÔNG có thật. Java bỏ override. Muốn làm thật: trừ sát thương nhận vào trong `damage(float)`/`handleDamage`. Gặp lại bài 15/25: `@Override` báo "does not override" = hàm không có ở v157.
76. **Tyteryi – hjson dùng khoá `ammo:` (không phải `ammoTypes`)**: ContentParser gọi `ammo(...)` nên THAY CẢ map đạn (silicon 67.5 / copper 33.75) sau khi Java `ammoTypes.put(silicon, kiếm MK1)`. Vô hại vì `shoot()` JS/Java tự `create` đạn kiếm và KHÔNG gọi `useAmmo()` (pháo không tốn đạn, chỉ cần `hasAmmo`). `this.inaccuracy` trên Build là trường rác (v157 chỉ có ở Block) → bỏ. `block.reload = 14/34` gán MỖI TICK lên block dùng chung nên nhiều pháo khác tier ghi đè nhau (giữ nguyên). hjson đặt `size 4, range 320, reload 34` thắng code; `range()` của lớp Build quyết định tầm thật.
77. **Đạn trúng đích (`hitEntity(b, entity, health)`)**: `entity` là `Hitboxc`; `entity.maxHealth`/`entity.damage` → ép `Healthc` (`maxHealth()`, `damage(float)`), `entity.x/y` → `Posc.getX()/getY()`, `entity.isGalileoJS` → `instanceof LeveledUnit` (bài 15), `entity.type.name` → ép `Unit`. `b.owner.getTier !== undefined` / `addCharge !== undefined` → `b.owner instanceof TyterYiBuild`. `Fx.itemTransfer.at(x, y, 6, Color)` là overload (x, y, rot, Color) nên giữ `6f`.
78. **Gộp lớp đạn bằng tham số**: 14 object `extend(BasicBulletType,…)` → `FlatSword(…, wave)` (micro/mini/mưa) + `SwordBullet(…, hpFrac, abetaHp)` (6 kiếm chính). Hai vòng for của `jigsawAssembleFx` gộp thành vòng `side`; seed `Mathf.randomSeed(seed, -45f, 45f)` dùng float (bài 20). `Math.floor(x) + "%"` trong JS in số nguyên → Java `Mathf.floor(x) + "%"`. Splice nay chép MỌI dòng bắt đầu bằng `let` (đổi → `String`), tham số thứ 3 là tên file JS.

79. **DTG Soldern – `this.isShooting` thiếu `()` (lần 7, hai chỗ).** (a) Dòng bắn shotgun cận chiến `targetInShotgunRange && this.isShooting && this.hasAmmo()`: JS luôn truthy nên chỉ còn `hasAmmo()`; (b) khiên `this.isShooting && this.shieldHealth > 0`: JS khiên luôn bung (không cần bắn) dù hjson ghi "chỉ dựng khiên khi đang bắn". Java gọi `isShooting()` thật cho cả hai (cùng hướng bài 10/17/35/52/58/61): khiên và shotgun chỉ chạy khi đang bắn. Muốn y hệt JS cũ: bỏ `isShooting() &&` ở hai chỗ. Cân bằng thay đổi rõ ở khiên (trước đây luôn bung và hấp thụ đạn).
80. **DTG Soldern – chi tiết khác.** `Vars.control.input.selected` KHÔNG có ở `InputHandler` v157 (chỉ có `selectedUnits`, `config`…) nên khối vẽ vòng tròn phụ trong `draw()` JS chưa từng chạy; vòng tròn đã do `drawSelect()` vẽ → Java bỏ khối đó. `block.basePrefix` không tồn tại (bài 13) → `super.draw()` thẳng. `PerfCore.region(modName + ...)` gọi mỗi khung hình → Java tra một lần ở lần vẽ đầu (cờ `regionsReady`) bằng `PerfCore.region("newex-" + tên, tên)` (bài 67); ảnh chưa có (`sub-left/right/main/top`…) thì `found()` sai như JS. `Color.valueOf("#ff1744")` gọi mỗi tick trong `updateTile/draw` → hằng `static final Color`. `this.maxHealth = x` ở `setTier` là hợp lệ vì `Building.maxHealth` là trường thật (`transient float`, kiểm tra bằng javap). `block.reload = 40/60` gán mỗi tick lên block dùng chung (giữ nguyên, bài 49/76); `hjson: reload 20, range 360, health 5500, ammoTypes silicon` nạp sau Java nên thắng giá trị trong code, nhưng `range()` của Build và `reload` mỗi tick quyết định giá trị thật.
81. **Công cụ / quy trình kiểm tra (quan trọng).** (a) Sandbox có mạng tới github.com + release-assets.githubusercontent.com nên TẢI ĐƯỢC jar: `curl -sL -o server-release.jar https://github.com/Anuken/Mindustry/releases/download/v157.4/server-release.jar` (16 MB). Từ giờ luôn tải jar trước rồi biên dịch cả `src/`, không chỉ kiểm tra cú pháp (bài 22 chỉ dùng khi thật sự không có mạng). (b) Biên dịch: `mkdir -p /tmp/out && java -m jdk.compiler/com.sun.tools.javac.Main -nowarn -encoding UTF-8 -cp server-release.jar -d /tmp/out $(find src -name '*.java') > build.log 2>&1; echo exit=$?; grep -c error: build.log` — phải xem `exit=` và `ls /tmp/out/newex` có file `.class`, đừng chỉ `grep error` (lọc rỗng KHÔNG chứng minh đã biên dịch). (c) Tra chữ ký API v157: `java -m jdk.jdeps/com.sun.tools.javap.Main -p -cp server-release.jar mindustry.gen.Building | grep tênHàm` (không có lệnh `javap` riêng; dùng module jdk.jdeps). Tra trước khi `@Override`/gán trường (bài 15/25/65). (d) Quy trình chuyển file rẻ token: sed tạm `\bisEn\b` → `isEn()` trên bản JS sao chép, rồi `splice2.py` với `@@E:n@@` (biểu thức song ngữ) và `@@R:a-b@@` (khối `if (isEn()) {return ...}`) cho mọi chuỗi UI; chỉ gõ tay phần logic. Đưa riêng phần logic của `updateTile`/`draw` vào file `.part` rồi ghép bằng Python. Hộp nâng cấp: tách `reqSnapshot()` thành phương thức trả `String` để `@@R@@` chép được nguyên văn `return ...`.

82. **Emperical – `hitTile` JS sai chữ ký.** v157 chỉ có `hitTile(Bullet, Building, float x, float y, float health, boolean direct)`. JS khai `hitTile(b, tile, health)` và gọi `this.super$hitTile(b, tile, health)` (3 tham số) → không khớp overload nào. Java override đúng bản 6 tham số, gọi `super.hitTile(...)` rồi `handleCustomHit` với Building trúng (đạn Laser/Lightning giữ nguyên JS: KHÔNG gọi super). Hệ quả: sát thương %MaxHP / nổ lan MK2B nay thật sự chạy khi trúng công trình. `hitEntity(Bullet, Hitboxc, float)` 3 tham số đúng nên giữ.
83. **Emperical – `Lines.poly` thiếu số cạnh.** `DrawAura` JS có `Lines.poly(x2, y2, radius / 2, Time.time)` (4 tham số: bị hiểu là sides = radius/2, radius = Time.time → đa giác khổng lồ tăng dần); hai dòng trên đó là tam giác `(x, y, 3, radius, góc)`. Java sửa thành `Lines.poly(x2, y2, 3, radius / 2f, Time.time)` và `(..., Time.time + 180f)` (tam giác xoay quanh vòng nhỏ). Muốn y hệt JS cũ cần `Lines.poly(x2, y2, (int)(radius/2), Time.time)` nhưng gần như chắc chắn là lỗi gõ thiếu đối số.
84. **Emperical – `isShooting` thiếu `()` (lần 8, hai chỗ) + API.** `this.isShooting && this.hasAmmo()` luôn truthy → JS bắn liên tục khi chỉ cần còn đạn, không cần mục tiêu; Java gọi `isShooting()` thật (bài 10/17/79). `impulse(float,float)` nằm ở `Physicsc` (không có ở `Velc`) → kiểm tra `entity instanceof Physicsc`. `backColor` chỉ có ở `BasicBulletType` (bài 33a) → ép kiểu khi truyền cho `SplashFx.ring`. Hai StatusEffect `paralyzed`/`oppressive` tạo bằng `new StatusEffect(...)` trong `init()` (loadContent, nhận tiền tố mod). `EmpericalExplosionCircleEffect` khai báo nhưng không dùng → bỏ. **Lưu ý:** bản `emperical.js` trong zip này CHƯA có cơ chế annihilate/overheat mới; nếu bạn đã thêm ở bản JS khác, gửi lại file để mình cập nhật Java.

85. **Indeniter – `createExplosionEffect(radius)` bỏ qua màu.** JS gọi `createExplosionEffect(150, "#00ffcc")` nhưng hàm chỉ nhận `radius` → mọi vòng nổ (perk 5/6/góc) đều trắng. Java: `SplashFx.ring(x, y, radius)` y hệt (không truyền màu). `global.bemodExplosionFx` không nơi nào gán → nhánh đó bỏ; luôn dùng vòng 150. `trI(key)` ~50 khoá song ngữ được sinh tự động bằng script từ JS (`key === "x"` → `key.equals("x")`), không gõ tay.
86. **Indeniter – `Vars.world.build(id)` sai (perk 5 buff 4 pháo).** JS lưu `cornerBuffedTurrets[targetBuild.id]` (id THỰC THỂ) rồi tra `Vars.world.build(parseInt(id))` (nhận VỊ TRÍ ô đã đóng gói) → gần như luôn null → mục bị xoá ở lần `UnitDamageEvent` kế tiếp, buff gán Bemod 35% không bao giờ chạy. Java tra bằng `Groups.build.getByID(id)` (đúng ý định). Muốn như JS cũ: đừng đăng ký sự kiện này. `range()` của pháo được buff lấy từ `ItemTurretBuild.range()` (không có `Building.range()`).
87. **Indeniter – `this.reload` không tồn tại ở Build.** `setTier` gán `this.reload = 60/pMult, 52/pMult, 180/pMult` nhưng Build v157 không có trường `reload` (chỉ block có; Rhino tạo thuộc tính JS rác) → tốc bắn theo tầng THỰC TẾ do hjson (`reload: 20`) quyết định, không phải 60/52/180. Java bỏ dòng gán (giữ hành vi), chỉ giữ `health`/`maxHealth`. Muốn tốc bắn theo tầng thật: đổi `turret.reload` hoặc dùng `reloadCounter`/`baseReloadSpeed()`. Cùng họ bài 62. `TurretBuild.version()` = 1 sẵn có nên `revision >= 1` ở `read()` đọc được byte perk (không cần override `version()`).
88. **Indeniter – `isShooting` thiếu `()` (lần 9, ba chỗ) + API.** `target != null || this.isShooting` (luôn truthy → cánh luôn bung), `(this.isShooting || this.isBursting) && hasAmmo()` (nạp năng lượng khi chỉ cần có đạn), `perk == 4 && hasAmmo() && this.isShooting` (đạn phụ bắn không cần mục tiêu). Java gọi `isShooting()` thật. `backColor` chỉ ở `BasicBulletType` (bài 33a). Đạn tuỳ biến: lớp `IBullet extends BasicBulletType` ghi đè `hitEntity` rồi gọi `handleBulletHit` của pháo chủ (chỉ khi `other instanceof Unit`). Biến bị lambda sửa (`furthestTarget`, `maxDist`) → mảng 1 phần tử (bài 14). `Seq.sort(Floatf)` thay `candidates.sort((a,b)=>…)`; `timer.get(0, 300f)` dùng Interval có sẵn.

89. **Galaxvorram – các lỗi JS (Java đã sửa/ghi chú).** (a) `galaxvorram.powerCapacity = 10000`, `targetBuildings = true`, `acceptItem = func(...)` KHÔNG phải trường/phương thức của Block/Turret v157 → chỉ là thuộc tính JS, không tác dụng (Java: hằng `POWER_CAP = 10000f`; `acceptItem` thật nằm ở Build và đã được ghi đè). `b.block.powerCapacity || 4000` luôn ra 4000 → hằng `OTHER_POWER_CAP`. (b) `pinfyrLaser.create(..., 1.0, 1.0)` truyền damage = 1.0 (tham số thứ 6 của `create` là DAMAGE, không phải hệ số) → tia Pinfyr chỉ gây 1 sát thương dù khai 2002; Java truyền `-1f` (= dùng damage của loại đạn: 2002). Muốn như cũ: truyền `1f`. (c) `armorPiercing: true` không phải trường BulletType (chỉ có `pierceArmor`) → bỏ. (d) `vTargetWarningFx.at(x, y, 0, ammo.c1)` chọn overload `at(..., Color)` (đặt `e.color`, không phải `e.data`) nên hiệu ứng luôn dùng tím mặc định; Java ép `(Object)` để màu theo loại đạn (mảng `colors` truyền cho các hiệu ứng khác vốn đã đi qua `data`). (e) `pinfyrList.each((b, i) => …)`: `Seq.each` chỉ truyền 1 đối số nên `i` undefined → hộp thông tin ghi "Pinfyr Block #NaN"; Java dùng chỉ số vòng lặp thật. (f) `createPinfyrDynamicLaser` tạo một loại đạn mới mỗi phát → Java dùng chung 1 `LaserBulletType` và đặt `length` trước mỗi `create`. (g) `StatusEffect.update` ở v157 có chữ ký `(Unit, StatusEntry)`; `turnToTarget(float)` là `protected`; `Damage.damage(team,x,y,r,dmg,air,ground)` có bản 7 tham số.
90. **Galaxvorram – `isShooting` thiếu `()` (lần 10).** `canShoot = this.isShooting` (điều khiển trực tiếp) và `syncChainCommand(..., this.isShooting, ...)`: JS luôn truthy → khi người chơi/logic điều khiển pháo là bắn ngay khi đã ngắm đúng và đủ điện+đạn, và lệnh bắn lan sang pháo kề; Java dùng `isShooting()` thật (chỉ bắn khi đang bấm bắn). `itemObs` (item `newex-obs`) nạp sau `create()` nên tra lười bằng `obs()`; `AMMO_DATA` được sinh tự động từ JS bằng script (chỉ `reqItem` của loại 5 để null, dùng `obs()` lúc chạy như JS). `range()` gọi quét công trình mỗi lần (giữ nguyên như JS, bán kính nhỏ).

91. **Flarmed – kiểu (b) (bài 29b), đạn phun lửa.** `flarmed.hjson` có `type: ItemTurret` nên Java KHÔNG `new` block, chỉ `Events.on(ContentInitEvent)` rồi `ammoTypes.put(coal/pyratite, new FlameBullet(...))` (hjson `ammoTypes: {}` rỗng nên không bị ghi đè). Hai StatusEffect mới `bong`, `viem-hoa` tạo bằng `new StatusEffect(...)` trong `Flarmed.init()` (như Emperical, bài 84). `update` của `viem-hoa` dùng đúng chữ ký v157 `update(Unit, StatusEntry)` (bài 65). Hai JS `makeFlame(dmg, burnTime, range, lifetime)` gộp thành 1 lớp `FlameBullet`; đặt tên trường `reach`/`burn`/`life`/`dmg` để KHÔNG trùng `range`/`damage`/`lifetime` của BulletType. Chủ đạn ép `Turret.TurretBuild` (có `rotation` công khai để khóa hướng); `b.data` lưu `Float`. `inCone` (closure JS) → hàm static nhận đủ tham số vì lambda chỉ dùng biến `final`.
92. **JS cập nhật sau khi đã chuyển → đồng bộ rẻ token.** Không cần đọc lại cả file: quét mọi chuỗi/hằng số trong JS mới mà Java không có (script nhỏ regex), rồi chỉ đọc các dòng lệch + `grep phase|mobile`. Archueng: JS thêm chế độ MOBILE bấm 4 lần (phase 1 chờ chạm → 2 đặt/đổi hướng, nút "KHÓA" → 3 đã khóa, nút "TUNG" → bấm nữa mới tung). Java thêm `firePhase()`, `touchWorld()` (`Core.input.mouseWorld`), `touchOnUi()` (`Core.scene.hasMouse`), vẽ đường ngắm theo `locked = phase == firePhase()`, nhãn nút 3 trạng thái, và `touchUp` xử lý `phase == 2 && mobile → 3`. PC giữ nguyên 3 lần bấm. Lưu ý: `phase` là `static int`, các chỗ khác KHÔNG được giả định `phase == 2` là "sẵn sàng tung" (dùng `firePhase()`).
93. **Lần này KHÔNG biên dịch thật (sandbox tắt mạng, không tải được server-release.jar).** Chỉ kiểm cú pháp (bài 22): không có lỗi ngoài "cannot find symbol/does not override". Cần chạy lệnh bài 81 trước khi push; chỗ dễ lỗi nhất: `Draw.color(Color, Color, Color, float)` của JS đã thay bằng hàm `mix3` tự cài (nội suy a→b→c, có thể lệch nhẹ so với overload gốc), `Turret.TurretBuild.rotation` / `Bullet.timer`/`Bullet.data` truy cập công khai, `StatusEffect.damage/permanent/color` gán trường, `Core.scene.hasMouse(float,float)`.
94. **Aether Spiral (họ Endyr, kiểu b).** (a) Tái dùng `Endyr.getItemBySlotType/getSlotTypeByItem` (cùng package) thay vì chép lại; chỉ viết lại `statDesc` (sallowyr khác: "+1 Đạn/lần bắn") và `buildSlotUI/showItemSelectionDialog` cho `AetherBuild`. Khối chuỗi thống kê ~40 dòng song ngữ được CHÉP bằng script từ JS (đổi `this.getItemCount`→`getItemCount`, `let`→`String`), không gõ tay. (b) **Đạn tạo ở `create()` (loadContent), KHÔNG tạo trong ContentInitEvent:** JS tạo `extend(BasicBulletType,{init(){...}})` lúc nạp script nên `init()` được game gọi; đạn tạo sau `ContentInitEvent` thì `init()` không bao giờ chạy → mất hết giá trị đặt trong `init()`. Đạn đặt giá trị trong `init()` sau `super.init()` đúng như JS. (c) `this.isShooting` thiếu `()` (lần 11): JS luôn truthy nên khi người chơi điều khiển pháo tự bắn không cần bấm; Java gọi `isShooting()` thật. (d) Buff "Tốc độ bắn" chỉ nhân `dt` của đồng hồ sạc, KHÔNG nhân `reload` thật (JS không override `baseReloadSpeed`) → giữ nguyên. `rotateSpeed: 5.0` là trường tự thêm trên đối tượng Build JS → hằng `ROT_SPEED`. (e) `hitEntity` của 3 loại đạn: `super.hitEntity` đã gây sát thương rồi JS còn `applyDamageWithStarlight` thêm một lần nữa (đòn kép, giữ nguyên); `other` là `Hitboxc` → kiểm `instanceof Unit`. Sao Đen Tím gây thêm 50% máu tối đa. (f) `topRegion.found` thiếu `()` (bài 51) → dùng `found()`, tra sprite một lần (bài 67). (g) `Mathf.rand.random(360)`/`range(20)` → float (bài 20); `Mathf.randomSeed(id+i, 360)` → `randomSeed(seed, 0f, 360f)`. (h) Hai sóng xung kích gộp hàm `ellipseLine`. (i) `BlockDestroyEvent` dùng `Groups.build.each` + danh sách thay mảng JS; `list.remove(last)` thay `pop()`.

 (dễ/nhanh → khó/lâu)
1. ~~lazash.js~~ (xong)
2. ~~nethyr.js~~ (xong)
3. ~~hitekalum.js~~ (xong)
4. ~~lyvervon-system.js~~ (xong)
5. ~~dor.js~~ (xong)
6. ~~overyr.js~~ (xong)
7. ~~forstarsilum.js~~ (xong)
8. ~~maxitoner.js~~ (xong)
9. ~~plasanod.js~~ (xong)
10. ~~nucleytor.js~~ (xong)
11. ~~emperfum.js~~ (xong)
12. ~~tyteryi.js~~ (xong)
13. ~~dtg-soldern.js~~ (xong)
14. ~~emperical.js~~ (xong)
15. ~~indeniter.js~~ (xong)
16. ~~galaxvorram.js~~ (xong)
17. ~~aether-spiral.js~~ (xong)
18. cos.js (1222 dòng, dòng thực tế hiện ~1221)
19. swordoder.js (1409 dòng)
20. survivor-skills.js (1493 dòng, dòng thực tế hiện ~1492)
21. loadout-selection.js (1508 dòng, dòng thực tế hiện ~1737 — đã tăng, kiểm tra lại trước khi làm)
22. reflecounum.js (1541 dòng)
23. cargo-drone.js (1814 dòng, dòng thực tế hiện ~2560 — đã tăng nhiều, kiểm tra lại trước khi làm)
24. thermion.js (1866 dòng)
25. newmode-pvp.js (7993 dòng, dòng thực tế hiện ~8833 — đang được chỉnh ở nhánh khác, xem areas/neowex-java-port)
