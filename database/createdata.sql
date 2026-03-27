USE hotelnhom11;

START TRANSACTION;

-- =========================
-- CONFIG
-- =========================
SET @BASE_DATE := '2026-01-01 08:00:00';

-- Reuse existing nhan_vien ids
SET @NV_FALLBACK := (SELECT id FROM nhan_vien ORDER BY id LIMIT 1);
SET @NV1 := COALESCE((SELECT id FROM nhan_vien WHERE id = 1 LIMIT 1), @NV_FALLBACK);
SET @NV2 := COALESCE((SELECT id FROM nhan_vien WHERE id = 2 LIMIT 1), @NV1);
SET @NV3 := COALESCE((SELECT id FROM nhan_vien WHERE id = 3 LIMIT 1), @NV1);
SET @NV4 := COALESCE((SELECT id FROM nhan_vien WHERE id = 4 LIMIT 1), @NV1);

-- =========================
-- CLEANUP OLD DATA
-- =========================
DROP TEMPORARY TABLE IF EXISTS tmp_seed_customers;
CREATE TEMPORARY TABLE tmp_seed_customers AS
SELECT id FROM khach_hang WHERE email LIKE 'seed20_%@test.local';

DROP TEMPORARY TABLE IF EXISTS tmp_seed_bookings;
CREATE TEMPORARY TABLE tmp_seed_bookings AS
SELECT id FROM dat_phong WHERE khach_hang_id IN (SELECT id FROM tmp_seed_customers);

DROP TEMPORARY TABLE IF EXISTS tmp_seed_invoices;
CREATE TEMPORARY TABLE tmp_seed_invoices AS
SELECT id FROM hoa_don WHERE dat_phong_id IN (SELECT id FROM tmp_seed_bookings);

DELETE FROM thanh_toan WHERE hoa_don_id IN (SELECT id FROM tmp_seed_invoices);

DELETE FROM hoa_don_khuyen_mai
WHERE hoa_don_id IN (SELECT id FROM tmp_seed_invoices)
   OR khuyen_mai_id IN (SELECT id FROM khuyen_mai WHERE ten LIKE 'KM_SEED20_%');

DELETE FROM hoa_don WHERE id IN (SELECT id FROM tmp_seed_invoices);

DELETE FROM su_dung_dich_vu WHERE dat_phong_id IN (SELECT id FROM tmp_seed_bookings);

DELETE FROM chi_tiet_dat_phong WHERE dat_phong_id IN (SELECT id FROM tmp_seed_bookings);

DELETE FROM dat_phong WHERE id IN (SELECT id FROM tmp_seed_bookings);

DELETE FROM khach_hang WHERE id IN (SELECT id FROM tmp_seed_customers);

DELETE FROM phong WHERE so_phong LIKE 'S20%';

DELETE FROM loai_phong WHERE ten_loai LIKE 'LP_SEED20_%';

DELETE FROM dich_vu WHERE ten LIKE 'DV_SEED20_%';

DELETE FROM khuyen_mai WHERE ten LIKE 'KM_SEED20_%';

-- =========================
-- SEQUENCE
-- =========================
DROP TEMPORARY TABLE IF EXISTS tmp_seq_20;
CREATE TEMPORARY TABLE tmp_seq_20 (n INT PRIMARY KEY);

INSERT INTO tmp_seq_20 VALUES
(1),(2),(3),(4),(5),(6),(7),(8),(9),(10),
(11),(12),(13),(14),(15),(16),(17),(18),(19),(20);

-- =========================
-- 1) KHACH_HANG
-- =========================
INSERT INTO khach_hang (ten, sdt, email)
SELECT
    CONCAT('Khach test ', LPAD(s.n, 2, '0')),
    CONCAT('09093', LPAD(s.n, 5, '0')),
    CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local')
FROM tmp_seq_20 s;

-- =========================
-- 2) LOAI_PHONG
-- =========================
INSERT INTO loai_phong (ten_loai, mo_ta, image_url, gia_co_ban)
SELECT
    CONCAT('LP_SEED20_', LPAD(s.n, 2, '0')),
    CONCAT('Loai phong seed co ban #', s.n),
    CONCAT('https://picsum.photos/seed/lp_seed20_', LPAD(s.n, 2, '0'), '/900/600'),
    (450000 + s.n * 50000)
FROM tmp_seq_20 s;

-- =========================
-- 3) PHONG
-- =========================
INSERT INTO phong (so_phong, image_url, trang_thai, loai_phong_id)
SELECT
    CONCAT('S20', LPAD(s.n, 2, '0')),
    CONCAT('https://picsum.photos/seed/room_seed20_', LPAD(s.n, 2, '0'), '/900/600'),
    CASE MOD(s.n, 4)
        WHEN 1 THEN 'AVAILABLE'
        WHEN 2 THEN 'OCCUPIED'
        WHEN 3 THEN 'MAINTENANCE'
        ELSE 'CLEANING'
    END,
    lp.id
FROM tmp_seq_20 s
JOIN loai_phong lp ON lp.ten_loai = CONCAT('LP_SEED20_', LPAD(s.n, 2, '0'));

-- =========================
-- 4) DICH_VU
-- =========================
INSERT INTO dich_vu (ten, image_url, gia)
SELECT
    CONCAT('DV_SEED20_', LPAD(s.n, 2, '0')),
    CONCAT('https://picsum.photos/seed/dv_seed20_', LPAD(s.n, 2, '0'), '/900/600'),
    (50000 + s.n * 25000)
