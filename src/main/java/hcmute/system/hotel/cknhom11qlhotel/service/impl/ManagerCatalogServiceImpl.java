package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IAdminManagementService;
import hcmute.system.hotel.cknhom11qlhotel.service.IManagerCatalogService;
import hcmute.system.hotel.cknhom11qlhotel.stream.ManagerCatalogQueriesStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ManagerCatalogServiceImpl implements IManagerCatalogService {

    private final IAdminManagementService adminManagementService;
    private final ManagerCatalogQueriesStream managerCatalogQueriesStream;

    public ManagerCatalogServiceImpl(IAdminManagementService adminManagementService,
                                     ManagerCatalogQueriesStream managerCatalogQueriesStream) {
        this.adminManagementService = adminManagementService;
        this.managerCatalogQueriesStream = managerCatalogQueriesStream;
    }

    @Override
    public List<RoomResponse> layDanhSachPhong(RoomStatus roomStatus, String keyword) {
        return managerCatalogQueriesStream.locPhong(adminManagementService.getRooms(), roomStatus, keyword);
    }

    @Override
    public List<PromotionResponse> layDanhSachKhuyenMai(String keyword) {
        return managerCatalogQueriesStream.locKhuyenMai(adminManagementService.getPromotions(), keyword);
    }

    @Override
    public List<LoaiPhong> layLoaiPhong() {
        return adminManagementService.getRoomTypes();
    }

    @Override
    public void capNhatPhong(Long roomId, RoomRequest request) {
        adminManagementService.updateRoom(roomId, request, null);
    }

    @Override
    public void capNhatKhuyenMai(Long promotionId, PromotionRequest request) {
        adminManagementService.updatePromotion(promotionId, request);
    }
}
