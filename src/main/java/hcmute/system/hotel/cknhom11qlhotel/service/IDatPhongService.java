package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanKhachHangTraCuuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;

import java.util.Optional;

public interface IDatPhongService {

    void taoDatPhong(LeTanTaoDatPhongFormDto form, Long nhanVienId);

    void huyDatPhong(Long datPhongId, Long nhanVienId);

    Optional<LeTanKhachHangTraCuuDto> timKhachHangTheoSdt(String sdt);
}
