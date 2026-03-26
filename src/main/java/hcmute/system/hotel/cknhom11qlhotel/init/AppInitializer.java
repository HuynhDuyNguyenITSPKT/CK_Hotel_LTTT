package hcmute.system.hotel.cknhom11qlhotel.init;


import hcmute.system.hotel.cknhom11qlhotel.config.TransactionManager;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {

    private final TransactionManager tm = new TransactionManager();

    @Override
    public void run(String... args) throws Exception {
        tm.runInTransaction(em -> {
            seedIfMissing(em, EmployeeRole.ADMIN, "admin", "admin@hotel.local", "Admin System");
            seedIfMissing(em, EmployeeRole.RECEPTIONIST, "receptionist", "receptionist@hotel.local", "Le Tan Mac Dinh");
            seedIfMissing(em, EmployeeRole.MANAGER, "manager", "manager@hotel.local", "Quan Ly Mac Dinh");
            return null;
        });
    }

    private void seedIfMissing(EntityManager em,
                               EmployeeRole role,
                               String username,
                               String email,
                               String employeeName) {

        Long roleCount = em.createQuery(
                        "select count(n) from NhanVien n where n.role = :role", Long.class)
                .setParameter("role", role)
                .getSingleResult();

        if (roleCount != null && roleCount > 0) return;

        TaiKhoan taiKhoan = em.createQuery(
                        "select t from TaiKhoan t where t.username = :username", TaiKhoan.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (taiKhoan == null) {
            taiKhoan = new TaiKhoan();
            taiKhoan.setUsername(username);
            taiKhoan.setEmail(email);
            taiKhoan.setPassword("123456");
            taiKhoan.setTrangThai(AccountStatus.ACTIVE);
            em.persist(taiKhoan);
        }

        // đảm bảo có ID
        em.flush();

        Long linkedEmployeeCount = em.createQuery(
                        "select count(n) from NhanVien n where n.taiKhoan.id = :taiKhoanId", Long.class)
                .setParameter("taiKhoanId", taiKhoan.getId())
                .getSingleResult();

        if (linkedEmployeeCount != null && linkedEmployeeCount > 0) return;

        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen(employeeName);
        nhanVien.setRole(role);
        nhanVien.setTaiKhoan(taiKhoan);
        em.persist(nhanVien);
    }
}
