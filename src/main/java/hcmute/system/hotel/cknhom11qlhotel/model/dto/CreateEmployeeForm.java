package hcmute.system.hotel.cknhom11qlhotel.model.dto;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;

public class CreateEmployeeForm {
    private String employeeName;
    private EmployeeRole role;
    private String username;
    private String email;
    private String password;

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

