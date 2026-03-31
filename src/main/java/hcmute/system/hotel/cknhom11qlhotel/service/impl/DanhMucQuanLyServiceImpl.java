package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IDanhMucQuanLyService;
import hcmute.system.hotel.cknhom11qlhotel.stream.ManagerCatalogQueriesStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DanhMucQuanLyServiceImpl implements IDanhMucQuanLyService {

    private final QuanLyPhongService quanLyPhongService;
    private final QuanLyKhuyenMaiService quanLyKhuyenMaiService;
    private final QuanLyDichVuService quanLyDichVuService;
    private final ManagerCatalogQueriesStream managerCatalogQueriesStream;

    public DanhMucQuanLyServiceImpl(QuanLyPhongService quanLyPhongService,
                                    QuanLyKhuyenMaiService quanLyKhuyenMaiService,
                                    QuanLyDichVuService quanLyDichVuService,
                                    ManagerCatalogQueriesStream managerCatalogQueriesStream) {
        this.quanLyPhongService = quanLyPhongService;
        this.quanLyKhuyenMaiService = quanLyKhuyenMaiService;
        this.quanLyDichVuService = quanLyDichVuService;
        this.managerCatalogQueriesStream = managerCatalogQueriesStream;
    }

    @Override
    public List<RoomResponse> layDanhSachPhong(RoomStatus roomStatus, String keyword) {
        return managerCatalogQueriesStream.locPhong(quanLyPhongService.layDanhSachPhong(), roomStatus, keyword);
    }

    @Override
    public List<PromotionResponse> layDanhSachKhuyenMai(String keyword) {
        return managerCatalogQueriesStream.locKhuyenMai(quanLyKhuyenMaiService.layDanhSachKhuyenMai(), keyword);
    }

    @Override
    public List<ServiceResponse> layDanhSachDichVu(String keyword) {
        return managerCatalogQueriesStream.locDichVu(quanLyDichVuService.layDanhSachDichVu(), keyword);
    }

    @Override
    public List<LoaiPhong> layLoaiPhong() {
        return quanLyPhongService.layDanhSachLoaiPhong();
    }

    @Override
    @Transactional
    public void capNhatPhong(Long roomId, RoomRequest request) {
        quanLyPhongService.capNhatPhong(roomId, request, null);
    }

    @Override
    @Transactional
    public void capNhatKhuyenMai(Long promotionId, PromotionRequest request) {
        quanLyKhuyenMaiService.capNhatKhuyenMai(promotionId, request);
    }

    @Override
    @Transactional
    public void capNhatDichVu(Long serviceId, ServiceRequest request) {
        quanLyDichVuService.capNhatDichVu(serviceId, request, null);
    }
}
