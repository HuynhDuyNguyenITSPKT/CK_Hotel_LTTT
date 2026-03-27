package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.DashboardStatsResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PaymentReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RevenueChartResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAdminManagementService {
    List<AdminAccountView> getAccounts();

    List<AdminEmployeeView> getEmployees();

    void createEmployeeWithAccount(CreateEmployeeForm form);

    void updateAccountStatus(Long accountId, AccountStatus status);

    void updateEmployeeRole(Long employeeId, EmployeeRole role);

    DashboardStatsResponse getDashboardStats();

    RevenueChartResponse getRevenueChart();


    List<InvoiceReportResponse> getInvoicesByFilters(Integer day, Integer month, Integer year);

    List<PaymentReportResponse> getPaymentsByFilters(Integer day, Integer month, Integer year);

    byte[] exportInvoicesExcel(Integer day, Integer month, Integer year);

    byte[] exportPaymentsExcel(Integer day, Integer month, Integer year);

    byte[] exportInvoicesPdf(Integer day, Integer month, Integer year);

    byte[] exportPaymentsPdf(Integer day, Integer month, Integer year);

    List<RoomResponse> getRooms();

    List<LoaiPhong> getRoomTypes();

    RoomResponse createRoom(RoomRequest request, MultipartFile imageFile);

    RoomResponse updateRoom(Long roomId, RoomRequest request, MultipartFile imageFile);

    void deleteRoom(Long roomId);

    List<ServiceResponse> getServices();

    ServiceResponse createService(ServiceRequest request, MultipartFile imageFile);

    ServiceResponse updateService(Long serviceId, ServiceRequest request, MultipartFile imageFile);

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
