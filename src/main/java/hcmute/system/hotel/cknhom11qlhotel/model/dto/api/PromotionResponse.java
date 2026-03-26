package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;

import java.math.BigDecimal;

public class PromotionResponse {
    private Long id;
    private String ten;
    private DiscountType loaiGiam;
    private BigDecimal giaTri;

    public PromotionResponse() {
    }

    public PromotionResponse(Long id, String ten, DiscountType loaiGiam, BigDecimal giaTri) {
        this.id = id;
        this.ten = ten;
        this.loaiGiam = loaiGiam;
        this.giaTri = giaTri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DiscountType getLoaiGiam() {
        return loaiGiam;
    }

    public void setLoaiGiam(DiscountType loaiGiam) {
        this.loaiGiam = loaiGiam;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }
}
