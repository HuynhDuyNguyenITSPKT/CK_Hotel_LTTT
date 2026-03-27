package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeTanHoaDonThanhToanDto {
    private final Long datPhongId;
    private final Long hoaDonId;
    private final String maHoaDon;
    private final String maDatPhong;
    private final String tenKhachHang;
    private final String danhSachPhong;
    private final LocalDateTime ngayTaoHoaDon;
    private final BigDecimal tongTienHoaDon;
    private final BigDecimal tongDaThanhToan;
    private final BigDecimal soTienConLai;
    private final String trangThaiThanhToan;
    private final String sacThaiThanhToan;

    public LeTanHoaDonThanhToanDto(Long datPhongId,
                                   Long hoaDonId,
                                   String maHoaDon,
                                   String maDatPhong,
                                   String tenKhachHang,
                                   String danhSachPhong,
                                   LocalDateTime ngayTaoHoaDon,
                                   BigDecimal tongTienHoaDon,
                                   BigDecimal tongDaThanhToan,
                                   BigDecimal soTienConLai,
                                   String trangThaiThanhToan,
                                   String sacThaiThanhToan) {
        this.datPhongId = datPhongId;
        this.hoaDonId = hoaDonId;
        this.maHoaDon = maHoaDon;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.danhSachPhong = danhSachPhong;
        this.ngayTaoHoaDon = ngayTaoHoaDon;
        this.tongTienHoaDon = tongTienHoaDon;
        this.tongDaThanhToan = tongDaThanhToan;
        this.soTienConLai = soTienConLai;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.sacThaiThanhToan = sacThaiThanhToan;
    }

    public Long getDatPhongId() {
        return datPhongId;
    }

    public Long getHoaDonId() {
        return hoaDonId;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getDanhSachPhong() {
        return danhSachPhong;
    }

    public LocalDateTime getNgayTaoHoaDon() {
        return ngayTaoHoaDon;
    }

    public BigDecimal getTongTienHoaDon() {
        return tongTienHoaDon;
    }

    public BigDecimal getTongDaThanhToan() {
        return tongDaThanhToan;
    }

    public BigDecimal getSoTienConLai() {
        return soTienConLai;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public String getSacThaiThanhToan() {
        return sacThaiThanhToan;
    }
}
