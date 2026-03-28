package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.time.LocalDate;

public class LeTanDangOThemDichVuDto {
    private final Long datPhongId;
    private final String maDatPhong;
    private final String tenKhachHang;
    private final String soPhong;
    private final LocalDate ngayNhan;
    private final LocalDate ngayTra;

    public LeTanDangOThemDichVuDto(Long datPhongId,
                                   String maDatPhong,
                                   String tenKhachHang,
                                   String soPhong,
                                   LocalDate ngayNhan,
                                   LocalDate ngayTra) {
        this.datPhongId = datPhongId;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.soPhong = soPhong;
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

    public String getSoPhong() {
        return soPhong;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }
}
