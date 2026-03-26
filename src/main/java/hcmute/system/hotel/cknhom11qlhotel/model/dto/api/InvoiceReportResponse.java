package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceReportResponse {
    private Long hoaDonId;
    private Long datPhongId;
    private String nhanVien;
    private BigDecimal tongTien;
    private LocalDateTime ngayTao;

    public InvoiceReportResponse() {
    }

    public InvoiceReportResponse(Long hoaDonId, Long datPhongId, String nhanVien, BigDecimal tongTien, LocalDateTime ngayTao) {
        this.hoaDonId = hoaDonId;
        this.datPhongId = datPhongId;
        this.nhanVien = nhanVien;
        this.tongTien = tongTien;
        this.ngayTao = ngayTao;
    }

    public Long getHoaDonId() {
        return hoaDonId;
    }

    public void setHoaDonId(Long hoaDonId) {
        this.hoaDonId = hoaDonId;
    }

    public Long getDatPhongId() {
        return datPhongId;
    }

    public void setDatPhongId(Long datPhongId) {
        this.datPhongId = datPhongId;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }
}
