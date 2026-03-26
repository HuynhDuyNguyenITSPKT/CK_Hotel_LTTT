package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "Tên khách hàng không được để trống")
        String ten,

        @NotBlank(message = "Số điện thoại không được để trống")
        String sdt,

        @Email(message = "Email không hợp lệ")
        String email
) {
}