FROM tmp_seq_20 s;

-- =========================
-- 5) KHUYEN_MAI
-- =========================
INSERT INTO khuyen_mai (ten, loai_giam, gia_tri)
SELECT
    CONCAT('KM_SEED20_', LPAD(s.n, 2, '0')),
    CASE WHEN MOD(s.n, 2) = 0 THEN 'AMOUNT' ELSE 'PERCENT' END,
    CASE
        WHEN MOD(s.n, 2) = 0 THEN (30000 + s.n * 7000)
        ELSE (5 + MOD(s.n, 20))
    END
FROM tmp_seq_20 s;

-- =========================
-- 6) DAT_PHONG (FIX TIME)
-- =========================
INSERT INTO dat_phong (khach_hang_id, nhan_vien_id, ngay_dat, trang_thai)
SELECT
    kh.id,
    CASE MOD(s.n - 1, 4)
        WHEN 0 THEN @NV1
        WHEN 1 THEN @NV2
        WHEN 2 THEN @NV3
        ELSE @NV4
    END,
    TIMESTAMP(@BASE_DATE) + INTERVAL FLOOR(RAND()*180) DAY,
    CASE MOD(s.n, 5)
        WHEN 1 THEN 'PENDING'
        WHEN 2 THEN 'CONFIRMED'
        WHEN 3 THEN 'CHECKED_IN'
        WHEN 4 THEN 'CHECKED_OUT'
        ELSE 'CANCELLED'
    END
FROM tmp_seq_20 s
JOIN khach_hang kh 
ON kh.email = CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local');

-- =========================
-- 7) CHI_TIET_DAT_PHONG
-- =========================
INSERT INTO chi_tiet_dat_phong (dat_phong_id, phong_id, ngay_nhan, ngay_tra, gia)
SELECT
    dp.id,
    p.id,
    DATE(dp.ngay_dat) + INTERVAL 1 DAY,
    DATE(dp.ngay_dat) + INTERVAL (2 + MOD(s.n, 3)) DAY,
    lp.gia_co_ban
FROM tmp_seq_20 s
JOIN khach_hang kh ON kh.email = CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local')
JOIN dat_phong dp
    ON dp.khach_hang_id = kh.id
JOIN phong p ON p.so_phong = CONCAT('S20', LPAD(s.n, 2, '0'))
JOIN loai_phong lp ON lp.id = p.loai_phong_id;

-- =========================
-- 8) SU_DUNG_DICH_VU
-- =========================
INSERT INTO su_dung_dich_vu (dat_phong_id, phong_id, dich_vu_id, so_luong, thoi_diem)
SELECT
    dp.id,
    p.id,
    dv.id,
    1 + MOD(s.n, 3),
    TIMESTAMP(dp.ngay_dat) + INTERVAL (8 + FLOOR(RAND()*10)) HOUR
FROM tmp_seq_20 s
JOIN khach_hang kh ON kh.email = CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local')
JOIN dat_phong dp ON dp.khach_hang_id = kh.id
JOIN phong p ON p.so_phong = CONCAT('S20', LPAD(s.n, 2, '0'))
JOIN dich_vu dv ON dv.ten = CONCAT('DV_SEED20_', LPAD(s.n, 2, '0'));

-- =========================
-- 9) HOA_DON (FIX TIME)
-- =========================
INSERT INTO hoa_don (dat_phong_id, nhan_vien_id, tong_tien, ngay_tao)
SELECT
    dp.id,
    dp.nhan_vien_id,
    (650000 + s.n * 120000),
    TIMESTAMP(dp.ngay_dat) + INTERVAL (1 + FLOOR(RAND()*3)) DAY
FROM tmp_seq_20 s
JOIN khach_hang kh ON kh.email = CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local')
JOIN dat_phong dp ON dp.khach_hang_id = kh.id;

-- =========================
-- 10) HOA_DON_KHUYEN_MAI
-- =========================
INSERT INTO hoa_don_khuyen_mai (hoa_don_id, khuyen_mai_id)
SELECT
    hd.id,
    km.id
FROM tmp_seq_20 s
JOIN khach_hang kh ON kh.email = CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local')
JOIN dat_phong dp ON dp.khach_hang_id = kh.id
JOIN hoa_don hd ON hd.dat_phong_id = dp.id
JOIN khuyen_mai km ON km.ten = CONCAT('KM_SEED20_', LPAD(s.n, 2, '0'));

-- =========================
-- 11) THANH_TOAN (FIX TIME 🔥)
-- =========================
INSERT INTO thanh_toan (hoa_don_id, so_tien, phuong_thuc, ngay_thanh_toan)
SELECT
    hd.id,
    hd.tong_tien,
    CASE MOD(s.n, 4)
        WHEN 1 THEN 'CASH'
        WHEN 2 THEN 'CARD'
        WHEN 3 THEN 'TRANSFER'
        ELSE 'E_WALLET'
    END,
    TIMESTAMP(hd.ngay_tao)
    + INTERVAL FLOOR(RAND()*15) DAY
    + INTERVAL FLOOR(RAND()*24) HOUR
FROM tmp_seq_20 s
JOIN khach_hang kh ON kh.email = CONCAT('seed20_', LPAD(s.n, 2, '0'), '@test.local')
JOIN dat_phong dp ON dp.khach_hang_id = kh.id
JOIN hoa_don hd ON hd.dat_phong_id = dp.id;

COMMIT;