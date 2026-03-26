package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServiceRequest(
        @NotBlank(message = "Tên dịch vụ không được để trống")
        String ten,

        String imageUrl,

        @NotNull(message = "Giá dịch vụ không được để trống")
        @DecimalMin(value = "0.0", inclusive = false, message = "Giá dịch vụ phải lớn hơn 0")
        BigDecimal gia
) {
}

