package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class QuanLyDichVuService {

    private final DichVuRepository dichVuRepository;
    private final HoTroHinhAnhService adminMediaSupport;
    private final KiemTraYeuCauService adminRequestValidator;

    public QuanLyDichVuService(DichVuRepository dichVuRepository,
                               HoTroHinhAnhService adminMediaSupport,
                               KiemTraYeuCauService adminRequestValidator) {
        this.dichVuRepository = dichVuRepository;
        this.adminMediaSupport = adminMediaSupport;
        this.adminRequestValidator = adminRequestValidator;
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> layDanhSachDichVu() {
        return dichVuRepository.findAllByOrderByIdDesc().stream()
                .map(this::toServiceResponse)
                .toList();
    }

    @Transactional
    public ServiceResponse taoDichVu(ServiceRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateServiceRequest(request);

        DichVu dichVu = new DichVu();
        dichVu.setTen(request.getTen().trim());
        dichVu.setImageUrl(adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/services"));
        dichVu.setGia(request.getGia());
        return toServiceResponse(dichVuRepository.save(dichVu));
    }

    @Transactional
    public ServiceResponse capNhatDichVu(Long serviceId, ServiceRequest request, MultipartFile imageFile) {
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
    public void xoaDichVu(Long serviceId) {
        if (!dichVuRepository.existsById(serviceId)) {
            throw new IllegalArgumentException("Không tìm thấy dịch vụ");
        }
        dichVuRepository.deleteById(serviceId);
    }

    private ServiceResponse toServiceResponse(DichVu dichVu) {
        return new ServiceResponse(dichVu.getId(), dichVu.getTen(), dichVu.getImageUrl(), dichVu.getGia());
    }
}
