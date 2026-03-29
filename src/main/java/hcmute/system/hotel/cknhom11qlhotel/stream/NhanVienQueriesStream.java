package hcmute.system.hotel.cknhom11qlhotel.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NhanVienQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public NhanVienQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public Optional<NhanVien> timTheoId(List<Object> duLieu, Long nhanVienId) {
        return objectStreamSupport.streamTheoLoai(duLieu, NhanVien.class)
                .filter(nhanVien -> nhanVien.getId() != null && nhanVien.getId().equals(nhanVienId))
                .findFirst();
    }
}
