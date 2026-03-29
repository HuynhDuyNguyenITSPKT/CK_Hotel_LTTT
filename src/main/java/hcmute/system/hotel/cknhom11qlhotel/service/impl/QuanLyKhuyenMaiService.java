package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuanLyKhuyenMaiService {

    private final KhuyenMaiRepository khuyenMaiRepository;
    private final KiemTraYeuCauService adminRequestValidator;

    public QuanLyKhuyenMaiService(KhuyenMaiRepository khuyenMaiRepository,
                                  KiemTraYeuCauService adminRequestValidator) {
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.adminRequestValidator = adminRequestValidator;
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> layDanhSachKhuyenMai() {
        return khuyenMaiRepository.findAllByOrderByIdDesc().stream()
                .map(this::toPromotionResponse)
                .toList();
    }

    @Transactional
    public PromotionResponse taoKhuyenMai(PromotionRequest request) {
        adminRequestValidator.validatePromotionRequest(request);

        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTen(request.getTen().trim());
        khuyenMai.setLoaiGiam(request.getLoaiGiam());
        khuyenMai.setGiaTri(request.getGiaTri());
        return toPromotionResponse(khuyenMaiRepository.save(khuyenMai));
    }

    @Transactional
    public PromotionResponse capNhatKhuyenMai(Long promotionId, PromotionRequest request) {
        adminRequestValidator.validatePromotionRequest(request);

        KhuyenMai khuyenMai = khuyenMaiRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khuyến mãi"));
        khuyenMai.setTen(request.getTen().trim());
        khuyenMai.setLoaiGiam(request.getLoaiGiam());
        khuyenMai.setGiaTri(request.getGiaTri());
        return toPromotionResponse(khuyenMai);
    }

    @Transactional
    public void xoaKhuyenMai(Long promotionId) {
        if (!khuyenMaiRepository.existsById(promotionId)) {
            throw new IllegalArgumentException("Không tìm thấy khuyến mãi");
        }
        khuyenMaiRepository.deleteById(promotionId);
    }

    private PromotionResponse toPromotionResponse(KhuyenMai khuyenMai) {
        return new PromotionResponse(khuyenMai.getId(), khuyenMai.getTen(), khuyenMai.getLoaiGiam(), khuyenMai.getGiaTri());
    }
}
