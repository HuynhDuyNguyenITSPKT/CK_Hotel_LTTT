package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.service.ICheckInOutService;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import org.springframework.stereotype.Service;

@Service
public class CheckInOutServiceImpl implements ICheckInOutService {

    private final IReceptionistDashboardService receptionistDashboardService;

    public CheckInOutServiceImpl(IReceptionistDashboardService receptionistDashboardService) {
        this.receptionistDashboardService = receptionistDashboardService;
    }

    @Override
    public void thucHienCheckIn(Long datPhongId, Long nhanVienId) {
        receptionistDashboardService.thucHienCheckIn(datPhongId, nhanVienId);
    }

    @Override
    public void thucHienCheckOut(Long datPhongId, Long nhanVienId) {
        receptionistDashboardService.thucHienCheckOut(datPhongId, nhanVienId);
    }
}
