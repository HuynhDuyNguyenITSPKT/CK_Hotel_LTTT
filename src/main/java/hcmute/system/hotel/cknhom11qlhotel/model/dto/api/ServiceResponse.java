package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import java.math.BigDecimal;

public record ServiceResponse(
        Long id,
        String ten,
        String imageUrl,
        BigDecimal gia
) {
}

