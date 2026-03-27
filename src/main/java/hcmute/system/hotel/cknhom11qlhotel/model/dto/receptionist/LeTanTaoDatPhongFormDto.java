package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LeTanTaoDatPhongFormDto {
    private String tenKhachHang;
    private String sdt;
    private String email;
    private Long phongId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayNhan;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayTra;

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(LocalDate ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }
}
