package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.service.IDatPhongService;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import org.springframework.stereotype.Service;

@Service
public class DatPhongServiceImpl implements IDatPhongService {

    private final IReceptionistDashboardService receptionistDashboardService;

    public DatPhongServiceImpl(IReceptionistDashboardService receptionistDashboardService) {
        this.receptionistDashboardService = receptionistDashboardService;
    }

    @Override
    public void taoDatPhong(LeTanTaoDatPhongFormDto form, Long nhanVienId) {
        receptionistDashboardService.taoDatPhong(form, nhanVienId);
    }

    @Override
    public void huyDatPhong(Long datPhongId, Long nhanVienId) {
        receptionistDashboardService.huyDatPhong(datPhongId, nhanVienId);
    }
}
