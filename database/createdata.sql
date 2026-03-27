USE hotelnhom11;

-- =====================================================
-- Seed script for test data (no insert into tai_khoan/nhan_vien)
-- Rerunnable: this script cleans old TEST seed data first.
-- =====================================================

SET @NV_ID := COALESCE(
	(SELECT id FROM nhan_vien WHERE role = 'ADMIN' LIMIT 1),
	(SELECT id FROM nhan_vien ORDER BY id LIMIT 1)
);

SELECT @NV_ID AS seed_nhan_vien_id;

START TRANSACTION;

-- -----------------------------------------------------
-- 1) Cleanup old TEST seed data
-- -----------------------------------------------------
DROP TEMPORARY TABLE IF EXISTS tmp_seed_customer_ids;
CREATE TEMPORARY TABLE tmp_seed_customer_ids AS
SELECT id
FROM khach_hang
WHERE email LIKE '%@test.seed2026.local';

DROP TEMPORARY TABLE IF EXISTS tmp_seed_booking_ids;
CREATE TEMPORARY TABLE tmp_seed_booking_ids AS
SELECT id
FROM dat_phong
WHERE khach_hang_id IN (SELECT id FROM tmp_seed_customer_ids);

DROP TEMPORARY TABLE IF EXISTS tmp_seed_invoice_ids;
CREATE TEMPORARY TABLE tmp_seed_invoice_ids AS
SELECT id
FROM hoa_don
WHERE dat_phong_id IN (SELECT id FROM tmp_seed_booking_ids);

DELETE FROM thanh_toan
WHERE hoa_don_id IN (SELECT id FROM tmp_seed_invoice_ids);

DELETE FROM hoa_don_khuyen_mai
WHERE hoa_don_id IN (SELECT id FROM tmp_seed_invoice_ids);

DELETE FROM hoa_don
WHERE id IN (SELECT id FROM tmp_seed_invoice_ids);

DELETE FROM su_dung_dich_vu
WHERE dat_phong_id IN (SELECT id FROM tmp_seed_booking_ids);

DELETE FROM chi_tiet_dat_phong
WHERE dat_phong_id IN (SELECT id FROM tmp_seed_booking_ids);

DELETE FROM dat_phong
WHERE id IN (SELECT id FROM tmp_seed_booking_ids);

DELETE FROM khach_hang
WHERE id IN (SELECT id FROM tmp_seed_customer_ids);

DELETE FROM phong
WHERE so_phong LIKE 'T6%';

DELETE FROM loai_phong
WHERE ten_loai LIKE 'LP_TEST_%';

DELETE FROM dich_vu
WHERE ten LIKE 'DV_TEST_%';

DELETE FROM khuyen_mai
WHERE ten LIKE 'KM_TEST_%';

-- -----------------------------------------------------
-- 2) Master data
-- -----------------------------------------------------
INSERT INTO khach_hang (ten, sdt, email) VALUES
('Nguyen Van An', '0909200001', 'an01@test.seed2026.local'),
('Tran Thi Binh', '0909200002', 'binh02@test.seed2026.local'),
('Le Quoc Cuong', '0909200003', 'cuong03@test.seed2026.local'),
('Pham Thu Dung', '0909200004', 'dung04@test.seed2026.local'),
('Hoang Minh Duc', '0909200005', 'duc05@test.seed2026.local'),
('Vo Anh Khoa', '0909200006', 'khoa06@test.seed2026.local'),
('Bui My Linh', '0909200007', 'linh07@test.seed2026.local'),
('Do Gia Minh', '0909200008', 'minh08@test.seed2026.local'),
('Dang Quynh Nhu', '0909200009', 'nhu09@test.seed2026.local'),
('Phan Bao Ngoc', '0909200010', 'ngoc10@test.seed2026.local'),
('Nguyen Hoang Phuc', '0909200011', 'phuc11@test.seed2026.local'),
('Tran Gia Han', '0909200012', 'han12@test.seed2026.local'),
('Le Thanh Dat', '0909200013', 'dat13@test.seed2026.local'),
('Vu Khai Tam', '0909200014', 'tam14@test.seed2026.local'),
('Phung Tuan Kiet', '0909200015', 'kiet15@test.seed2026.local');

