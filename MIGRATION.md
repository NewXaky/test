# Tiến độ chuyển NeoWex từ JS sang Java

Tổng: 97 file JS, 58648 dòng.

Đã xong: 75/98 file trong bảng (kèm main.js phần hành tinh trong NexMod). Còn lại 23 file (~32 000 dòng). Mọi file Java đã được biên dịch thử bằng javac --release 8 với server-release.jar của Mindustry v157.4 (không lỗi). reflecounum.js, thermion.js đang xử lý riêng; newmode-pvp.js (8645 dòng) làm từng phần.

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

## Thứ tự làm tiếp (dễ/nhanh → khó/lâu)
1. ~~lazash.js~~ (xong)
2. ~~nethyr.js~~ (xong)
3. hitekalum.js (719 dòng)
4. lyvervon-system.js (721 dòng)
5. dor.js (726 dòng)
6. overyr.js (740 dòng)
7. forstarsilum.js (741 dòng)
8. maxitoner.js (813 dòng)
9. plasanod.js (829 dòng)
10. nucleytor.js (860 dòng)
11. emperfum.js (873 dòng)
12. tyteryi.js (922 dòng)
13. dtg-soldern.js (925 dòng)
14. emperical.js (1013 dòng)
15. indeniter.js (1028 dòng)
16. galaxvorram.js (1058 dòng)
17. aether-spiral.js (1137 dòng)
18. cos.js (1222 dòng)
19. swordoder.js (1410 dòng)
20. survivor-skills.js (1493 dòng)
21. loadout-selection.js (1508 dòng)
22. reflecounum.js (1542 dòng)
23. cargo-drone.js (1814 dòng)
24. thermion.js (1867 dòng)
25. newmode-pvp.js (7993 dòng)
