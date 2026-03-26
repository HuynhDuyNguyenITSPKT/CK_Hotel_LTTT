package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.config.TransactionManager;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class SeedDataService {

    private final TransactionManager transactionManager;
    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;

    public SeedDataService(TransactionManager transactionManager,
                           TaiKhoanRepository taiKhoanRepository,
                           NhanVienRepository nhanVienRepository) {
        this.transactionManager = transactionManager;
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    public void seedDefaultUsers() {
        try {
            transactionManager.runInTransaction(em -> {
                seedIfMissing(em, EmployeeRole.ADMIN, "admin", "admin@hotel.local", "Admin System");
                seedIfMissing(em, EmployeeRole.RECEPTIONIST, "receptionist", "receptionist@hotel.local", "Le Tan Mac Dinh");
                seedIfMissing(em, EmployeeRole.MANAGER, "manager", "manager@hotel.local", "Quan Ly Mac Dinh");
                return null;
            });
        } catch (Exception ex) {
            throw new IllegalStateException("Khong the seed tai khoan mac dinh", ex);
        }
    }

    private void seedIfMissing(EntityManager em,
                               EmployeeRole role,
                               String username,
                               String email,
                               String employeeName) {

        if (nhanVienRepository.countByRole(em, role) > 0) {
            return;
        }

        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(em, username).orElseGet(() -> {
            TaiKhoan created = new TaiKhoan();
            created.setUsername(username);
            created.setEmail(email);
            created.setPassword("123456");
            created.setTrangThai(AccountStatus.ACTIVE);
            taiKhoanRepository.save(em, created);
            return created;
        });

        // Ensure generated ID is available for FK checks.
        em.flush();

        if (nhanVienRepository.countByTaiKhoanId(em, taiKhoan.getId()) > 0) {
            return;
        }

        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen(employeeName);
        nhanVien.setRole(role);
        nhanVien.setTaiKhoan(taiKhoan);
        nhanVienRepository.save(em, nhanVien);
    }
}