INSERT INTO loai_phong (ten_loai, mo_ta, image_url, gia_co_ban) VALUES
('LP_TEST_STD_2026', 'Standard room for seed test', 'https://picsum.photos/seed/lp_std_2026/900/600', 600000.00),
('LP_TEST_DLX_2026', 'Deluxe room for seed test', 'https://picsum.photos/seed/lp_dlx_2026/900/600', 950000.00),
('LP_TEST_FAM_2026', 'Family room for seed test', 'https://picsum.photos/seed/lp_fam_2026/900/600', 1300000.00),
('LP_TEST_STE_2026', 'Suite room for seed test', 'https://picsum.photos/seed/lp_ste_2026/900/600', 2500000.00);

SET @LP_STD := (SELECT id FROM loai_phong WHERE ten_loai = 'LP_TEST_STD_2026' ORDER BY id DESC LIMIT 1);
SET @LP_DLX := (SELECT id FROM loai_phong WHERE ten_loai = 'LP_TEST_DLX_2026' ORDER BY id DESC LIMIT 1);
SET @LP_FAM := (SELECT id FROM loai_phong WHERE ten_loai = 'LP_TEST_FAM_2026' ORDER BY id DESC LIMIT 1);
SET @LP_STE := (SELECT id FROM loai_phong WHERE ten_loai = 'LP_TEST_STE_2026' ORDER BY id DESC LIMIT 1);

INSERT INTO phong (so_phong, image_url, trang_thai, loai_phong_id) VALUES
('T601', 'https://picsum.photos/seed/room_t601/900/600', 'AVAILABLE', @LP_STD),
('T602', 'https://picsum.photos/seed/room_t602/900/600', 'AVAILABLE', @LP_STD),
('T603', 'https://picsum.photos/seed/room_t603/900/600', 'CLEANING', @LP_STD),
('T604', 'https://picsum.photos/seed/room_t604/900/600', 'AVAILABLE', @LP_DLX),
('T605', 'https://picsum.photos/seed/room_t605/900/600', 'AVAILABLE', @LP_DLX),
('T606', 'https://picsum.photos/seed/room_t606/900/600', 'AVAILABLE', @LP_DLX),
('T607', 'https://picsum.photos/seed/room_t607/900/600', 'AVAILABLE', @LP_FAM),
('T608', 'https://picsum.photos/seed/room_t608/900/600', 'AVAILABLE', @LP_FAM),
('T609', 'https://picsum.photos/seed/room_t609/900/600', 'AVAILABLE', @LP_STE),
('T610', 'https://picsum.photos/seed/room_t610/900/600', 'AVAILABLE', @LP_STE),
('T611', 'https://picsum.photos/seed/room_t611/900/600', 'AVAILABLE', @LP_STE),
('T612', 'https://picsum.photos/seed/room_t612/900/600', 'MAINTENANCE', @LP_STE);

INSERT INTO dich_vu (ten, image_url, gia) VALUES
('DV_TEST_BREAKFAST_2026', 'https://picsum.photos/seed/dv_breakfast_2026/900/600', 120000.00),
('DV_TEST_LAUNDRY_2026', 'https://picsum.photos/seed/dv_laundry_2026/900/600', 80000.00),
('DV_TEST_SHUTTLE_2026', 'https://picsum.photos/seed/dv_shuttle_2026/900/600', 250000.00),
('DV_TEST_SPA_2026', 'https://picsum.photos/seed/dv_spa_2026/900/600', 500000.00),
('DV_TEST_MINIBAR_2026', 'https://picsum.photos/seed/dv_minibar_2026/900/600', 180000.00),
('DV_TEST_LATE_CHECKOUT_2026', 'https://picsum.photos/seed/dv_late_checkout_2026/900/600', 300000.00);

