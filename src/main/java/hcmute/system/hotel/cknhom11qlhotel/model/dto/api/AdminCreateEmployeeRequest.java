package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdminCreateEmployeeRequest {

    @NotBlank(message = "Tên nhân viên không được để trống")
    private String employeeName;

    @NotNull(message = "Vai trò không được để trống")
    private EmployeeRole role;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    public AdminCreateEmployeeRequest() {
    }

    public AdminCreateEmployeeRequest(String employeeName, EmployeeRole role, String username, String email, String password) {
        this.employeeName = employeeName;
        this.role = role;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
