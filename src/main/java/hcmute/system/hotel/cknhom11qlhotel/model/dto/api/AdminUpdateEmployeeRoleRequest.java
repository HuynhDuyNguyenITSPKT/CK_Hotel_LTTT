package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.validation.constraints.NotNull;

public class AdminUpdateEmployeeRoleRequest {

    @NotNull(message = "Vai trò không được để trống")
    private EmployeeRole role;

    public AdminUpdateEmployeeRoleRequest() {
    }

    public AdminUpdateEmployeeRoleRequest(EmployeeRole role) {
        this.role = role;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }
}
