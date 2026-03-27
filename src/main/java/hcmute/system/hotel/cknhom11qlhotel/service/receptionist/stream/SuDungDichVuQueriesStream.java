package hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.SuDungDichVu;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SuDungDichVuQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public SuDungDichVuQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public List<SuDungDichVu> locTheoDatPhongId(List<Object> duLieu, Long datPhongId) {
        return objectStreamSupport.streamTheoLoai(duLieu, SuDungDichVu.class)
                .filter(item -> item.getDatPhong() != null
                        && item.getDatPhong().getId() != null
                        && item.getDatPhong().getId().equals(datPhongId))
                .toList();
    }

    public Map<Long, BigDecimal> tongTienTheoDatPhong(List<Object> duLieu) {
        Map<Long, BigDecimal> ketQua = new HashMap<>();

        objectStreamSupport.streamTheoLoai(duLieu, SuDungDichVu.class)
                .forEach(item -> {
                    if (item.getDatPhong() == null || item.getDatPhong().getId() == null || item.getDichVu() == null) {
                        return;
                    }
                    BigDecimal gia = item.getDichVu().getGia() == null ? BigDecimal.ZERO : item.getDichVu().getGia();
                    int soLuong = item.getSoLuong() == null ? 0 : Math.max(0, item.getSoLuong());
                    BigDecimal tongTien = gia.multiply(BigDecimal.valueOf(soLuong));
                    ketQua.merge(item.getDatPhong().getId(), tongTien, BigDecimal::add);
                });

        return ketQua;
    }
}
