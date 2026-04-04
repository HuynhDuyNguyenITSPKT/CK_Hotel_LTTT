package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.KhachHang;

import java.util.List;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    boolean existsBySdt(String sdt);

    boolean existsByEmail(String email);

    Optional<KhachHang> findBySdt(String sdt);

    Optional<KhachHang> findByEmailIgnoreCase(String email);

    List<KhachHang> findAllByOrderByIdDesc();
}