INSERT INTO khuyen_mai (ten, loai_giam, gia_tri) VALUES
('KM_TEST_TET_2026', 'PERCENT', 10.00),
('KM_TEST_WEEKEND_2026', 'AMOUNT', 200000.00),
('KM_TEST_LOYALTY_2026', 'PERCENT', 5.00),
('KM_TEST_FLASH_2026', 'AMOUNT', 150000.00);

-- -----------------------------------------------------
-- 3) Booking seed data (many rows, many dates/months)
-- -----------------------------------------------------
DROP TEMPORARY TABLE IF EXISTS tmp_booking_seed;
CREATE TEMPORARY TABLE tmp_booking_seed (
	ma VARCHAR(10) PRIMARY KEY,
	kh_sdt VARCHAR(20) NOT NULL,
	so_phong VARCHAR(30) NOT NULL,
	ngay_dat DATETIME NOT NULL,
	trang_thai VARCHAR(30) NOT NULL,
	ngay_nhan DATE NOT NULL,
	ngay_tra DATE NOT NULL,
	gia_phong DECIMAL(15,2) NOT NULL,
	tong_hoa_don DECIMAL(15,2) NULL,
	ngay_tao_hd DATETIME NULL
);

INSERT INTO tmp_booking_seed (ma, kh_sdt, so_phong, ngay_dat, trang_thai, ngay_nhan, ngay_tra, gia_phong, tong_hoa_don, ngay_tao_hd) VALUES
('B001', '0909200001', 'T601', '2026-01-02 09:10:00', 'CHECKED_OUT', '2026-01-02', '2026-01-04', 1200000.00, 1560000.00, '2026-01-04 10:40:00'),
('B002', '0909200002', 'T605', '2026-01-04 11:20:00', 'CHECKED_OUT', '2026-01-04', '2026-01-07', 2850000.00, 3430000.00, '2026-01-07 11:30:00'),
('B003', '0909200003', 'T609', '2026-01-08 14:05:00', 'CHECKED_OUT', '2026-01-08', '2026-01-10', 5000000.00, 5780000.00, '2026-01-10 12:10:00'),
('B004', '0909200004', 'T603', '2026-01-12 08:40:00', 'CHECKED_OUT', '2026-01-12', '2026-01-13', 600000.00, 760000.00, '2026-01-13 09:20:00'),
('B005', '0909200005', 'T606', '2026-01-16 10:25:00', 'CHECKED_OUT', '2026-01-16', '2026-01-18', 1900000.00, 2250000.00, '2026-01-18 13:00:00'),
('B006', '0909200006', 'T607', '2026-01-20 15:00:00', 'CANCELLED',   '2026-01-21', '2026-01-23', 2600000.00, NULL, NULL),
('B007', '0909200007', 'T610', '2026-01-24 09:15:00', 'CHECKED_OUT', '2026-01-24', '2026-01-26', 5000000.00, 5420000.00, '2026-01-26 10:30:00'),
('B008', '0909200008', 'T602', '2026-02-01 12:30:00', 'CHECKED_OUT', '2026-02-01', '2026-02-02', 600000.00, 720000.00, '2026-02-02 09:10:00'),
('B009', '0909200009', 'T605', '2026-02-05 13:50:00', 'CHECKED_OUT', '2026-02-05', '2026-02-07', 1900000.00, 2290000.00, '2026-02-07 12:20:00'),
('B010', '0909200010', 'T604', '2026-02-10 09:00:00', 'CONFIRMED',   '2026-02-12', '2026-02-13', 950000.00, NULL, NULL),
('B011', '0909200011', 'T611', '2026-02-14 10:45:00', 'CHECKED_OUT', '2026-02-14', '2026-02-17', 7500000.00, 8180000.00, '2026-02-17 15:10:00'),
('B012', '0909200012', 'T604', '2026-02-18 16:25:00', 'CHECKED_OUT', '2026-02-18', '2026-02-20', 1900000.00, 2360000.00, '2026-02-20 11:00:00'),
('B013', '0909200013', 'T608', '2026-02-22 09:35:00', 'CHECKED_OUT', '2026-02-22', '2026-02-24', 2600000.00, 3060000.00, '2026-02-24 10:55:00'),
('B014', '0909200014', 'T603', '2026-02-26 11:05:00', 'PENDING',     '2026-03-01', '2026-03-02', 600000.00, NULL, NULL),
('B015', '0909200015', 'T601', '2026-03-02 08:10:00', 'CHECKED_OUT', '2026-03-02', '2026-03-04', 1200000.00, 1470000.00, '2026-03-04 09:40:00'),
('B016', '0909200001', 'T606', '2026-03-06 14:20:00', 'CHECKED_OUT', '2026-03-06', '2026-03-08', 1900000.00, 2310000.00, '2026-03-08 11:45:00'),
('B017', '0909200002', 'T609', '2026-03-10 15:15:00', 'CHECKED_OUT', '2026-03-10', '2026-03-11', 2500000.00, 2890000.00, '2026-03-11 12:25:00'),
('B018', '0909200003', 'T612', '2026-03-14 10:05:00', 'CHECKED_OUT', '2026-03-14', '2026-03-16', 5000000.00, 5650000.00, '2026-03-16 14:15:00'),
('B019', '0909200004', 'T607', '2026-03-18 09:30:00', 'CHECKED_OUT', '2026-03-18', '2026-03-20', 2600000.00, 3020000.00, '2026-03-20 10:20:00'),
('B020', '0909200005', 'T604', '2026-03-22 13:10:00', 'CONFIRMED',   '2026-03-24', '2026-03-25', 950000.00, NULL, NULL),
('B021', '0909200006', 'T605', '2026-03-26 11:50:00', 'CHECKED_OUT', '2026-03-26', '2026-03-28', 1900000.00, 2430000.00, '2026-03-28 17:25:00'),
('B022', '0909200007', 'T602', '2026-04-01 10:40:00', 'CHECKED_OUT', '2026-04-01', '2026-04-02', 600000.00, 780000.00, '2026-04-02 11:05:00'),
('B023', '0909200008', 'T611', '2026-04-06 09:55:00', 'CHECKED_OUT', '2026-04-06', '2026-04-08', 5000000.00, 5520000.00, '2026-04-08 10:50:00'),
('B024', '0909200009', 'T603', '2026-04-11 15:35:00', 'PENDING',     '2026-04-13', '2026-04-14', 600000.00, NULL, NULL);

