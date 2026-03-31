package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class RoomTypeRequest {

    @NotBlank(message = "Tên loại phòng không được để trống")
    private String tenLoai;

    private String moTa;

    private String imageUrl;

    @NotNull(message = "Giá cơ bản không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá cơ bản phải lớn hơn 0")
    private BigDecimal giaCoBan;

    public RoomTypeRequest() {
    }

    public RoomTypeRequest(String tenLoai, String moTa, String imageUrl, BigDecimal giaCoBan) {
        this.tenLoai = tenLoai;
        this.moTa = moTa;
        this.imageUrl = imageUrl;
        this.giaCoBan = giaCoBan;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(BigDecimal giaCoBan) {
        this.giaCoBan = giaCoBan;
    }
}
