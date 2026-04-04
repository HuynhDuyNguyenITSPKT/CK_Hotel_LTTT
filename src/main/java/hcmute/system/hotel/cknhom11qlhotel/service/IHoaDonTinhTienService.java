package hcmute.system.hotel.cknhom11qlhotel.service;

import java.math.BigDecimal;
import java.util.List;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.NhanVien;

public interface IHoaDonTinhTienService {

    HoaDon capNhatTongTienHoaDonTheoThucTe(DatPhong datPhong,
                                           List<ChiTietDatPhong> danhSachChiTiet,
                                           NhanVien nhanVienThaoTac);

    String dinhDangTien(BigDecimal soTien);
}
