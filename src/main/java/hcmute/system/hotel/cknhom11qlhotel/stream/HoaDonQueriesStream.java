package hcmute.system.hotel.cknhom11qlhotel.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HoaDonQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public HoaDonQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public Map<Long, HoaDon> mapTheoDatPhongId(List<Object> duLieu) {
        Map<Long, HoaDon> ketQua = new HashMap<>();
        objectStreamSupport.streamTheoLoai(duLieu, HoaDon.class)
                .forEach(hoaDon -> {
                    if (hoaDon.getDatPhong() != null && hoaDon.getDatPhong().getId() != null) {
                        ketQua.put(hoaDon.getDatPhong().getId(), hoaDon);
                    }
                });
        return ketQua;
    }

    public BigDecimal tongHoaDon(List<Object> duLieu) {
        return objectStreamSupport.streamTheoLoai(duLieu, HoaDon.class)
                .map(HoaDon::getTongTien)
                .filter(value -> value != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
