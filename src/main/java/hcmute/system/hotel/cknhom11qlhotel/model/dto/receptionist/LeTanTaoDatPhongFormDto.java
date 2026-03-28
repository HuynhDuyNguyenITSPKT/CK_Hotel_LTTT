package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeTanTaoDatPhongFormDto {
    private String tenKhachHang;
    private String sdt;
    private String email;
    private List<Long> phongIds = new ArrayList<>();

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

    public List<Long> getPhongIds() {
        return phongIds;
    }

    public void setPhongIds(List<Long> phongIds) {
        this.phongIds = phongIds == null ? new ArrayList<>() : phongIds;
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
