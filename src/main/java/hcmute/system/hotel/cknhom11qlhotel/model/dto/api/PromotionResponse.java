package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;

import java.math.BigDecimal;

public record PromotionResponse(
        Long id,
        String ten,
        DiscountType loaiGiam,
        BigDecimal giaTri
) {
}

