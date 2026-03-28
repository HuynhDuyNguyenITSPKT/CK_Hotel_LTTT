package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.math.BigDecimal;

public class LeTanDichVuDto {
    private final Long dichVuId;
    private final String tenDichVu;
    private final String imageUrl;
    private final BigDecimal gia;

    public LeTanDichVuDto(Long dichVuId, String tenDichVu, String imageUrl, BigDecimal gia) {
        this.dichVuId = dichVuId;
        this.tenDichVu = tenDichVu;
        this.imageUrl = imageUrl;
        this.gia = gia;
    }

    public Long getDichVuId() {
        return dichVuId;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getGia() {
        return gia;
    }
}
