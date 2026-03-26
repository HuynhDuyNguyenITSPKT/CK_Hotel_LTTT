package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {
    List<KhuyenMai> findAllByOrderByIdDesc();
}

