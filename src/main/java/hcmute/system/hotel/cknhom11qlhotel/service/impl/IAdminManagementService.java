package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;

import java.util.List;

public interface IAdminManagementService {
    List<AdminAccountView> getAccounts();

    List<AdminEmployeeView> getEmployees();

    void createEmployeeWithAccount(CreateEmployeeForm form);

    void updateAccountStatus(Long accountId, AccountStatus status);

    void updateEmployeeRole(Long employeeId, EmployeeRole role);
}

