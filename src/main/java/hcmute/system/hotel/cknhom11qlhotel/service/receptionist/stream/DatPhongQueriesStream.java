package hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class DatPhongQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public DatPhongQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public List<DatPhong> sapXepTheoNgayDatGiam(List<Object> duLieu) {
        return objectStreamSupport.streamTheoLoai(duLieu, DatPhong.class)
                .sorted(Comparator.comparing(DatPhong::getNgayDat, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    public List<DatPhong> locTheoTrangThai(List<Object> duLieu, Set<BookingStatus> tapTrangThai) {
        return objectStreamSupport.streamTheoLoai(duLieu, DatPhong.class)
                .filter(datPhong -> datPhong.getTrangThai() != null && tapTrangThai.contains(datPhong.getTrangThai()))
                .toList();
    }

    public Optional<DatPhong> timTheoId(List<Object> duLieu, Long datPhongId) {
        return objectStreamSupport.streamTheoLoai(duLieu, DatPhong.class)
                .filter(datPhong -> datPhong.getId() != null && datPhong.getId().equals(datPhongId))
                .findFirst();
    }
}
