package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhongRepository extends JpaRepository<Phong, Long> {

    @EntityGraph(attributePaths = "loaiPhong")
    List<Phong> findAllByOrderByIdDesc();

    boolean existsBySoPhong(String soPhong);

    boolean existsByLoaiPhong_Id(Long loaiPhongId);
}

