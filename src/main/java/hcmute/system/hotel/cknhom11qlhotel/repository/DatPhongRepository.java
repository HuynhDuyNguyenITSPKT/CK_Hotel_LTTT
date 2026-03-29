package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface DatPhongRepository extends JpaRepository<DatPhong, Long> {

    List<DatPhong> findAllByOrderByNgayDatDesc();

    List<DatPhong> findTop6ByOrderByNgayDatDesc();

    List<DatPhong> findByTrangThaiInOrderByNgayDatDesc(Collection<BookingStatus> trangThais);

    List<DatPhong> findByTrangThaiInOrderByNgayNhanAscIdAsc(Collection<BookingStatus> trangThais);

    List<DatPhong> findByTrangThaiInOrderByNgayTraAscIdAsc(Collection<BookingStatus> trangThais);

    List<DatPhong> findByTrangThaiOrderByNgayDatDesc(BookingStatus trangThai);

    List<DatPhong> findByTrangThaiOrderByIdDesc(BookingStatus trangThai);

    long countByNgayNhanAndTrangThaiNot(LocalDate ngayNhan, BookingStatus trangThai);

    long countByNgayTraAndTrangThaiNot(LocalDate ngayTra, BookingStatus trangThai);

    long countByTrangThaiIn(Collection<BookingStatus> trangThais);
}
