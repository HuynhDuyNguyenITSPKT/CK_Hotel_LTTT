package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminCreateEmployeeRequest(
        @NotBlank(message = "Tên nhân viên không được để trống")
        String employeeName,

        @NotNull(message = "Vai trò không được để trống")
        EmployeeRole role,

        @NotBlank(message = "Tên đăng nhập không được để trống")
        String username,

        @Email(message = "Email không hợp lệ")
        @NotBlank(message = "Email không được để trống")
        String email,

        @NotBlank(message = "Mật khẩu không được để trống")
        String password
) {
}

