package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.math.BigDecimal;

public class LeTanDichVuDto {
    private final Long dichVuId;
    private final String tenDichVu;
    private final BigDecimal gia;

    public LeTanDichVuDto(Long dichVuId, String tenDichVu, BigDecimal gia) {
        this.dichVuId = dichVuId;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
    }

    public Long getDichVuId() {
        return dichVuId;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public BigDecimal getGia() {
        return gia;
    }
}
