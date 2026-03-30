package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeTanCheckOutDto {
    private final Long datPhongId;
    private final String maDatPhong;
    private final String tenKhachHang;
    private final String soDienThoai;
    private final String soPhong;
    private final LocalDate ngayTra;
    private final BigDecimal tongTienTamTinh;
    private final BigDecimal tongDaThanhToan;
    private final BigDecimal soTienConLai;
    private final String trangThaiThanhToan;
    private final String sacThaiThanhToan;
    private final boolean daThanhToanDu;
    private final BookingStatus trangThaiDatPhong;

    public LeTanCheckOutDto(Long datPhongId,
                            String maDatPhong,
                            String tenKhachHang,
                            String soDienThoai,
                            String soPhong,
                            LocalDate ngayTra,
                            BigDecimal tongTienTamTinh,
                            BigDecimal tongDaThanhToan,
                            BigDecimal soTienConLai,
                            String trangThaiThanhToan,
                            String sacThaiThanhToan,
                            boolean daThanhToanDu,
                            BookingStatus trangThaiDatPhong) {
        this.datPhongId = datPhongId;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.soPhong = soPhong;
        this.ngayTra = ngayTra;
        this.tongTienTamTinh = tongTienTamTinh;
        this.tongDaThanhToan = tongDaThanhToan;
        this.soTienConLai = soTienConLai;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.sacThaiThanhToan = sacThaiThanhToan;
        this.daThanhToanDu = daThanhToanDu;
        this.trangThaiDatPhong = trangThaiDatPhong;
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

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public BigDecimal getTongTienTamTinh() {
        return tongTienTamTinh;
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

    public boolean isDaThanhToanDu() {
        return daThanhToanDu;
    }

    public BookingStatus getTrangThaiDatPhong() {
        return trangThaiDatPhong;
    }
}
