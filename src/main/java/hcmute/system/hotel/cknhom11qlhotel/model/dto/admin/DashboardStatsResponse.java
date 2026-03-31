package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import java.math.BigDecimal;

public class DashboardStatsResponse {
    private long totalHoaDon;
    private BigDecimal tongDoanhThu;
    private long totalKhachHang;
    private long totalPhong;
    private long totalDichVu;
    private long totalKhuyenMai;
    private long totalNhanVien;
    private long totalTaiKhoan;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(long totalHoaDon, BigDecimal tongDoanhThu, long totalKhachHang, long totalPhong,
                                  long totalDichVu, long totalKhuyenMai, long totalNhanVien, long totalTaiKhoan) {
        this.totalHoaDon = totalHoaDon;
        this.tongDoanhThu = tongDoanhThu;
        this.totalKhachHang = totalKhachHang;
        this.totalPhong = totalPhong;
        this.totalDichVu = totalDichVu;
        this.totalKhuyenMai = totalKhuyenMai;
        this.totalNhanVien = totalNhanVien;
        this.totalTaiKhoan = totalTaiKhoan;
    }

    public long getTotalHoaDon() {
        return totalHoaDon;
    }

    public void setTotalHoaDon(long totalHoaDon) {
        this.totalHoaDon = totalHoaDon;
    }

    public BigDecimal getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(BigDecimal tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public long getTotalKhachHang() {
        return totalKhachHang;
    }

    public void setTotalKhachHang(long totalKhachHang) {
        this.totalKhachHang = totalKhachHang;
    }

    public long getTotalPhong() {
        return totalPhong;
    }

    public void setTotalPhong(long totalPhong) {
        this.totalPhong = totalPhong;
    }

    public long getTotalDichVu() {
        return totalDichVu;
    }

    public void setTotalDichVu(long totalDichVu) {
        this.totalDichVu = totalDichVu;
    }

    public long getTotalKhuyenMai() {
        return totalKhuyenMai;
    }

    public void setTotalKhuyenMai(long totalKhuyenMai) {
        this.totalKhuyenMai = totalKhuyenMai;
    }

    public long getTotalNhanVien() {
        return totalNhanVien;
    }

    public void setTotalNhanVien(long totalNhanVien) {
        this.totalNhanVien = totalNhanVien;
    }

    public long getTotalTaiKhoan() {
        return totalTaiKhoan;
    }

    public void setTotalTaiKhoan(long totalTaiKhoan) {
        this.totalTaiKhoan = totalTaiKhoan;
    }
}
