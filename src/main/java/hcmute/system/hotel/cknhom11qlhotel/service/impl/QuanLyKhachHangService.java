package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuanLyKhachHangService {

    private final KhachHangRepository khachHangRepository;
    private final HoTroHinhAnhService adminMediaSupport;
    private final KiemTraYeuCauService adminRequestValidator;

    public QuanLyKhachHangService(KhachHangRepository khachHangRepository,
                                  HoTroHinhAnhService adminMediaSupport,
                                  KiemTraYeuCauService adminRequestValidator) {
        this.khachHangRepository = khachHangRepository;
        this.adminMediaSupport = adminMediaSupport;
        this.adminRequestValidator = adminRequestValidator;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> layDanhSachKhachHang() {
        return khachHangRepository.findAllByOrderByIdDesc().stream()
                .map(this::toCustomerResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse taoKhachHang(CustomerRequest request) {
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
    public CustomerResponse capNhatKhachHang(Long customerId, CustomerRequest request) {
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
    public void xoaKhachHang(Long customerId) {
        if (!khachHangRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }
        khachHangRepository.deleteById(customerId);
    }

    private CustomerResponse toCustomerResponse(KhachHang khachHang) {
        return new CustomerResponse(khachHang.getId(), khachHang.getTen(), khachHang.getSdt(), khachHang.getEmail());
    }
}
