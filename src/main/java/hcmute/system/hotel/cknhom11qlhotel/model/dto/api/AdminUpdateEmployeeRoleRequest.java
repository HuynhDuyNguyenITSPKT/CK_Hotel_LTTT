package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.validation.constraints.NotNull;

public record AdminUpdateEmployeeRoleRequest(
        @NotNull(message = "Vai trò không được để trống")
        EmployeeRole role
) {
}

