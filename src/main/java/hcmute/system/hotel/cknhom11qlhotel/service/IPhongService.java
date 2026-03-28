package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;

public interface IPhongService {

    void capNhatTrangThaiPhong(Long phongId, RoomStatus trangThai);
}
