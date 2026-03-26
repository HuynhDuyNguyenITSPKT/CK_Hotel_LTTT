package hcmute.system.hotel.cknhom11qlhotel.repository.jpa;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaTaiKhoanRepository implements TaiKhoanRepository {

    @Override
    public Optional<TaiKhoan> findByUsername(EntityManager em, String username) {
        return em.createQuery("select t from TaiKhoan t where t.username = :username", TaiKhoan.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<TaiKhoan> findById(EntityManager em, Long id) {
        return Optional.ofNullable(em.find(TaiKhoan.class, id));
    }

    @Override
    public List<TaiKhoan> findAllWithNhanVien(EntityManager em) {
        return em.createQuery(
                        "select t from TaiKhoan t left join fetch t.nhanVien order by t.id desc",
                        TaiKhoan.class)
                .getResultList();
    }

    @Override
    public boolean existsByUsername(EntityManager em, String username) {
        Long count = em.createQuery("select count(t) from TaiKhoan t where t.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(EntityManager em, String email) {
        Long count = em.createQuery("select count(t) from TaiKhoan t where t.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public void save(EntityManager em, TaiKhoan taiKhoan) {
        em.persist(taiKhoan);
    }
}

