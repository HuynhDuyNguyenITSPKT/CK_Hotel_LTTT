# Hướng dẫn luồng nghiệp vụ cho Lễ tân

Tài liệu này mô tả các bước thao tác cho vai trò lễ tân trên màn hình dashboard.

## 1. Tổng quan nhanh

Mục tiêu:
- Theo dõi nhanh số lượt check-in/check-out trong ngày.
- Theo dõi phòng sẵn sàng và booking đang xử lý.
- Xem danh sách sắp đến hạn để xử lý ngay.

Thao tác:
- Mở tab `Tổng quan nhanh` để xem các chỉ số và danh sách rút gọn.
- Nếu thấy booking đến hạn, chuyển sang tab `Check-in` hoặc `Check-out` để thao tác chi tiết.

## 2. Check-in

Điều kiện:
- Booking phải ở trạng thái `PENDING` hoặc `CONFIRMED`.
- Chưa qua hạn check-in.

Thao tác:
1. Mở tab `Check-in`.
2. Tìm booking theo mã đặt phòng/tên khách.
3. Bấm nút `Check-in`.

Kết quả hệ thống:
- Booking chuyển sang `CHECKED_IN`.
- Phòng được chuyển sang `OCCUPIED`.
- Nhân viên thao tác được gán vào booking.

## 3. Check-out và thanh toán (đúng logic nghiệp vụ)

Nguyên tắc bắt buộc:
- Không được check-out nếu chưa thanh toán đủ hóa đơn.
- Thu tiền đủ rồi mới cho check-out.

Thao tác thu tiền:
1. Mở tab `Check-out`.
2. Nếu khách dùng thêm dịch vụ trong thời gian ở, chọn dịch vụ + số lượng và bấm `Thêm dịch vụ`.
3. Xem cột `Còn lại` để biết số tiền cần thu sau khi cộng dịch vụ.
4. Nhập `Số tiền thanh toán` (không được lớn hơn công nợ còn lại).
5. Chọn `Phương thức` (CASH/CARD/TRANSFER/E_WALLET).
6. Bấm `Thu tiền`.

Thao tác check-out:
1. Đảm bảo cột `Còn lại` = 0.
2. Bấm `Check-out`.

Kết quả hệ thống:
- Booking chuyển sang `CHECKED_OUT`.
- Phòng chuyển sang `CLEANING`.
- Trạng thái thanh toán hiển thị đã đủ.

Lưu ý:
- Nếu booking chưa có hóa đơn, hệ thống sẽ tạo hóa đơn khi thu tiền lần đầu.
- Khi thêm dịch vụ cho khách đang ở, tổng hóa đơn được cập nhật theo entity `SuDungDichVu`.
- Nếu thu vượt số tiền còn lại, hệ thống từ chối và thông báo lỗi.

## 4. Quản lý phòng

Mục tiêu:
- Cập nhật trạng thái phòng theo vận hành thực tế tại quầy.

Thao tác:
1. Mở tab `Quản lý phòng`.
2. Chọn trạng thái mới cho phòng.
3. Bấm `Lưu`.

Khuyến nghị sử dụng trạng thái:
- `AVAILABLE`: phòng sẵn sàng đón khách.
- `OCCUPIED`: phòng đang có khách ở.
- `CLEANING`: phòng đang dọn dẹp sau check-out.
- `MAINTENANCE`: phòng bảo trì, tạm khóa đặt phòng.

## 5. Đặt phòng

Quy trình entity:
- `KhachHang` -> `DatPhong` -> `ChiTietDatPhong`.
- Một `DatPhong` có thể có nhiều `ChiTietDatPhong` (đổi phòng/chia nhiều giai đoạn lưu trú).

Thao tác:
1. Mở tab `Đặt phòng`.
2. Nhập thông tin khách (tên, SĐT, email).
3. Chọn phòng và khoảng ngày nhận/trả.
4. Bấm `Tạo đặt phòng`.

Kết quả hệ thống:
- Tạo mới hoặc cập nhật khách hàng theo SĐT/email.
- Tạo bản ghi `DatPhong` trạng thái `CONFIRMED`.
- Tạo bản ghi `ChiTietDatPhong` gắn với phòng và khoảng ngày.
- Kiểm tra trùng lịch trước khi lưu.

## 6. Hóa đơn và thanh toán

Mục tiêu:
- Theo dõi tình trạng hóa đơn theo từng booking.
- Kiểm soát số tiền đã thu và công nợ còn lại.

Thao tác:
1. Mở tab `Hóa đơn & thanh toán`.
2. Xem các cột `Tổng tiền`, `Đã thanh toán`, `Còn lại`.
3. Nếu còn nợ, bấm `Đi tới thu tiền` để chuyển nhanh sang tab `Check-out` và thu tiền.

Lưu ý nghiệp vụ:
- Tổng tiền hóa đơn được tính trên toàn bộ `ChiTietDatPhong` của một `DatPhong`, không chỉ một dòng chi tiết.
- Chỉ khi công nợ còn lại bằng 0 thì mới được check-out.

## Xử lý lỗi thường gặp

- `Chưa đến ngày nhận phòng`: Booking check-in sớm hơn ngày nhận.
- `Hóa đơn chưa thanh toán đủ`: Cần thu tiền thêm trước khi check-out.
- `Số tiền vượt quá công nợ còn lại`: Nhập lại số tiền thu nhỏ hơn hoặc bằng phần còn lại.
- `Khoảng ngày đặt trùng`: Chọn lại phòng hoặc đổi ngày nhận/trả.
