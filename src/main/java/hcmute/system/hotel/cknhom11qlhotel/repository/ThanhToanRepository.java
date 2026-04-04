package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.ThanhToan;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {

    List<ThanhToan> findTop10ByOrderByNgayThanhToanDesc();

    List<ThanhToan> findAllByOrderByNgayThanhToanDesc();

    @Query("select coalesce(sum(t.soTien), 0) from ThanhToan t where t.hoaDon.id = :hoaDonId")
    BigDecimal tongThanhToanTheoHoaDonId(@Param("hoaDonId") Long hoaDonId);

    @Query("""
        select t.hoaDon.id, coalesce(sum(t.soTien), 0)
        from ThanhToan t
        where t.hoaDon.id in :hoaDonIds
        group by t.hoaDon.id
        """)
    List<Object[]> tongThanhToanTheoDanhSachHoaDon(@Param("hoaDonIds") Collection<Long> hoaDonIds);
}

