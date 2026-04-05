# HỆ THỐNG QUẢN LÝ KHÁCH SẠN

Spring Boot MVC + Thymeleaf + Hibernate/JPA + MySQL

Chào bạn, mình đã chỉnh lại toàn bộ nội dung file README.md sang tiếng Việt chuẩn để bạn có thể dùng trực tiếp cho phần nộp báo cáo/giảng viên.

## 1. Giới thiệu đề tài

Đây là dự án xây dựng phần mềm quản lý khách sạn theo mô hình web server-side rendering, phát triển bằng Java 17 và Spring Boot.

Hệ thống tập trung vào 3 nhóm vai trò:

- ADMIN: Quản trị hệ thống, tài khoản, danh mục, báo cáo.
- MANAGER: Giám sát vận hành, xem dashboard/báo cáo, cập nhật một số danh mục.
- RECEPTIONIST: Đặt phòng, check-in/check-out, thêm/xóa dịch vụ, thanh toán, áp khuyến mãi.

Nghiệp vụ cốt lõi xoay quanh các thực thể: DatPhong, ChiTietDatPhong, Phong, LoaiPhong, DichVu, SuDungDichVu, HoaDon, ThanhToan, KhuyenMai, KhachHang, NhanVien, TaiKhoan.

## 2. Công nghệ sử dụng

- Java 17
- Spring Boot 4.0.4
- Spring Web (MVC)
- Thymeleaf
- Spring Data JPA (Hibernate)
- Bean Validation
- MySQL 8+
- Maven / Maven Wrapper
- Apache POI (xuất Excel)
- OpenPDF (xuất PDF)
- Cloudinary (upload ảnh)
- JUnit 5 + Spring Boot Test

## 3. Kiến trúc hệ thống

Dự án tổ chức theo mô hình Controller - Service - Repository:

- View: Thymeleaf templates trong src/main/resources/templates
- Controller: Tiếp nhận request, phân quyền theo vai trò, điều hướng dashboard/tab
- Service: Xử lý nghiệp vụ, validation, tính tiền, báo cáo
- Repository: Truy vấn dữ liệu với Spring Data JPA
- Entity/DTO/Enum: Mô hình dữ liệu và dữ liệu truyền giữa các tầng
- Init/Config: Seed tài khoản mặc định, cấu hình Cloudinary

Luồng chính:

View (Thymeleaf) -> Controller -> Service -> Repository -> Database

Cơ chế xác thực/phân quyền hiện tại là session-based (HttpSession), chưa dùng Spring Security.

## 4. Chức năng chính

### 4.1. Đăng nhập / đăng xuất

- Đăng nhập tại /login với username + password.
- Chỉ cho phép tài khoản trạng thái ACTIVE.
- Điều hướng theo vai trò:
  - ADMIN -> /admin/dashboard
  - MANAGER -> /manager/dashboard
  - RECEPTIONIST -> /receptionist/dashboard
- Đăng xuất bằng /logout (xóa session).

### 4.2. Dashboard tổng hợp theo vai trò

- ADMIN: Dashboard tổng quan + quản trị tab (accounts, employees, rooms, room-types, services, promotions, customers).
- MANAGER: Thống kê tổng quan, trend booking, báo cáo hóa đơn/thanh toán, cập nhật danh mục được phép.
- RECEPTIONIST: Tổng quan vận hành lễ tân, check-in/check-out, đặt phòng, đang ở thêm dịch vụ, hóa đơn thanh toán, quản lý phòng.

### 4.3. Quản trị hệ thống (ADMIN)

- Quản lý tài khoản: đổi trạng thái ACTIVE/INACTIVE/LOCKED.
- Quản lý nhân viên: tạo nhân viên + tài khoản, cập nhật vai trò.
- CRUD khách hàng.
- CRUD loại phòng, phòng, dịch vụ, khuyến mãi.
- Lọc/tìm kiếm và phân trang trên dashboard.

### 4.4. Nghiệp vụ lễ tân: Đặt phòng

- Tra cứu khách hàng theo SĐT.
- Tạo booking với nhiều phòng trong một lần thao tác.
- Validate:
  - ngayTra > ngayNhan
  - ngayNhan >= hôm nay
  - phòng phải AVAILABLE
  - không trùng lịch booking khác (PENDING/CONFIRMED/CHECKED_IN/CHECKED_OUT)