INSERT INTO dat_phong (khach_hang_id, nhan_vien_id, ngay_dat, trang_thai)
SELECT kh.id, @NV_ID, t.ngay_dat, t.trang_thai
FROM tmp_booking_seed t
JOIN khach_hang kh ON kh.sdt = t.kh_sdt;

DROP TEMPORARY TABLE IF EXISTS tmp_booking_map;
CREATE TEMPORARY TABLE tmp_booking_map AS
SELECT
	t.ma,
	dp.id AS dat_phong_id,
	p.id AS phong_id,
	t.ngay_nhan,
	t.ngay_tra,
	t.gia_phong,
	t.tong_hoa_don,
	t.ngay_tao_hd
FROM tmp_booking_seed t
JOIN khach_hang kh ON kh.sdt = t.kh_sdt
JOIN dat_phong dp ON dp.khach_hang_id = kh.id AND dp.ngay_dat = t.ngay_dat
JOIN phong p ON p.so_phong = t.so_phong;

INSERT INTO chi_tiet_dat_phong (dat_phong_id, phong_id, ngay_nhan, ngay_tra, gia)
SELECT dat_phong_id, phong_id, ngay_nhan, ngay_tra, gia_phong
FROM tmp_booking_map;

-- -----------------------------------------------------
-- 4) Service usage data
-- -----------------------------------------------------
DROP TEMPORARY TABLE IF EXISTS tmp_service_seed;
CREATE TEMPORARY TABLE tmp_service_seed (
	ma VARCHAR(10) NOT NULL,
	dv_ten VARCHAR(120) NOT NULL,
	so_luong INT NOT NULL,
	thoi_diem DATETIME NOT NULL
);

