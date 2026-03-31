package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentReportResponse {
    private Long thanhToanId;
    private Long hoaDonId;
    private PaymentMethod phuongThuc;
    private BigDecimal soTien;
    private LocalDateTime ngayThanhToan;

    public PaymentReportResponse() {
    }

    public PaymentReportResponse(Long thanhToanId, Long hoaDonId, PaymentMethod phuongThuc, BigDecimal soTien, LocalDateTime ngayThanhToan) {
        this.thanhToanId = thanhToanId;
        this.hoaDonId = hoaDonId;
        this.phuongThuc = phuongThuc;
        this.soTien = soTien;
        this.ngayThanhToan = ngayThanhToan;
    }

    public Long getThanhToanId() {
        return thanhToanId;
    }

    public void setThanhToanId(Long thanhToanId) {
        this.thanhToanId = thanhToanId;
    }

    public Long getHoaDonId() {
        return hoaDonId;
    }

    public void setHoaDonId(Long hoaDonId) {
        this.hoaDonId = hoaDonId;
    }

    public PaymentMethod getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(PaymentMethod phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }
}
