package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.DichVu;

import java.util.List;

public interface DichVuRepository extends JpaRepository<DichVu, Long> {
    List<DichVu> findAllByOrderByIdDesc();
}

