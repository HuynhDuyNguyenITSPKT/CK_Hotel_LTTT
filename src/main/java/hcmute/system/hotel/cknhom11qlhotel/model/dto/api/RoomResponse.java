package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;

public record RoomResponse(
        Long id,
        String soPhong,
        RoomStatus trangThai,
        String imageUrl,
        Long loaiPhongId,
        String tenLoaiPhong,
        String moTaLoaiPhong
) {
}

