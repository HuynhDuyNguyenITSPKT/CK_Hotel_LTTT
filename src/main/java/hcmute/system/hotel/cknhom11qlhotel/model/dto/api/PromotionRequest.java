package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PromotionRequest(
        @NotBlank(message = "Tên khuyến mãi không được để trống")
        String ten,

        @NotNull(message = "Loại giảm không được để trống")
        DiscountType loaiGiam,

        @NotNull(message = "Giá trị giảm không được để trống")
        @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm phải lớn hơn 0")
        BigDecimal giaTri
) {
}

