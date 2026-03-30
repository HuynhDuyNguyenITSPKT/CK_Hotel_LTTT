package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.time.LocalDate;

public class LeTanDangOThemDichVuDto {
    private final Long datPhongId;
    private final String maDatPhong;
    private final String tenKhachHang;
    private final String soDienThoai;
    private final String soPhong;
    private final Integer tongSoLuongDichVu;
    private final String phongChiTiet;
    private final LocalDate ngayNhan;
    private final LocalDate ngayTra;

    public LeTanDangOThemDichVuDto(Long datPhongId,
                                   String maDatPhong,
                                   String tenKhachHang,
                                   String soDienThoai,
                                   String soPhong,
                                   Integer tongSoLuongDichVu,
                                   String phongChiTiet,
                                   LocalDate ngayNhan,
                                   LocalDate ngayTra) {
        this.datPhongId = datPhongId;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.soPhong = soPhong;
        this.tongSoLuongDichVu = tongSoLuongDichVu;
        this.phongChiTiet = phongChiTiet;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
    }

    public Long getDatPhongId() {
        return datPhongId;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public Integer getTongSoLuongDichVu() {
        return tongSoLuongDichVu;
    }

    public String getPhongChiTiet() {
        return phongChiTiet;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }
}
