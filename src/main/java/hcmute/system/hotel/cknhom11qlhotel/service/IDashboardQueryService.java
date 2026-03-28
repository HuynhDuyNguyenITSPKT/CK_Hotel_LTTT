package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanCheckInDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanCheckOutDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanChiTietDatPhongDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanDangOThemDichVuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanDatPhongDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanDichVuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanHoaDonThanhToanDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanKhuyenMaiDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanPhongDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanSuDungDichVuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanThongKeNhanhDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.TrangDuLieuDto;

import java.util.List;

public interface IDashboardQueryService {

    List<LeTanThongKeNhanhDto> layThongKeNhanh();

    List<LeTanPhongDto> layPhongChoDatPhong();

    List<LeTanDichVuDto> layDanhSachDichVu();

    List<LeTanKhuyenMaiDto> layDanhSachKhuyenMai();

    List<LeTanSuDungDichVuDto> layDanhSachSuDungDichVu();

    List<LeTanChiTietDatPhongDto> layDanhSachChiTietDatPhong();

    TrangDuLieuDto<LeTanCheckInDto> layTrangCheckIn(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang,
                                                       int kichThuoc,
                                                       String boLocTrangThaiThanhToan,
                                                       String sapXep);

    TrangDuLieuDto<LeTanPhongDto> layTrangQuanLyPhong(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang,
                                                       int kichThuoc,
                                                       String boLocTrangThaiDatPhong,
                                                       String sapXep);

    TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang, int kichThuoc, String sapXep);

    TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang,
                                                                     int kichThuoc,
                                                                     String boLocTrangThaiThanhToan,
                                                                     String sapXep);

    List<LeTanCheckInDto> layCheckInSapToi(int gioiHan);

    List<LeTanCheckOutDto> layCheckOutSapToi(int gioiHan);

    List<LeTanDatPhongDto> layDatPhongGanNhat(int gioiHan);
}
