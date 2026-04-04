package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ChiTietDatPhongRepository extends JpaRepository<ChiTietDatPhong, Long> {

    List<ChiTietDatPhong> findAllByOrderByIdDesc();

    List<ChiTietDatPhong> findByDatPhongIdOrderByIdDesc(Long datPhongId);

    List<ChiTietDatPhong> findByDatPhongIdIn(Collection<Long> datPhongIds);

    @Query("""
        select case when count(c) > 0 then true else false end
        from ChiTietDatPhong c
        join c.datPhong dp
        where c.phong.id = :phongId
          and (:datPhongIdBoQua is null or dp.id <> :datPhongIdBoQua)
          and dp.trangThai in :trangThaiApDung
          and dp.ngayNhan < :ngayTra
          and dp.ngayTra > :ngayNhan
        """)
    boolean existsLichPhongTrung(@Param("phongId") Long phongId,
                                 @Param("datPhongIdBoQua") Long datPhongIdBoQua,
                                 @Param("ngayNhan") LocalDate ngayNhan,
                                 @Param("ngayTra") LocalDate ngayTra,
                                 @Param("trangThaiApDung") Set<BookingStatus> trangThaiApDung);
}