- Quản lý khách hàng theo SĐT/email để tránh trùng/lệch danh tính.
- Tạo DatPhong trạng thái CONFIRMED + các dòng ChiTietDatPhong.
- Cho phép hủy booking trong trạng thái PENDING/CONFIRMED.

### 4.5. Nghiệp vụ lễ tân: Check-in / Check-out

- Check-in:
  - booking phải ở PENDING/CONFIRMED
  - hôm nay trong khoảng ngayNhan <= homNay < ngayTra
  - phòng booking phải AVAILABLE
  - không trùng với booking khác đang CHECKED_IN
  - kết quả: booking -> CHECKED_IN, phòng -> OCCUPIED
  - đồng bộ/cập nhật tổng tiền hóa đơn theo thực tế
- Check-out:
  - booking phải CHECKED_IN
  - tất cả phòng trong booking phải OCCUPIED
  - hóa đơn phải thanh toán đủ
  - kết quả: booking -> CHECKED_OUT, phòng -> CLEANING

### 4.6. Nghiệp vụ lễ tân: Dịch vụ trong thời gian ở

- Thêm dịch vụ cho booking đang ở (áp dụng theo từng phòng hoặc tất cả phòng).
- Xóa dịch vụ đã thêm nếu thao tác nhầm.
- Mọi thay đổi dịch vụ đều cập nhật lại tổng tiền hóa đơn.

### 4.7. Nghiệp vụ lễ tân: Hóa đơn / thanh toán / khuyến mãi

- Ghi nhận thanh toán theo phương thức CASH/CARD/TRANSFER/E_WALLET.
- Áp dụng mã khuyến mãi cho booking đang CHECKED_IN.
- Hỗ trợ thao tác kết hợp: áp khuyến mãi + thanh toán trong một bước.
- Theo dõi tổng đã thanh toán và số tiền còn lại để quyết định check-out.

### 4.8. Quản lý trạng thái phòng

- Cập nhật trạng thái phòng từ dashboard lễ tân.
- Có ràng buộc nghiệp vụ để tránh sai trạng thái khi phòng đang có khách lưu trú.

### 4.9. Báo cáo và xuất file

- ADMIN và MANAGER có báo cáo hóa đơn + thanh toán theo ngày/tháng/năm.
- Xuất báo cáo ra:
  - Excel (.xlsx)
  - PDF (.pdf)

## 5. Cấu trúc thư mục dự án

```text
.
|- pom.xml
|- mvnw / mvnw.cmd
|- database/
|  |- fulldatanhom11.sql
|- docs/
|  |- QUY_TRINH_LE_TAN_MOI_VA_CACH_TINH_TIEN.md
|- src/
|  |- main/
|  |  |- java/hcmute/system/hotel/cknhom11qlhotel/
|  |  |  |- config/
|  |  |  |- controller/
|  |  |  |- init/
|  |  |  |- model/
|  |  |  |- repository/
|  |  |  |- service/
|  |  |  |- stream/
|  |  |  |- util/
|  |  |- resources/
|  |  |  |- application.properties
|  |  |  |- templates/
|  |- test/
|- uml/
|  |- ActivityDiagram/
|  |- ClassDiagram/
|  |- package/
|  |- Seqụence/
|  |- Usecase/
```

Lưu ý: Tên thư mục sequence trong repo hiện tại là Seqụence (ký tự có dấu), giữ nguyên theo repo.

## 6. Hướng dẫn tạo cơ sở dữ liệu

### Bước 1: Tạo database MySQL

```sql
CREATE DATABASE hotelnhom11 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Bước 2: Nạp dữ liệu mẫu (khuyến nghị)

Import file:

- database/fulldatanhom11.sql

File này bao gồm cả cấu trúc bảng và dữ liệu mẫu.

### Bước 3: Cấu hình kết nối

Mở file src/main/resources/application.properties và sửa:

- spring.datasource.url
- spring.datasource.username
- spring.datasource.password

Mặc định trong code:

- url: jdbc:mysql://localhost:3306/hotelnhom11
- username: root
- password: 1234

### Bước 4: Lưu ý schema update

- spring.jpa.hibernate.ddl-auto=update

Ứng dụng sẽ cập nhật schema khi chạy.

### Bước 5: Seed tài khoản mặc định

Khi app start, AppInitializer gọi SeedDataService để seed user theo role nếu hệ thống chưa có.

## 7. Hướng dẫn chạy project

### Cách 1: Chạy bằng Maven Wrapper (Windows)

```bash
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

