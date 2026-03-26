package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ThanhToan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {

    @EntityGraph(attributePaths = {"hoaDon"})
    List<ThanhToan> findTop10ByOrderByNgayThanhToanDesc();
}

