package hcmute.system.hotel.cknhom11qlhotel.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PhongQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public PhongQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public List<Phong> sapXepTheoSoPhong(List<Object> duLieu) {
        return objectStreamSupport.streamTheoLoai(duLieu, Phong.class)
                .sorted(Comparator.comparing(Phong::getSoPhong, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    public List<Phong> locTheoTrangThai(List<Object> duLieu, Set<RoomStatus> tapTrangThai) {
        return objectStreamSupport.streamTheoLoai(duLieu, Phong.class)
                .filter(phong -> phong.getTrangThai() != null && tapTrangThai.contains(phong.getTrangThai()))
                .toList();
    }

    public Optional<Phong> timTheoId(List<Object> duLieu, Long phongId) {
        return objectStreamSupport.streamTheoLoai(duLieu, Phong.class)
                .filter(phong -> phong.getId() != null && phong.getId().equals(phongId))
                .findFirst();
    }
}
