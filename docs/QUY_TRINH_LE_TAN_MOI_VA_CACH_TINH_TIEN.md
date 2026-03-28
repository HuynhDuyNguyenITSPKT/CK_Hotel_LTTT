# Quy trinh le tan moi va cach tinh tien

## 1) Muc tieu thay doi

- Dat phong cho phep chon nhieu phong trong mot lan tao booking.
- Ngay nhan va ngay tra duoc quan ly tai `DatPhong` (booking-level), khong phai xu ly theo tung detail rieng le.
- Sau khi tao booking: khong sua/khong them detail nua, chi co 2 huong:
  - Huy booking (neu con PENDING/CONFIRMED)
  - Tao booking moi
- Cac form xu ly chinh (check-in, check-out, cap nhat trang thai phong, thao tac booking) duoc dua vao dialog modal.
- Neu them nham dich vu, le tan co the xoa dich vu va he thong cap nhat tong tien lai.

## 2) Luong dat phong

### Buoc 1: Tao booking moi

- Mo dialog "Them khach hang / dat phong".
- Nhap thong tin khach hang: ten, sdt, email.
- Chon ngay nhan, ngay tra.
- Chon nhieu phong AVAILABLE.
- Bam luu.

### Buoc 2: Kiem tra hop le

He thong kiem tra:

- Ngay tra phai sau ngay nhan.
- Phai chon it nhat 1 phong.
- Moi phong phai o trang thai AVAILABLE.
- Moi phong khong duoc trung lich voi booking khac (bo qua booking da CANCELLED).

### Buoc 3: Tao du lieu

- Tao `DatPhong` voi:
  - `ngayDat`
  - `ngayNhan`
  - `ngayTra`
  - `trangThai = CONFIRMED`
- Tao nhieu `ChiTietDatPhong` (moi phong 1 dong) de luu danh sach phong cua booking.

## 3) Luong xem chi tiet va huy booking

- Tu danh sach booking, bam "Xem chi tiet" de mo dialog chi tiet.
- Dialog hien danh sach phong thuoc booking (chi tiet dat phong).
- Neu booking dang o `PENDING/CONFIRMED`: cho phep bam "Huy booking".
- Neu booking da `CHECKED_IN/CHECKED_OUT/CANCELLED`: khong cho huy.

## 4) Luong check-in

### Dieu kien cho phep

- Booking phai o `PENDING` hoac `CONFIRMED`.
- Ngay hien tai phai nam trong khoang luu tru: `ngayNhan <= homNay < ngayTra`.
- Booking phai co detail phong hop le.
- Tat ca phong trong booking phai dang `AVAILABLE`.
- Khong duoc trung voi booking khac dang `CHECKED_IN` tren cung phong.

### Ket qua

- Booking -> `CHECKED_IN`.
- Tat ca phong trong booking -> `OCCUPIED`.

## 5) Luong check-out

### Dieu kien cho phep

- Booking phai dang `CHECKED_IN`.
- Tat ca phong cua booking phai dang `OCCUPIED`.
- Hoa don phai duoc thanh toan du (con lai = 0).

### Ket qua

- Booking -> `CHECKED_OUT`.
- Tat ca phong trong booking -> `CLEANING`.

## 6) Luong them/xoa dich vu

### Them dich vu

- Chon booking dang o (`CHECKED_IN`).
- Chon dich vu + so luong tren giao dien.
- He thong tao dong `SuDungDichVu`.
- He thong cap nhat tong tien hoa don ngay.

### Xoa dich vu (neu them nham)

- Trong danh sach dich vu cua booking, bam "Xoa".
- Xac nhan trong dialog.
- He thong xoa dong `SuDungDichVu`.
- He thong cap nhat tong tien hoa don ngay.

## 7) Kiem soat trang thai phong

Khi cap nhat tay trang thai phong:

- Neu phong dang co booking `CHECKED_IN` hieu luc:
  - Chi cho phep phong o `OCCUPIED`.
  - Cam chuyen sang AVAILABLE/CLEANING/MAINTENANCE.
- Neu phong khong co booking `CHECKED_IN` hieu luc:
  - Cam chuyen sang `OCCUPIED`.

## 8) Cong thuc tinh tien tong

Gia su:

- Danh sach phong trong booking: `details`
- Danh sach dich vu da dung: `services`
- Danh sach khuyen mai ap dung: `promotions`

### 8.1 Tien phong

- So dem o:
  - `soDem = max(1, ngayTra - ngayNhan)`
- Tien tung phong:
  - `tienPhongItem = giaPhongCoBan * soDem`
- Tong tien phong:
  - `tongTienPhong = sum(tienPhongItem)`

### 8.2 Tien dich vu

- Tien tung dich vu:
  - `tienDvItem = donGiaDv * soLuong`
- Tong tien dich vu:
  - `tongTienDichVu = sum(tienDvItem)`

### 8.3 Tong truoc khuyen mai

- `tongTruocKM = tongTienPhong + tongTienDichVu`

### 8.4 Ap dung khuyen mai

Moi khuyen mai duoc ap dung lan luot:

- Neu loai `PERCENT`:
  - `mucGiam = tongHienTai * tyLe / 100` (gioi han toi da 100%)
- Neu loai `AMOUNT`:
  - `mucGiam = giaTri`
- Cap nhat:
  - `tongHienTai = max(0, tongHienTai - mucGiam)`

Ket qua:

- `tongHoaDon = tongSauKhuyenMai`

### 8.5 Thanh toan va cong no

- `tongDaThanhToan = sum(cac giao dich thanh toan)`
- `soTienConLai = max(0, tongHoaDon - tongDaThanhToan)`

Trang thai thanh toan:

- `soTienConLai == 0` -> da thanh toan du
- `0 < tongDaThanhToan < tongHoaDon` -> thanh toan mot phan
- `tongDaThanhToan == 0` -> chua thanh toan

## 9) Luu y van hanh

- Khong sua detail booking cu. Neu thong tin sai, huy booking cu va tao booking moi.
- Moi thay doi anh huong den tien (them/xoa dich vu, doi KM) deu phai cap nhat tong hoa don ngay.
- Check-in/check-out luon uu tien kiem tra trang thai phong va trang thai booking truoc khi ghi du lieu.
