package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface TaiKhoanRepository {

    Optional<TaiKhoan> findByUsername(EntityManager em, String username);

    void save(EntityManager em, TaiKhoan taiKhoan);
}
