package hcmute.system.hotel.cknhom11qlhotel.repository.jpa;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

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
    public void save(EntityManager em, TaiKhoan taiKhoan) {
        em.persist(taiKhoan);
    }
}

