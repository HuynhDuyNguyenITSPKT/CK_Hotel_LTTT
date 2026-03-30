package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeTanHoaDonThanhToanDto {
    private final Long datPhongId;
    private final Long hoaDonId;
    private final String maHoaDon;
    private final String maDatPhong;
    private final String tenKhachHang;
    private final String danhSachPhong;
    private final String maKhuyenMai;
    private final String thongTinKhuyenMai;
    private final LocalDateTime ngayTaoHoaDon;
    private final BigDecimal tongTienTruocKhuyenMai;
    private final BigDecimal tongMucGiamKhuyenMai;
    private final BigDecimal tongTienHoaDon;
    private final BigDecimal tongDaThanhToan;
    private final BigDecimal soTienConLai;
    private final String trangThaiThanhToan;
    private final String sacThaiThanhToan;
    private final BookingStatus trangThaiDatPhong;

    public LeTanHoaDonThanhToanDto(Long datPhongId,
                                   Long hoaDonId,
                                   String maHoaDon,
                                   String maDatPhong,
                                   String tenKhachHang,
                                   String danhSachPhong,
                                   String maKhuyenMai,
                                   String thongTinKhuyenMai,
                                   LocalDateTime ngayTaoHoaDon,
                                   BigDecimal tongTienTruocKhuyenMai,
                                   BigDecimal tongMucGiamKhuyenMai,
                                   BigDecimal tongTienHoaDon,
                                   BigDecimal tongDaThanhToan,
                                   BigDecimal soTienConLai,
                                   String trangThaiThanhToan,
                                   String sacThaiThanhToan,
                                   BookingStatus trangThaiDatPhong) {
        this.datPhongId = datPhongId;
        this.hoaDonId = hoaDonId;
        this.maHoaDon = maHoaDon;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.danhSachPhong = danhSachPhong;
        this.maKhuyenMai = maKhuyenMai;
        this.thongTinKhuyenMai = thongTinKhuyenMai;
        this.ngayTaoHoaDon = ngayTaoHoaDon;
        this.tongTienTruocKhuyenMai = tongTienTruocKhuyenMai;
        this.tongMucGiamKhuyenMai = tongMucGiamKhuyenMai;
        this.tongTienHoaDon = tongTienHoaDon;
        this.tongDaThanhToan = tongDaThanhToan;
        this.soTienConLai = soTienConLai;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.sacThaiThanhToan = sacThaiThanhToan;
        this.trangThaiDatPhong = trangThaiDatPhong;
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

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public String getThongTinKhuyenMai() {
        return thongTinKhuyenMai;
    }

    public LocalDateTime getNgayTaoHoaDon() {
        return ngayTaoHoaDon;
    }

    public BigDecimal getTongTienTruocKhuyenMai() {
        return tongTienTruocKhuyenMai;
    }

    public BigDecimal getTongMucGiamKhuyenMai() {
        return tongMucGiamKhuyenMai;
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

    public BookingStatus getTrangThaiDatPhong() {
        return trangThaiDatPhong;
    }
}
