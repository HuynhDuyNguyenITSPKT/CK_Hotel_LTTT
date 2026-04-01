package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;

import java.math.BigDecimal;
import java.util.List;

public interface IHoaDonTinhTienService {

    HoaDon capNhatTongTienHoaDonTheoThucTe(DatPhong datPhong,
                                           List<ChiTietDatPhong> danhSachChiTiet,
                                           NhanVien nhanVienThaoTac);

    String dinhDangTien(BigDecimal soTien);
}
