package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatPhongRepository extends JpaRepository<DatPhong, Long> {

    @EntityGraph(attributePaths = {
            "khachHang",
            "nhanVien",
            "chiTietDatPhongs",
            "chiTietDatPhongs.phong",
            "chiTietDatPhongs.phong.loaiPhong",
            "hoaDon"
    })
    List<DatPhong> findAllByOrderByNgayDatDesc();
}
