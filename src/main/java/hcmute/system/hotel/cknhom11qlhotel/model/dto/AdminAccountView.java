package hcmute.system.hotel.cknhom11qlhotel.model.dto;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;

public class AdminAccountView {
    private final Long accountId;
    private final String username;
    private final String email;
    private final AccountStatus status;
    private final String employeeName;
    private final EmployeeRole role;

    public AdminAccountView(Long accountId,
                            String username,
                            String email,
                            AccountStatus status,
                            String employeeName,
                            EmployeeRole role) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.status = status;
        this.employeeName = employeeName;
        this.role = role;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public EmployeeRole getRole() {
        return role;
    }
}

