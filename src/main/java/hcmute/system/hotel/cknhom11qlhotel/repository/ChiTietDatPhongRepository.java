package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietDatPhongRepository extends JpaRepository<ChiTietDatPhong, Long> {

    @EntityGraph(attributePaths = {
            "datPhong",
            "datPhong.khachHang",
            "phong",
            "phong.loaiPhong"
    })
    List<ChiTietDatPhong> findAllByOrderByNgayNhanDesc();
}
