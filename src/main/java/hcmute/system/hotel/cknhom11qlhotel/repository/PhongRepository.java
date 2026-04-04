package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhongRepository extends JpaRepository<Phong, Long> {

    List<Phong> findAllByOrderByIdDesc();

    List<Phong> findAllByOrderBySoPhongAsc();

    List<Phong> findByTrangThaiOrderBySoPhongAsc(RoomStatus trangThai);

    long countByTrangThai(RoomStatus trangThai);

    boolean existsBySoPhong(String soPhong);

    boolean existsByLoaiPhong_Id(Long loaiPhongId);
}

