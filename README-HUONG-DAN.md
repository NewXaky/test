# NeoWex – Java, giai đoạn 1 (nền tảng)

Đã chuyển: giai đoạn 1 (nền tảng), toàn bộ nhóm block và toàn bộ hệ thống dùng chung. Còn lại: pháo, đơn vị/drone, giao diện/chế độ chơi và phần dở của main.js. Xem MIGRATION.md.
Xem `MIGRATION.md` để biết toàn bộ danh sách còn lại.

## Cách dùng
1. Đặt các file này vào repo NeoWex (cạnh `content/`, `sprites/`, `maps/`...):
   `src/`, `build.gradle`, `settings.gradle`, `.github/workflows/build.yml`, `.gitignore`, `mod.hjson` (bản mới có 2 dòng `main` và `java`).
2. Sửa `mindustryVersion` trong `build.gradle` cho khớp bản game.
3. Push lên GitHub, xem Actions. Nếu lỗi, gửi log cho mình.

## Lưu ý quan trọng
- Giai đoạn 1 CHƯA chạy được như một mod hoàn chỉnh: các script JS còn lại vẫn đang gọi `global.PerfCore`, `global.NexNet`... mà các biến này giờ nằm trong Java.
  Vì vậy lần build này chỉ để kiểm tra biên dịch với API Mindustry thật. Đừng thay mod đang chạy bằng bản này.
- `build.gradle` KHÔNG đưa thư mục `scripts/` vào jar.

## Thứ tự khởi tạo trong NexMod (quan trọng khi ghép)
- `SplashFx.init()` phải gọi SAU CÙNG: JS gốc `require("splash-fx")` ở cuối main.js để nó chạy sau mọi chỉnh sửa đạn khác.
- `FixSave` cần các lớp unit tùy chỉnh (leolyr, elorix, suv-27, reykor) `implements LeveledUnit` khi chuyển nhóm unit.
- Các hook ClientLoadEvent hiện được đăng ký theo thứ tự init() của NexMod chứ chưa theo thứ tự require của main.js; cần rà lại khi ghép lần cuối.
