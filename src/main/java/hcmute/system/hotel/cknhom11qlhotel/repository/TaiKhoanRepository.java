package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.TaiKhoan;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {

    Optional<TaiKhoan> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<TaiKhoan> findAllByOrderByIdDesc();
}