INSERT INTO tmp_service_seed (ma, dv_ten, so_luong, thoi_diem) VALUES
('B001', 'DV_TEST_BREAKFAST_2026', 2, '2026-01-03 08:00:00'),
('B001', 'DV_TEST_MINIBAR_2026', 1, '2026-01-03 22:10:00'),
('B002', 'DV_TEST_BREAKFAST_2026', 3, '2026-01-06 08:10:00'),
('B002', 'DV_TEST_LAUNDRY_2026', 2, '2026-01-06 14:30:00'),
('B002', 'DV_TEST_SHUTTLE_2026', 1, '2026-01-07 09:00:00'),
('B003', 'DV_TEST_SPA_2026', 1, '2026-01-09 18:20:00'),
('B003', 'DV_TEST_LATE_CHECKOUT_2026', 1, '2026-01-10 09:30:00'),
('B004', 'DV_TEST_BREAKFAST_2026', 1, '2026-01-13 07:30:00'),
('B005', 'DV_TEST_MINIBAR_2026', 2, '2026-01-17 21:40:00'),
('B005', 'DV_TEST_LAUNDRY_2026', 1, '2026-01-17 10:50:00'),
('B007', 'DV_TEST_SPA_2026', 1, '2026-01-25 17:00:00'),
('B008', 'DV_TEST_BREAKFAST_2026', 1, '2026-02-02 07:40:00'),
('B009', 'DV_TEST_BREAKFAST_2026', 2, '2026-02-06 08:15:00'),
('B009', 'DV_TEST_SHUTTLE_2026', 1, '2026-02-07 09:10:00'),
('B011', 'DV_TEST_SPA_2026', 2, '2026-02-16 19:20:00'),
('B011', 'DV_TEST_MINIBAR_2026', 3, '2026-02-16 22:30:00'),
('B012', 'DV_TEST_LAUNDRY_2026', 2, '2026-02-19 15:30:00'),
('B013', 'DV_TEST_BREAKFAST_2026', 2, '2026-02-23 08:10:00'),
('B013', 'DV_TEST_LATE_CHECKOUT_2026', 1, '2026-02-24 10:00:00'),
('B015', 'DV_TEST_BREAKFAST_2026', 2, '2026-03-03 08:05:00'),
('B016', 'DV_TEST_LAUNDRY_2026', 2, '2026-03-07 15:50:00'),
('B016', 'DV_TEST_MINIBAR_2026', 1, '2026-03-07 22:05:00'),
('B017', 'DV_TEST_SHUTTLE_2026', 1, '2026-03-11 09:20:00'),
('B018', 'DV_TEST_SPA_2026', 1, '2026-03-15 18:30:00'),
('B018', 'DV_TEST_BREAKFAST_2026', 2, '2026-03-16 08:15:00'),
('B019', 'DV_TEST_MINIBAR_2026', 2, '2026-03-19 22:10:00'),
('B021', 'DV_TEST_BREAKFAST_2026', 3, '2026-03-27 08:00:00'),
('B021', 'DV_TEST_SPA_2026', 1, '2026-03-27 20:20:00'),
('B022', 'DV_TEST_BREAKFAST_2026', 1, '2026-04-02 08:10:00'),
('B023', 'DV_TEST_MINIBAR_2026', 2, '2026-04-07 21:40:00'),
('B023', 'DV_TEST_LATE_CHECKOUT_2026', 1, '2026-04-08 10:10:00');

INSERT INTO su_dung_dich_vu (dat_phong_id, phong_id, dich_vu_id, so_luong, thoi_diem)
SELECT
	m.dat_phong_id,
	m.phong_id,
	dv.id,
	s.so_luong,
	s.thoi_diem
