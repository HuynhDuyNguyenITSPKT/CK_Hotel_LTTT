package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanRepository {

    Optional<TaiKhoan> findByUsername(EntityManager em, String username);

    Optional<TaiKhoan> findById(EntityManager em, Long id);

    List<TaiKhoan> findAllWithNhanVien(EntityManager em);

    boolean existsByUsername(EntityManager em, String username);

    boolean existsByEmail(EntityManager em, String email);

    void save(EntityManager em, TaiKhoan taiKhoan);
}
