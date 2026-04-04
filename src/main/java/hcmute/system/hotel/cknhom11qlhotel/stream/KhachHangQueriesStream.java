package hcmute.system.hotel.cknhom11qlhotel.stream;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.KhachHang;

import java.util.List;
import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KhachHangQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public KhachHangQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public Optional<KhachHang> timTheoSdtHoacEmail(List<Object> duLieu, String sdt, String email) {
        String sdtChuan = sdt == null ? "" : sdt.trim();
        String emailChuan = email == null ? "" : email.trim().toLowerCase();

        return objectStreamSupport.streamTheoLoai(duLieu, KhachHang.class)
                .filter(khachHang -> {
                    String sdtKhach = khachHang.getSdt() == null ? "" : khachHang.getSdt().trim();
                    String emailKhach = khachHang.getEmail() == null ? "" : khachHang.getEmail().trim().toLowerCase();
                    return (!sdtChuan.isBlank() && sdtChuan.equals(sdtKhach))
                            || (!emailChuan.isBlank() && emailChuan.equals(emailKhach));
                })
                .findFirst();
    }
}
