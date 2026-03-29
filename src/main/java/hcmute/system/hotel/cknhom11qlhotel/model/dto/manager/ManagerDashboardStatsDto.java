package hcmute.system.hotel.cknhom11qlhotel.model.dto.manager;

import java.math.BigDecimal;

public class ManagerDashboardStatsDto {
    private final long soKhachHomNay;
    private final long soPhongDatHomNay;
    private final long soPhongDatHomQua;
    private final BigDecimal phanTramSoVoiHomQua;
    private final String nhanXuHuong;
    private final BigDecimal tongDoanhThu;
    private final long soPhongSanSang;
    private final long soPhongDangO;
    private final long soPhongDangDon;
    private final long soPhongBaoTri;

    public ManagerDashboardStatsDto(long soKhachHomNay,
                                    long soPhongDatHomNay,
                                    long soPhongDatHomQua,
                                    BigDecimal phanTramSoVoiHomQua,
                                    String nhanXuHuong,
                                    BigDecimal tongDoanhThu,
                                    long soPhongSanSang,
                                    long soPhongDangO,
                                    long soPhongDangDon,
                                    long soPhongBaoTri) {
        this.soKhachHomNay = soKhachHomNay;
        this.soPhongDatHomNay = soPhongDatHomNay;
        this.soPhongDatHomQua = soPhongDatHomQua;
        this.phanTramSoVoiHomQua = phanTramSoVoiHomQua;
        this.nhanXuHuong = nhanXuHuong;
        this.tongDoanhThu = tongDoanhThu;
        this.soPhongSanSang = soPhongSanSang;
        this.soPhongDangO = soPhongDangO;
        this.soPhongDangDon = soPhongDangDon;
        this.soPhongBaoTri = soPhongBaoTri;
    }

    public long getSoKhachHomNay() {
        return soKhachHomNay;
    }

    public long getSoPhongDatHomNay() {
        return soPhongDatHomNay;
    }

    public long getSoPhongDatHomQua() {
        return soPhongDatHomQua;
    }

    public BigDecimal getPhanTramSoVoiHomQua() {
        return phanTramSoVoiHomQua;
    }

    public String getNhanXuHuong() {
        return nhanXuHuong;
    }

    public BigDecimal getTongDoanhThu() {
        return tongDoanhThu;
    }

    public long getSoPhongSanSang() {
        return soPhongSanSang;
    }

    public long getSoPhongDangO() {
        return soPhongDangO;
    }

    public long getSoPhongDangDon() {
        return soPhongDangDon;
    }

    public long getSoPhongBaoTri() {
        return soPhongBaoTri;
    }
}
