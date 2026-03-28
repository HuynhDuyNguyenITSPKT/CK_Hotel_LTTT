package hcmute.system.hotel.cknhom11qlhotel.stream;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class ObjectStreamSupport {

    public <T> Stream<T> streamTheoLoai(List<Object> duLieu, Class<T> lopDuLieu) {
        if (duLieu == null || duLieu.isEmpty()) {
            return Stream.empty();
        }
        return duLieu.stream()
                .filter(lopDuLieu::isInstance)
                .map(lopDuLieu::cast);
    }
}
