package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("select coalesce(sum(h.tongTien), 0) from HoaDon h")
    BigDecimal sumTongTien();

    @EntityGraph(attributePaths = {"nhanVien", "datPhong"})
    List<HoaDon> findTop10ByOrderByNgayTaoDesc();

    @EntityGraph(attributePaths = {"nhanVien", "datPhong"})
    List<HoaDon> findAllByOrderByNgayTaoDesc();

    List<HoaDon> findAllByOrderByNgayTaoAsc();
}