### Cách 2: Chạy bằng Maven đã cài sẵn

```bash
mvn clean package
mvn spring-boot:run
```

### Cách 3: Chạy trên IDE

- Mở project bằng IntelliJ IDEA / VS Code.
- Chờ Maven resolve dependencies.
- Run class: CkNhom11QlhotelApplication.

Sau khi chạy, truy cập:

- http://localhost:8080

### Chạy test

```bash
.\mvnw.cmd test
```

## 8. Tài khoản mẫu

Theo script SQL và cơ chế seed mặc định:

- ADMIN: username admin | password 123456
- RECEPTIONIST: username receptionist | password 123456
- MANAGER: username manager | password 123456

Lưu ý: Đăng nhập thất bại nếu tài khoản không ở trạng thái ACTIVE.

## 9. Phân quyền

- ADMIN:
  - Toàn quyền dashboard admin
  - Quản lý tài khoản, nhân viên, phòng, loại phòng, dịch vụ, khuyến mãi, khách hàng
  - Xuất báo cáo Excel/PDF
- MANAGER:
  - Xem dashboard quản lý + thống kê trend
  - Xem và xuất báo cáo Excel/PDF
  - Cập nhật một số danh mục: phòng, khuyến mãi, dịch vụ
  - Không xử lý đặt phòng/check-in/check-out/thanh toán
- RECEPTIONIST:
  - Đặt phòng, hủy booking hợp lệ
  - Check-in/check-out
  - Thêm/xóa dịch vụ
  - Thanh toán, áp khuyến mãi
  - Cập nhật trạng thái phòng theo ràng buộc nghiệp vụ
  - Không quản trị tài khoản/nhân viên hệ thống

## 10. UML

Các sơ đồ UML lưu trong thư mục uml/, gồm:

- Use Case Diagram
- Activity Diagram
- Sequence Diagram
- Class Diagram
- Package Diagram

Trong quá trình hoàn thiện, các sơ đồ booking/check-in đã được đối chiếu với code service/controller để đồng bộ logic.

## 11. Thành viên nhóm đề xuất

Bạn có thể chia vai trò như sau:

- Sinh viên 1: Thiết kế CSDL, Entity, Repository.
- Sinh viên 2: Giao diện Thymeleaf, Controller, luồng Dashboard.
- Sinh viên 3: Nghiệp vụ DatPhong/CheckInOut/ThanhToan, báo cáo Excel-PDF, UML, báo cáo môn.

## 12. Các điểm nổi bật của đồ án

- Kiến trúc rõ ràng Controller -> Service -> Repository.
- Phân quyền 3 vai trò rõ nghiệp vụ.
- Có ràng buộc nghiệp vụ khách sạn cho booking/check-in/check-out.
- Hỗ trợ upload ảnh (Cloudinary) cho phòng/loại phòng/dịch vụ.
- Hỗ trợ báo cáo và xuất file Excel/PDF.
- Có seed user mặc định để demo nhanh.

## 13. Hướng phát triển thêm

- Mã hóa mật khẩu bằng BCrypt (hiện tại đang so sánh plain text để phù hợp đồ án môn học).
- Áp dụng Spring Security + CSRF + session policy chặt chẽ hơn.
- Bổ sung audit log thao tác người dùng.
- Bổ sung thông báo đến hạn check-in/check-out.
- Thêm API layer (REST) để tách Frontend/Backend nếu cần.
- Viết thêm test nghiệp vụ (unit/integration) cho các luồng trong service.

## 14. Kết luận

Dự án minh họa một hệ thống quản lý khách sạn tương đối đầy đủ cho môi trường đồ án môn học, bao gồm quản trị hệ thống, vận hành lễ tân, báo cáo quản lý và ràng buộc nghiệp vụ thực tế.

Với Java 17 + Spring Boot + JPA + MySQL + Thymeleaf, kiến trúc hiện tại dễ tiếp tục mở rộng theo hướng sản phẩm hóa trong các giai đoạn tiếp theo.

## Phụ lục cấu hình Cloudinary

Để upload ảnh ổn định và an toàn hơn, nên cấu hình bằng biến môi trường thay vì hard-code:

- CLOUDINARY_CLOUD_NAME
- CLOUDINARY_API_KEY
- CLOUDINARY_API_SECRET

Nếu thiếu cấu hình, các thao tác upload ảnh sẽ báo lỗi trong service upload.
