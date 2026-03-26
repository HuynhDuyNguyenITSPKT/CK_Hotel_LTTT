package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomRequest(
        @NotBlank(message = "Số phòng không được để trống")
        String soPhong,

        String imageUrl,

        @NotNull(message = "Trạng thái phòng không được để trống")
        RoomStatus trangThai,

        @NotNull(message = "Loại phòng không được để trống")
        Long loaiPhongId
) {
}

