package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PromotionRequest {

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String ten;

    @NotNull(message = "Loại giảm không được để trống")
    private DiscountType loaiGiam;

    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm phải lớn hơn 0")
    private BigDecimal giaTri;

    public PromotionRequest() {
    }

    public PromotionRequest(String ten, DiscountType loaiGiam, BigDecimal giaTri) {
        this.ten = ten;
        this.loaiGiam = loaiGiam;
        this.giaTri = giaTri;
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
