package hcmute.system.hotel.cknhom11qlhotel.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ManagerCatalogQueriesStream {

    public List<RoomResponse> locPhong(List<RoomResponse> rooms, RoomStatus roomStatus, String keyword) {
        return rooms.stream()
                .filter(Objects::nonNull)
                .filter(room -> roomStatus == null || room.getTrangThai() == roomStatus)
                .filter(room -> matchKeyword(keyword,
                        room.getSoPhong(),
                        room.getTenLoaiPhong(),
                        room.getMoTaLoaiPhong(),
                        room.getTrangThai() == null ? null : room.getTrangThai().name()))
                .toList();
    }

    public List<PromotionResponse> locKhuyenMai(List<PromotionResponse> promotions, String keyword) {
        return promotions.stream()
                .filter(Objects::nonNull)
                .filter(promotion -> matchKeyword(keyword,
                        promotion.getTen(),
                        promotion.getLoaiGiam() == null ? null : promotion.getLoaiGiam().name(),
                        promotion.getGiaTri() == null ? null : promotion.getGiaTri().toPlainString()))
                .toList();
    }

        public List<ServiceResponse> locDichVu(List<ServiceResponse> services, String keyword) {
        return services.stream()
            .filter(Objects::nonNull)
            .filter(service -> matchKeyword(keyword,
                service.getTen(),
                service.getGia() == null ? null : service.getGia().toPlainString()))
            .toList();
        }

    private boolean matchKeyword(String keyword, String... values) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        for (String value : values) {
            if (value != null && value.toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                return true;
            }
        }
        return false;
    }
}
