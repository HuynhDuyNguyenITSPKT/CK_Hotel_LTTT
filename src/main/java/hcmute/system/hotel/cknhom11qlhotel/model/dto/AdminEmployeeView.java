package hcmute.system.hotel.cknhom11qlhotel.model.dto;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;

public class AdminEmployeeView {
    private final Long employeeId;
    private final String employeeName;
    private final EmployeeRole role;
    private final Long accountId;
    private final String username;
    private final String email;
    private final AccountStatus status;

    public AdminEmployeeView(Long employeeId,
                             String employeeName,
                             EmployeeRole role,
                             Long accountId,
                             String username,
                             String email,
                             AccountStatus status) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.role = role;
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public AccountStatus getStatus() {
        return status;
    }
}

