package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
public class SeedDataService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;

    public SeedDataService(TaiKhoanRepository taiKhoanRepository,
                           NhanVienRepository nhanVienRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Transactional
    public void seedDefaultUsers() {
        Stream.of(
                        new SeedUser(EmployeeRole.ADMIN, "admin", "admin@hotel.local", "Admin System"),
                        new SeedUser(EmployeeRole.RECEPTIONIST, "receptionist", "receptionist@hotel.local", "Le Tan Mac Dinh"),
                        new SeedUser(EmployeeRole.MANAGER, "manager", "manager@hotel.local", "Quan Ly Mac Dinh")
                )
                .forEach(this::seedIfMissing);
    }

    private void seedIfMissing(SeedUser seedUser) {

        if (nhanVienRepository.countByRole(seedUser.role()) > 0) {
            return;
        }

        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(seedUser.username()).orElseGet(() -> {
            TaiKhoan created = new TaiKhoan();
            created.setUsername(seedUser.username());
            created.setEmail(seedUser.email());
            created.setPassword("123456");
            created.setTrangThai(AccountStatus.ACTIVE);
            return taiKhoanRepository.save(created);
        });

        if (nhanVienRepository.countByTaiKhoanId(taiKhoan.getId()) > 0) {
            return;
        }

        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen(seedUser.employeeName());
        nhanVien.setRole(seedUser.role());
        nhanVien.setTaiKhoan(taiKhoan);
        nhanVienRepository.save(nhanVien);
    }

    private record SeedUser(EmployeeRole role, String username, String email, String employeeName) {
    }
}
