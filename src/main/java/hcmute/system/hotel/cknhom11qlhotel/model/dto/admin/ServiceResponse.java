package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import java.math.BigDecimal;

public class ServiceResponse {
    private Long id;
    private String ten;
    private String imageUrl;
    private BigDecimal gia;

    public ServiceResponse() {
    }

    public ServiceResponse(Long id, String ten, String imageUrl, BigDecimal gia) {
        this.id = id;
        this.ten = ten;
        this.imageUrl = imageUrl;
        this.gia = gia;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }
}
