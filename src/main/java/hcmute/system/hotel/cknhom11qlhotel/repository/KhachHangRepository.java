package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    boolean existsBySdt(String sdt);

    boolean existsByEmail(String email);

    Optional<KhachHang> findBySdt(String sdt);

    Optional<KhachHang> findByEmailIgnoreCase(String email);

    List<KhachHang> findAllByOrderByIdDesc();
}

