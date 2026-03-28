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
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanThongKeNhanhDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.TrangDuLieuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;

import java.math.BigDecimal;
import java.util.List;

public interface IReceptionistDashboardService {

    List<LeTanThongKeNhanhDto> layThongKeNhanh();

    List<LeTanCheckInDto> layCheckInSapToi(int gioiHan);

    List<LeTanCheckOutDto> layCheckOutSapToi(int gioiHan);

    List<LeTanDatPhongDto> layDatPhongGanNhat(int gioiHan);

    TrangDuLieuDto<LeTanCheckInDto> layTrangCheckIn(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang,
                                                       int kichThuoc,
                                                       String boLocTrangThaiThanhToan,
                                                       String sapXep);

    TrangDuLieuDto<LeTanPhongDto> layTrangQuanLyPhong(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang,
                                                       int kichThuoc,
                                                       String boLocTrangThaiDatPhong,
                                                       String sapXep);

    TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang,
                                                                     int kichThuoc,
                                                                     String boLocTrangThaiThanhToan,
                                                                     String sapXep);

    TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang, int kichThuoc);

    TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang, int kichThuoc, String sapXep);

    List<LeTanPhongDto> layPhongChoDatPhong();

    List<LeTanDichVuDto> layDanhSachDichVu();

    List<LeTanKhuyenMaiDto> layDanhSachKhuyenMai();

    List<LeTanSuDungDichVuDto> layDanhSachSuDungDichVu();

    List<LeTanChiTietDatPhongDto> layDanhSachChiTietDatPhong();

    void thucHienCheckIn(Long datPhongId, Long nhanVienId);

    void thucHienCheckOut(Long datPhongId, Long nhanVienId);

    void thucHienThanhToan(Long datPhongId, BigDecimal soTienThanhToan, PaymentMethod phuongThuc, Long nhanVienId);

    void themDichVuTrongThoiGianO(Long datPhongId, Long dichVuId, Integer soLuong, Long nhanVienId);

    void xoaDichVuDaThem(Long suDungDichVuId, Long nhanVienId);

    void huyDatPhong(Long datPhongId, Long nhanVienId);

    void apDungMaKhuyenMai(Long datPhongId, String maKhuyenMai, Long nhanVienId);

    void apDungKhuyenMaiVaThanhToan(Long datPhongId,
                                    String maKhuyenMai,
                                    BigDecimal soTienThanhToan,
                                    PaymentMethod phuongThuc,
                                    Long nhanVienId);

    void capNhatTrangThaiPhong(Long phongId, RoomStatus trangThai);

    void taoDatPhong(LeTanTaoDatPhongFormDto form, Long nhanVienId);
}
