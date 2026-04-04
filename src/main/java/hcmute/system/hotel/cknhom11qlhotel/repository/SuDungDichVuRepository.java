package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.SuDungDichVu;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface SuDungDichVuRepository extends JpaRepository<SuDungDichVu, Long> {

    List<SuDungDichVu> findAllByOrderByThoiDiemDesc();

    List<SuDungDichVu> findByDatPhongIdOrderByThoiDiemDesc(Long datPhongId);

    @Query("""
        select coalesce(sum(sd.soLuong * dv.gia), 0)
        from SuDungDichVu sd
        join sd.dichVu dv
        where sd.datPhong.id = :datPhongId
        """)
    BigDecimal tinhTongTienTheoDatPhongId(@Param("datPhongId") Long datPhongId);

    @Query("""
        select sd.datPhong.id, coalesce(sum(sd.soLuong * dv.gia), 0)
        from SuDungDichVu sd
        join sd.dichVu dv
        where sd.datPhong.id in :datPhongIds
        group by sd.datPhong.id
        """)
    List<Object[]> tongTienTheoDanhSachDatPhongIds(@Param("datPhongIds") Collection<Long> datPhongIds);

    @Query("""
        select sd.datPhong.id, coalesce(sum(sd.soLuong), 0)
        from SuDungDichVu sd
        where sd.datPhong.id in :datPhongIds
        group by sd.datPhong.id
        """)
    List<Object[]> tongSoLuongTheoDanhSachDatPhongIds(@Param("datPhongIds") Collection<Long> datPhongIds);
}
