# NeoWex – Java, giai đoạn 1 (nền tảng)

Đã chuyển: PerfCore, NexNet, NexFree, MatchTimer, BuffEnemies, ShowHp, DamageDisplay, NexMod (khởi tạo).
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
