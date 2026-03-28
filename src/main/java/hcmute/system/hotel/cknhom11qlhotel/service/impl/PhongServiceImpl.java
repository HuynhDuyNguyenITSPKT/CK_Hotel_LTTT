package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IPhongService;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import org.springframework.stereotype.Service;

@Service
public class PhongServiceImpl implements IPhongService {

    private final IReceptionistDashboardService receptionistDashboardService;

    public PhongServiceImpl(IReceptionistDashboardService receptionistDashboardService) {
        this.receptionistDashboardService = receptionistDashboardService;
    }

    @Override
    public void capNhatTrangThaiPhong(Long phongId, RoomStatus trangThai) {
        receptionistDashboardService.capNhatTrangThaiPhong(phongId, trangThai);
    }
}
