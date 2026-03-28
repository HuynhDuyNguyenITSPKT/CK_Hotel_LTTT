package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;

public interface IDatPhongService {

    void taoDatPhong(LeTanTaoDatPhongFormDto form, Long nhanVienId);

    void huyDatPhong(Long datPhongId, Long nhanVienId);
}
