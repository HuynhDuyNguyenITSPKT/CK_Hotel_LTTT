package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.SuDungDichVu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuDungDichVuRepository extends JpaRepository<SuDungDichVu, Long> {

    @EntityGraph(attributePaths = {"datPhong", "phong", "dichVu"})
    List<SuDungDichVu> findAllByOrderByThoiDiemDesc();
}
