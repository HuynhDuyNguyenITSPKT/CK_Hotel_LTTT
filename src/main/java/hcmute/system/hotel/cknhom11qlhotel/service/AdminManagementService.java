package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.config.TransactionManager;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminManagementService implements IAdminManagementService {

    private final TransactionManager transactionManager;
    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;

    public AdminManagementService(TransactionManager transactionManager,
                                  TaiKhoanRepository taiKhoanRepository,
                                  NhanVienRepository nhanVienRepository) {
        this.transactionManager = transactionManager;
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public List<AdminAccountView> getAccounts() {
        try {
            return transactionManager.runInTransaction(em -> taiKhoanRepository.findAllWithNhanVien(em).stream()
                    .map(t -> new AdminAccountView(
                            t.getId(),
                            t.getUsername(),
                            t.getEmail(),
                            t.getTrangThai(),
                            t.getNhanVien() != null ? t.getNhanVien().getTen() : "",
                            t.getNhanVien() != null ? t.getNhanVien().getRole() : null
                    ))
                    .toList());
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể lấy danh sách tài khoản", ex);
        }
    }

    @Override
    public List<AdminEmployeeView> getEmployees() {
        try {
            return transactionManager.runInTransaction(em -> nhanVienRepository.findAllWithTaiKhoan(em).stream()
                    .map(n -> new AdminEmployeeView(
                            n.getId(),
                            n.getTen(),
                            n.getRole(),
                            n.getTaiKhoan().getId(),
                            n.getTaiKhoan().getUsername(),
                            n.getTaiKhoan().getEmail(),
                            n.getTaiKhoan().getTrangThai()
                    ))
                    .toList());
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể lấy danh sách nhân viên", ex);
        }
    }

    @Override
    public void createEmployeeWithAccount(CreateEmployeeForm form) {
        validateCreateForm(form);

        try {
            transactionManager.runInTransaction(em -> {
                String username = form.getUsername().trim();
                String email = form.getEmail().trim();

                if (taiKhoanRepository.existsByUsername(em, username)) {
                    throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
                }
                if (taiKhoanRepository.existsByEmail(em, email)) {
                    throw new IllegalArgumentException("Email đã tồn tại");
                }

                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setUsername(username);
                taiKhoan.setEmail(email);
                taiKhoan.setPassword(form.getPassword().trim());
                taiKhoan.setTrangThai(AccountStatus.ACTIVE);
                taiKhoanRepository.save(em, taiKhoan);
                em.flush();

                NhanVien nhanVien = new NhanVien();
                nhanVien.setTen(form.getEmployeeName().trim());
                nhanVien.setRole(form.getRole());
                nhanVien.setTaiKhoan(taiKhoan);
                nhanVienRepository.save(em, nhanVien);
                return null;
            });
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể tạo nhân viên", ex);
        }
    }

    @Override
    public void updateAccountStatus(Long accountId, AccountStatus status) {
        if (accountId == null || status == null) {
            throw new IllegalArgumentException("Dữ liệu cập nhật không hợp lệ");
        }

        try {
            transactionManager.runInTransaction(em -> {
                TaiKhoan taiKhoan = taiKhoanRepository.findById(em, accountId)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));
                taiKhoan.setTrangThai(status);
                return null;
            });
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể cập nhật trạng thái tài khoản", ex);
        }
    }

    @Override
    public void updateEmployeeRole(Long employeeId, EmployeeRole role) {
        if (employeeId == null || role == null) {
            throw new IllegalArgumentException("Dữ liệu cập nhật không hợp lệ");
        }

        try {
            transactionManager.runInTransaction(em -> {
                NhanVien nhanVien = nhanVienRepository.findById(em, employeeId)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));
                nhanVien.setRole(role);
                return null;
            });
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể cập nhật vai trò nhân viên", ex);
        }
    }

    private void validateCreateForm(CreateEmployeeForm form) {
        if (form == null
                || isBlank(form.getEmployeeName())
                || isBlank(form.getUsername())
                || isBlank(form.getEmail())
                || isBlank(form.getPassword())
                || form.getRole() == null) {
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

