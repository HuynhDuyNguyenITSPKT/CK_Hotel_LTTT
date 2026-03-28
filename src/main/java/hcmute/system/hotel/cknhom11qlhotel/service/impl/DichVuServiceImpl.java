package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.service.IDichVuService;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import org.springframework.stereotype.Service;

@Service
public class DichVuServiceImpl implements IDichVuService {

    private final IReceptionistDashboardService receptionistDashboardService;

    public DichVuServiceImpl(IReceptionistDashboardService receptionistDashboardService) {
        this.receptionistDashboardService = receptionistDashboardService;
    }

    @Override
    public void themDichVuTrongThoiGianO(Long datPhongId, Long dichVuId, Integer soLuong, Long nhanVienId) {
        receptionistDashboardService.themDichVuTrongThoiGianO(datPhongId, dichVuId, soLuong, nhanVienId);
    }

    @Override
    public void xoaDichVuDaThem(Long suDungDichVuId, Long nhanVienId) {
        receptionistDashboardService.xoaDichVuDaThem(suDungDichVuId, nhanVienId);
    }
}
