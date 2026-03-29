package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("select coalesce(sum(h.tongTien), 0) from HoaDon h")
    BigDecimal sumTongTien();

    List<HoaDon> findTop10ByOrderByNgayTaoDesc();

    List<HoaDon> findAllByOrderByNgayTaoDesc();

    List<HoaDon> findAllByOrderByNgayTaoAsc();

    Optional<HoaDon> findByDatPhongId(Long datPhongId);

    List<HoaDon> findByDatPhongIdIn(Collection<Long> datPhongIds);

    @Query("""
        select h.id
        from HoaDon h
        left join h.thanhToans t
        group by h.id, h.tongTien
        having coalesce(sum(t.soTien), 0) = 0
        """)
    Set<Long> findHoaDonIdsChuaThanhToan();

    @Query("""
        select h.id
        from HoaDon h
        left join h.thanhToans t
        group by h.id, h.tongTien
        having coalesce(sum(t.soTien), 0) > 0
           and coalesce(sum(t.soTien), 0) < h.tongTien
        """)
    Set<Long> findHoaDonIdsThanhToanMotPhan();

    @Query("""
        select h.id
        from HoaDon h
        left join h.thanhToans t
        group by h.id, h.tongTien
        having h.tongTien = 0 or coalesce(sum(t.soTien), 0) >= h.tongTien
        """)
    Set<Long> findHoaDonIdsDaThanhToan();

    @Query("""
        select count(h) > 0
        from HoaDon h
        join h.khuyenMais km
        where h.datPhong.khachHang.sdt = :sdt
          and km.id = :khuyenMaiId
          and (:excludeDatPhongId is null or h.datPhong.id <> :excludeDatPhongId)
        """)
    boolean existsKhuyenMaiDaDungBoiSdt(@Param("sdt") String sdt,
                                        @Param("khuyenMaiId") Long khuyenMaiId,
                                        @Param("excludeDatPhongId") Long excludeDatPhongId);
}

