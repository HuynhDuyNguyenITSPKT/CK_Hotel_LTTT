package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuanTriTaiKhoanNhanVienService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KiemTraYeuCauService kiemTraYeuCauService;

    public QuanTriTaiKhoanNhanVienService(TaiKhoanRepository taiKhoanRepository,
                                NhanVienRepository nhanVienRepository,
                                KiemTraYeuCauService kiemTraYeuCauService) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.kiemTraYeuCauService = kiemTraYeuCauService;
    }

    @Transactional(readOnly = true)
    public List<AdminAccountView> getAccounts() {
        return taiKhoanRepository.findAllByOrderByIdDesc().stream()
                .map(t -> new AdminAccountView(
                        t.getId(),
                        t.getUsername(),
                        t.getEmail(),
                        t.getTrangThai(),
                        t.getNhanVien() != null ? t.getNhanVien().getTen() : "",
                        t.getNhanVien() != null ? t.getNhanVien().getRole() : null
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminEmployeeView> getEmployees() {
        return nhanVienRepository.findAllWithTaiKhoan().stream()
                .map(n -> new AdminEmployeeView(
                        n.getId(),
                        n.getTen(),
                        n.getRole(),
                        n.getTaiKhoan().getId(),
                        n.getTaiKhoan().getUsername(),
                        n.getTaiKhoan().getEmail(),
                        n.getTaiKhoan().getTrangThai()
                ))
                .toList();
    }

    @Transactional
    public void createEmployeeWithAccount(CreateEmployeeForm form) {
        kiemTraYeuCauService.validateCreateForm(form);

        String username = form.getUsername().trim();
        String email = form.getEmail().trim();

        if (taiKhoanRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }
        if (taiKhoanRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setUsername(username);
        taiKhoan.setEmail(email);
        taiKhoan.setPassword(form.getPassword().trim());
        taiKhoan.setTrangThai(AccountStatus.ACTIVE);
        taiKhoanRepository.save(taiKhoan);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen(form.getEmployeeName().trim());
        nhanVien.setRole(form.getRole());
        nhanVien.setTaiKhoan(taiKhoan);
        nhanVienRepository.save(nhanVien);
    }

    @Transactional
    public void updateAccountStatus(Long accountId, AccountStatus status) {
        if (accountId == null || status == null) {
            throw new IllegalArgumentException("Dữ liệu cập nhật không hợp lệ");
        }

        TaiKhoan taiKhoan = taiKhoanRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));
        taiKhoan.setTrangThai(status);
    }

    @Transactional
    public void updateEmployeeRole(Long employeeId, EmployeeRole role) {
        if (employeeId == null || role == null) {
            throw new IllegalArgumentException("Dữ liệu cập nhật không hợp lệ");
        }

        NhanVien nhanVien = nhanVienRepository.findByIdWithTaiKhoan(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));
        nhanVien.setRole(role);
    }
}