FROM tmp_service_seed s
JOIN tmp_booking_map m ON m.ma = s.ma
JOIN dich_vu dv ON dv.ten = s.dv_ten;

-- -----------------------------------------------------
-- 5) Invoices, promotions, payments
-- -----------------------------------------------------
INSERT INTO hoa_don (dat_phong_id, nhan_vien_id, tong_tien, ngay_tao)
SELECT dat_phong_id, @NV_ID, tong_hoa_don, ngay_tao_hd
FROM tmp_booking_map
WHERE tong_hoa_don IS NOT NULL;

DROP TEMPORARY TABLE IF EXISTS tmp_invoice_map;
CREATE TEMPORARY TABLE tmp_invoice_map AS
SELECT
	m.ma,
	hd.id AS hoa_don_id,
	m.tong_hoa_don,
	m.ngay_tao_hd
FROM tmp_booking_map m
JOIN hoa_don hd ON hd.dat_phong_id = m.dat_phong_id;

DROP TEMPORARY TABLE IF EXISTS tmp_promo_seed;
CREATE TEMPORARY TABLE tmp_promo_seed (
	ma VARCHAR(10) NOT NULL,
	km_ten VARCHAR(120) NOT NULL
);

INSERT INTO tmp_promo_seed (ma, km_ten) VALUES
('B002', 'KM_TEST_WEEKEND_2026'),
('B003', 'KM_TEST_TET_2026'),
('B005', 'KM_TEST_LOYALTY_2026'),
('B007', 'KM_TEST_FLASH_2026'),
('B011', 'KM_TEST_TET_2026'),
('B012', 'KM_TEST_WEEKEND_2026'),
('B015', 'KM_TEST_FLASH_2026'),
('B018', 'KM_TEST_LOYALTY_2026'),
('B021', 'KM_TEST_WEEKEND_2026'),
('B023', 'KM_TEST_TET_2026');

INSERT INTO hoa_don_khuyen_mai (hoa_don_id, khuyen_mai_id)
SELECT im.hoa_don_id, km.id
FROM tmp_promo_seed ps
JOIN tmp_invoice_map im ON im.ma = ps.ma
JOIN khuyen_mai km ON km.ten = ps.km_ten;

INSERT INTO thanh_toan (hoa_don_id, so_tien, phuong_thuc, ngay_thanh_toan)
SELECT
	im.hoa_don_id,
	im.tong_hoa_don,
	CASE MOD(DAY(im.ngay_tao_hd), 4)
		WHEN 0 THEN 'CASH'
		WHEN 1 THEN 'CARD'
		WHEN 2 THEN 'TRANSFER'
		ELSE 'E_WALLET'
	END,
	DATE_ADD(im.ngay_tao_hd, INTERVAL 30 MINUTE)
FROM tmp_invoice_map im;

COMMIT;

-- -----------------------------------------------------
-- 6) Quick test queries (revenue by day/month)
-- -----------------------------------------------------

-- Revenue by day
SELECT
	DATE(ngay_tao) AS ngay,
	SUM(tong_tien) AS doanh_thu_ngay
FROM hoa_don
WHERE ngay_tao >= '2026-01-01' AND ngay_tao < '2026-05-01'
GROUP BY DATE(ngay_tao)
ORDER BY ngay;

-- Revenue by month
SELECT
	DATE_FORMAT(ngay_tao, '%Y-%m') AS thang,
	SUM(tong_tien) AS doanh_thu_thang
FROM hoa_don
WHERE ngay_tao >= '2026-01-01' AND ngay_tao < '2026-05-01'
GROUP BY DATE_FORMAT(ngay_tao, '%Y-%m')
ORDER BY thang;

-- Latest invoice sample
SELECT id, dat_phong_id, tong_tien, ngay_tao
FROM hoa_don
ORDER BY ngay_tao DESC
LIMIT 20;