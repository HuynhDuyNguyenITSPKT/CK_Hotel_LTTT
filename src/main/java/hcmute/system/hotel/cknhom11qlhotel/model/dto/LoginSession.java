package hcmute.system.hotel.cknhom11qlhotel.model.dto;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;

import java.io.Serializable;

public class LoginSession implements Serializable {
    private final Long employeeId;
    private final String username;
    private final String employeeName;
    private final EmployeeRole role;

    public LoginSession(Long employeeId, String username, String employeeName, EmployeeRole role) {
        this.employeeId = employeeId;
        this.username = username;
        this.employeeName = employeeName;
        this.role = role;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public EmployeeRole getRole() {
        return role;
    }
}

