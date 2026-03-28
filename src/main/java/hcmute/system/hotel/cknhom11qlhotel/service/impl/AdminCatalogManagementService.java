package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AdminCatalogManagementService {

    private final DichVuRepository dichVuRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final KhachHangRepository khachHangRepository;
    private final AdminMediaSupport adminMediaSupport;
    private final AdminRequestValidator adminRequestValidator;

    public AdminCatalogManagementService(DichVuRepository dichVuRepository,
                                         KhuyenMaiRepository khuyenMaiRepository,
                                         KhachHangRepository khachHangRepository,
                                         AdminMediaSupport adminMediaSupport,
                                         AdminRequestValidator adminRequestValidator) {
        this.dichVuRepository = dichVuRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.khachHangRepository = khachHangRepository;
        this.adminMediaSupport = adminMediaSupport;
        this.adminRequestValidator = adminRequestValidator;
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> getServices() {
        return dichVuRepository.findAllByOrderByIdDesc().stream()
                .map(this::toServiceResponse)
                .toList();
    }

    @Transactional
    public ServiceResponse createService(ServiceRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateServiceRequest(request);

        DichVu dichVu = new DichVu();
        dichVu.setTen(request.getTen().trim());
        dichVu.setImageUrl(adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/services"));
        dichVu.setGia(request.getGia());
        return toServiceResponse(dichVuRepository.save(dichVu));
    }

    @Transactional
    public ServiceResponse updateService(Long serviceId, ServiceRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateServiceRequest(request);

        DichVu dichVu = dichVuRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ"));

        String imageUrl = adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/services");

        dichVu.setTen(request.getTen().trim());
        dichVu.setImageUrl(imageUrl != null ? imageUrl : dichVu.getImageUrl());
        dichVu.setGia(request.getGia());
        return toServiceResponse(dichVu);
    }

    @Transactional
    public void deleteService(Long serviceId) {
        if (!dichVuRepository.existsById(serviceId)) {
            throw new IllegalArgumentException("Không tìm thấy dịch vụ");
        }
        dichVuRepository.deleteById(serviceId);
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> getPromotions() {
        return khuyenMaiRepository.findAllByOrderByIdDesc().stream()
                .map(this::toPromotionResponse)
                .toList();
    }

    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        adminRequestValidator.validatePromotionRequest(request);

        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTen(request.getTen().trim());
        khuyenMai.setLoaiGiam(request.getLoaiGiam());
        khuyenMai.setGiaTri(request.getGiaTri());
        return toPromotionResponse(khuyenMaiRepository.save(khuyenMai));
    }

    @Transactional
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        adminRequestValidator.validatePromotionRequest(request);

        KhuyenMai khuyenMai = khuyenMaiRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khuyến mãi"));
        khuyenMai.setTen(request.getTen().trim());
        khuyenMai.setLoaiGiam(request.getLoaiGiam());
        khuyenMai.setGiaTri(request.getGiaTri());
        return toPromotionResponse(khuyenMai);
    }

    @Transactional
    public void deletePromotion(Long promotionId) {
        if (!khuyenMaiRepository.existsById(promotionId)) {
            throw new IllegalArgumentException("Không tìm thấy khuyến mãi");
        }
        khuyenMaiRepository.deleteById(promotionId);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomers() {
        return khachHangRepository.findAllByOrderByIdDesc().stream()
                .map(this::toCustomerResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        adminRequestValidator.validateCustomerRequest(request);

        String sdt = request.getSdt().trim();
        if (khachHangRepository.existsBySdt(sdt)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        String email = adminMediaSupport.trimToNull(request.getEmail());
        if (email != null && khachHangRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setTen(request.getTen().trim());
        khachHang.setSdt(sdt);
        khachHang.setEmail(email);
        return toCustomerResponse(khachHangRepository.save(khachHang));
    }

    @Transactional
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        adminRequestValidator.validateCustomerRequest(request);

        KhachHang khachHang = khachHangRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));

        String sdt = request.getSdt().trim();
        if (!sdt.equalsIgnoreCase(khachHang.getSdt()) && khachHangRepository.existsBySdt(sdt)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        String email = adminMediaSupport.trimToNull(request.getEmail());
        if (email != null && !email.equalsIgnoreCase(adminMediaSupport.trimToNull(khachHang.getEmail()))
                && khachHangRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        khachHang.setTen(request.getTen().trim());
        khachHang.setSdt(sdt);
        khachHang.setEmail(email);
        return toCustomerResponse(khachHang);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        if (!khachHangRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }
        khachHangRepository.deleteById(customerId);
    }

    private ServiceResponse toServiceResponse(DichVu dichVu) {
        return new ServiceResponse(dichVu.getId(), dichVu.getTen(), dichVu.getImageUrl(), dichVu.getGia());
    }

    private PromotionResponse toPromotionResponse(KhuyenMai khuyenMai) {
        return new PromotionResponse(khuyenMai.getId(), khuyenMai.getTen(), khuyenMai.getLoaiGiam(), khuyenMai.getGiaTri());
    }

    private CustomerResponse toCustomerResponse(KhachHang khachHang) {
        return new CustomerResponse(khachHang.getId(), khachHang.getTen(), khachHang.getSdt(), khachHang.getEmail());
    }
}
