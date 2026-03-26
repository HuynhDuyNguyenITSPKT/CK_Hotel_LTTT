package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DichVuRepository extends JpaRepository<DichVu, Long> {
    List<DichVu> findAllByOrderByIdDesc();
}

