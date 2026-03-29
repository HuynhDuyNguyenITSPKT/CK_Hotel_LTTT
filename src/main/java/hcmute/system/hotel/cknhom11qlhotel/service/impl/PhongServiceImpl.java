package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IPhongService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
@Transactional
public class PhongServiceImpl implements IPhongService {

    private final PhongRepository phongRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;

    public PhongServiceImpl(PhongRepository phongRepository,
                            ChiTietDatPhongRepository chiTietDatPhongRepository) {
        this.phongRepository = phongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
    }

    @Override
    public void capNhatTrangThaiPhong(Long phongId, RoomStatus trangThai) {
        if (phongId == null || trangThai == null) {
            throw new IllegalArgumentException("Du lieu cap nhat phong khong hop le");
        }

        Phong phong = phongRepository.findById(phongId)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay phong"));

        LocalDate homNay = LocalDate.now();
        boolean coKhachDangO = chiTietDatPhongRepository.existsLichPhongTrung(
                phongId,
                null,
                homNay,
                homNay.plusDays(1),
                Set.of(BookingStatus.CHECKED_IN)
        );

        if (coKhachDangO && trangThai != RoomStatus.OCCUPIED) {
            throw new IllegalArgumentException("Phòng đang có khách lưu trú, không thể chuyển sang trạng thái " + trangThai);
        }
        if (!coKhachDangO && trangThai == RoomStatus.OCCUPIED) {
            throw new IllegalArgumentException("Không có booking CHECKED_IN hiệu lực cho phòng này");
        }

        phong.setTrangThai(trangThai);
        phongRepository.save(phong);
    }
}
