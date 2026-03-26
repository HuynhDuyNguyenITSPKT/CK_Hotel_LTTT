package hcmute.system.hotel.cknhom11qlhotel.repository.jpa;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<NhanVien> findByTaiKhoanId(EntityManager em, Long taiKhoanId) {
        return em.createQuery(
                        "select n from NhanVien n join fetch n.taiKhoan t where t.id = :taiKhoanId",
                        NhanVien.class)
                .setParameter("taiKhoanId", taiKhoanId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<NhanVien> findByUsername(EntityManager em, String username) {
        return em.createQuery(
                        "select n from NhanVien n join fetch n.taiKhoan t where lower(t.username) = lower(:username)",
                        NhanVien.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<NhanVien> findById(EntityManager em, Long id) {
        return em.createQuery(
                        "select n from NhanVien n join fetch n.taiKhoan t where n.id = :id",
                        NhanVien.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<NhanVien> findAllWithTaiKhoan(EntityManager em) {
        return em.createQuery(
                        "select n from NhanVien n join fetch n.taiKhoan t order by n.id desc",
                        NhanVien.class)
                .getResultList();
    }

    @Override
    public void save(EntityManager em, NhanVien nhanVien) {
        em.persist(nhanVien);
    }
}

