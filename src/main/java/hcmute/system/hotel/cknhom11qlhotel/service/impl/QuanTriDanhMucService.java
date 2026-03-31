package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class QuanTriDanhMucService {

    private final QuanLyDichVuService quanLyDichVuService;
    private final QuanLyKhuyenMaiService quanLyKhuyenMaiService;
    private final QuanLyKhachHangService quanLyKhachHangService;

    public QuanTriDanhMucService(QuanLyDichVuService quanLyDichVuService,
                                         QuanLyKhuyenMaiService quanLyKhuyenMaiService,
                                         QuanLyKhachHangService quanLyKhachHangService) {
        this.quanLyDichVuService = quanLyDichVuService;
        this.quanLyKhuyenMaiService = quanLyKhuyenMaiService;
        this.quanLyKhachHangService = quanLyKhachHangService;
    }

    public List<ServiceResponse> getServices() {
        return quanLyDichVuService.layDanhSachDichVu();
    }

    public ServiceResponse createService(ServiceRequest request, MultipartFile imageFile) {
        return quanLyDichVuService.taoDichVu(request, imageFile);
    }

    public ServiceResponse updateService(Long serviceId, ServiceRequest request, MultipartFile imageFile) {
        return quanLyDichVuService.capNhatDichVu(serviceId, request, imageFile);
    }

    public void deleteService(Long serviceId) {
        quanLyDichVuService.xoaDichVu(serviceId);
    }

    public List<PromotionResponse> getPromotions() {
        return quanLyKhuyenMaiService.layDanhSachKhuyenMai();
    }

    public PromotionResponse createPromotion(PromotionRequest request) {
        return quanLyKhuyenMaiService.taoKhuyenMai(request);
    }

    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        return quanLyKhuyenMaiService.capNhatKhuyenMai(promotionId, request);
    }

    public void deletePromotion(Long promotionId) {
        quanLyKhuyenMaiService.xoaKhuyenMai(promotionId);
    }

    public List<CustomerResponse> getCustomers() {
        return quanLyKhachHangService.layDanhSachKhachHang();
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        return quanLyKhachHangService.taoKhachHang(request);
    }

    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        return quanLyKhachHangService.capNhatKhachHang(customerId, request);
    }

    public void deleteCustomer(Long customerId) {
        quanLyKhachHangService.xoaKhachHang(customerId);
    }
}
