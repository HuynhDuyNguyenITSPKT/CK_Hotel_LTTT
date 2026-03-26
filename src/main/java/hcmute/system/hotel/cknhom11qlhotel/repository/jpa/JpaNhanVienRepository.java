package hcmute.system.hotel.cknhom11qlhotel.repository.jpa;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class JpaNhanVienRepository implements NhanVienRepository {

    @Override
    public long countByRole(EntityManager em, EmployeeRole role) {
        Long result = em.createQuery("select count(n) from NhanVien n where n.role = :role", Long.class)
                .setParameter("role", role)
                .getSingleResult();
        return result == null ? 0L : result;
    }

    @Override
    public long countByTaiKhoanId(EntityManager em, Long taiKhoanId) {
        Long result = em.createQuery("select count(n) from NhanVien n where n.taiKhoan.id = :taiKhoanId", Long.class)
                .setParameter("taiKhoanId", taiKhoanId)
                .getSingleResult();
        return result == null ? 0L : result;
    }

    @Override
    public void save(EntityManager em, NhanVien nhanVien) {
        em.persist(nhanVien);
    }
}

