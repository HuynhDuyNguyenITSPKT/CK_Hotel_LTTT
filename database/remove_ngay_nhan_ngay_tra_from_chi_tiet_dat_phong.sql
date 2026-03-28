USE hotelnhom11;

ALTER TABLE chi_tiet_dat_phong
    DROP COLUMN IF EXISTS ngay_nhan,
    DROP COLUMN IF EXISTS ngay_tra;
