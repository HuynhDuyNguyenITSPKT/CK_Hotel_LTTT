package hcmute.system.hotel.cknhom11qlhotel.stream;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.ThanhToan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ThanhToanQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public ThanhToanQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public Map<Long, BigDecimal> tongThanhToanTheoHoaDon(List<Object> duLieu) {
        Map<Long, BigDecimal> ketQua = new HashMap<>();
        objectStreamSupport.streamTheoLoai(duLieu, ThanhToan.class)
                .forEach(thanhToan -> {
                    if (thanhToan.getHoaDon() == null || thanhToan.getHoaDon().getId() == null) {
                        return;
                    }
                    BigDecimal soTien = thanhToan.getSoTien() == null ? BigDecimal.ZERO : thanhToan.getSoTien();
                    ketQua.merge(thanhToan.getHoaDon().getId(), soTien, BigDecimal::add);
                });
        return ketQua;
    }
}
