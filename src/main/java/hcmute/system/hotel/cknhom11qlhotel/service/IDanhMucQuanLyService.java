package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;

import java.util.List;

public interface IDanhMucQuanLyService {

    List<RoomResponse> layDanhSachPhong(RoomStatus roomStatus, String keyword);

    List<PromotionResponse> layDanhSachKhuyenMai(String keyword);

    List<ServiceResponse> layDanhSachDichVu(String keyword);

    List<LoaiPhong> layLoaiPhong();

    void capNhatPhong(Long roomId, RoomRequest request);

    void capNhatKhuyenMai(Long promotionId, PromotionRequest request);

    void capNhatDichVu(Long serviceId, ServiceRequest request);
}
