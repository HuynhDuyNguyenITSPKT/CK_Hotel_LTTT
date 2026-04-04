package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.KhuyenMai;

import java.util.List;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {
    List<KhuyenMai> findAllByOrderByIdDesc();
}

