package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.math.BigDecimal;

public class LeTanKhuyenMaiDto {
    private final Long khuyenMaiId;
    private final String maKhuyenMai;
    private final String tenKhuyenMai;
    private final String loaiGiam;
    private final BigDecimal giaTri;

    public LeTanKhuyenMaiDto(Long khuyenMaiId,
                             String maKhuyenMai,
                             String tenKhuyenMai,
                             String loaiGiam,
                             BigDecimal giaTri) {
        this.khuyenMaiId = khuyenMaiId;
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.loaiGiam = loaiGiam;
        this.giaTri = giaTri;
    }

    public Long getKhuyenMaiId() {
        return khuyenMaiId;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public String getLoaiGiam() {
        return loaiGiam;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }
}
