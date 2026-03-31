package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomTypeRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class QuanTriPhongDieuPhoiService {

    private final QuanLyPhongService quanLyPhongService;

    public QuanTriPhongDieuPhoiService(QuanLyPhongService quanLyPhongService) {
        this.quanLyPhongService = quanLyPhongService;
    }

    public List<RoomResponse> getRooms() {
        return quanLyPhongService.layDanhSachPhong();
    }

    public List<LoaiPhong> getRoomTypes() {
        return quanLyPhongService.layDanhSachLoaiPhong();
    }

    public LoaiPhong createRoomType(RoomTypeRequest request, MultipartFile imageFile) {
        return quanLyPhongService.taoLoaiPhong(request, imageFile);
    }

    public LoaiPhong updateRoomType(Long roomTypeId, RoomTypeRequest request, MultipartFile imageFile) {
        return quanLyPhongService.capNhatLoaiPhong(roomTypeId, request, imageFile);
    }

    public void deleteRoomType(Long roomTypeId) {
        quanLyPhongService.xoaLoaiPhong(roomTypeId);
    }

    public RoomResponse createRoom(RoomRequest request, MultipartFile imageFile) {
        return quanLyPhongService.taoPhong(request, imageFile);
    }

    public RoomResponse updateRoom(Long roomId, RoomRequest request, MultipartFile imageFile) {
        return quanLyPhongService.capNhatPhong(roomId, request, imageFile);
    }

    public void deleteRoom(Long roomId) {
        quanLyPhongService.xoaPhong(roomId);
    }
}
