package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.DashboardStatsResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;

import java.util.List;

public interface IAdminManagementService {
    List<AdminAccountView> getAccounts();

    List<AdminEmployeeView> getEmployees();

    void createEmployeeWithAccount(CreateEmployeeForm form);

    void updateAccountStatus(Long accountId, AccountStatus status);

    void updateEmployeeRole(Long employeeId, EmployeeRole role);

    DashboardStatsResponse getDashboardStats();

    List<RoomResponse> getRooms();

    RoomResponse createRoom(RoomRequest request);

    RoomResponse updateRoom(Long roomId, RoomRequest request);

    void deleteRoom(Long roomId);

    List<ServiceResponse> getServices();

    ServiceResponse createService(ServiceRequest request);

    ServiceResponse updateService(Long serviceId, ServiceRequest request);

    void deleteService(Long serviceId);

    List<PromotionResponse> getPromotions();

    PromotionResponse createPromotion(PromotionRequest request);

    PromotionResponse updatePromotion(Long promotionId, PromotionRequest request);

    void deletePromotion(Long promotionId);

    List<CustomerResponse> getCustomers();

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Long customerId, CustomerRequest request);

    void deleteCustomer(Long customerId);
}
