package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.DashboardStatsResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PaymentReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RevenueChartResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomTypeRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.service.IQuanTriTongHopService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DieuPhoiQuanTriService implements IQuanTriTongHopService {

    private final QuanTriTaiKhoanNhanVienService adminIdentityService;
    private final BaoCaoThongKeQuanTriService adminAnalyticsService;
    private final QuanTriPhongDieuPhoiService adminRoomManagementService;
    private final QuanTriDanhMucService adminCatalogManagementService;

    public DieuPhoiQuanTriService(QuanTriTaiKhoanNhanVienService adminIdentityService,
                                  BaoCaoThongKeQuanTriService adminAnalyticsService,
                                  QuanTriPhongDieuPhoiService adminRoomManagementService,
                                  QuanTriDanhMucService adminCatalogManagementService) {
        this.adminIdentityService = adminIdentityService;
        this.adminAnalyticsService = adminAnalyticsService;
        this.adminRoomManagementService = adminRoomManagementService;
        this.adminCatalogManagementService = adminCatalogManagementService;
    }

    @Override
    public List<AdminAccountView> getAccounts() {
        return adminIdentityService.getAccounts();
    }

    @Override
    public List<AdminEmployeeView> getEmployees() {
        return adminIdentityService.getEmployees();
    }

    @Override
    public void createEmployeeWithAccount(CreateEmployeeForm form) {
        adminIdentityService.createEmployeeWithAccount(form);
    }

    @Override
    public void updateAccountStatus(Long accountId, AccountStatus status) {
        adminIdentityService.updateAccountStatus(accountId, status);
    }

    @Override
    public void updateEmployeeRole(Long employeeId, EmployeeRole role) {
        adminIdentityService.updateEmployeeRole(employeeId, role);
    }

    @Override
    public DashboardStatsResponse getDashboardStats() {
        return adminAnalyticsService.getDashboardStats();
    }

    @Override
    public RevenueChartResponse getRevenueChart() {
        return adminAnalyticsService.getRevenueChart();
    }

    @Override
    public List<InvoiceReportResponse> getInvoicesByFilters(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.getInvoicesByFilters(day, month, year);
    }

    @Override
    public List<PaymentReportResponse> getPaymentsByFilters(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.getPaymentsByFilters(day, month, year);
    }

    @Override
    public byte[] exportInvoicesExcel(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportInvoicesExcel(day, month, year);
    }

    @Override
    public byte[] exportPaymentsExcel(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportPaymentsExcel(day, month, year);
    }

    @Override
    public byte[] exportInvoicesPdf(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportInvoicesPdf(day, month, year);
    }

    @Override
    public byte[] exportPaymentsPdf(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportPaymentsPdf(day, month, year);
    }

    @Override
    public List<RoomResponse> getRooms() {
        return adminRoomManagementService.getRooms();
    }

    @Override
    public List<LoaiPhong> getRoomTypes() {
        return adminRoomManagementService.getRoomTypes();
    }

    @Override
    public LoaiPhong createRoomType(RoomTypeRequest request, MultipartFile imageFile) {
        return adminRoomManagementService.createRoomType(request, imageFile);
    }

    @Override
    public LoaiPhong updateRoomType(Long roomTypeId, RoomTypeRequest request, MultipartFile imageFile) {
        return adminRoomManagementService.updateRoomType(roomTypeId, request, imageFile);
    }

    @Override
    public void deleteRoomType(Long roomTypeId) {
        adminRoomManagementService.deleteRoomType(roomTypeId);
    }

    @Override
    public RoomResponse createRoom(RoomRequest request, MultipartFile imageFile) {
        return adminRoomManagementService.createRoom(request, imageFile);
    }

    @Override
    public RoomResponse updateRoom(Long roomId, RoomRequest request, MultipartFile imageFile) {
        return adminRoomManagementService.updateRoom(roomId, request, imageFile);
    }

    @Override
    public void deleteRoom(Long roomId) {
        adminRoomManagementService.deleteRoom(roomId);
    }

    @Override
    public List<ServiceResponse> getServices() {
        return adminCatalogManagementService.getServices();
    }

    @Override
    public ServiceResponse createService(ServiceRequest request, MultipartFile imageFile) {
        return adminCatalogManagementService.createService(request, imageFile);
    }

    @Override
    public ServiceResponse updateService(Long serviceId, ServiceRequest request, MultipartFile imageFile) {
        return adminCatalogManagementService.updateService(serviceId, request, imageFile);
    }

    @Override
    public void deleteService(Long serviceId) {
        adminCatalogManagementService.deleteService(serviceId);
    }

    @Override
    public List<PromotionResponse> getPromotions() {
        return adminCatalogManagementService.getPromotions();
    }

    @Override
    public PromotionResponse createPromotion(PromotionRequest request) {
        return adminCatalogManagementService.createPromotion(request);
    }

    @Override
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        return adminCatalogManagementService.updatePromotion(promotionId, request);
    }

    @Override
    public void deletePromotion(Long promotionId) {
        adminCatalogManagementService.deletePromotion(promotionId);
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        return adminCatalogManagementService.getCustomers();
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        return adminCatalogManagementService.createCustomer(request);
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        return adminCatalogManagementService.updateCustomer(customerId, request);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        adminCatalogManagementService.deleteCustomer(customerId);
    }
}
