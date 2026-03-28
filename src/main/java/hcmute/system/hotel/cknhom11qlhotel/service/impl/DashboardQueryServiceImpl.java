package hcmute.system.hotel.cknhom11qlhotel.service.impl;

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
import hcmute.system.hotel.cknhom11qlhotel.service.IDashboardQueryService;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardQueryServiceImpl implements IDashboardQueryService {

    private final IReceptionistDashboardService receptionistDashboardService;

    public DashboardQueryServiceImpl(IReceptionistDashboardService receptionistDashboardService) {
        this.receptionistDashboardService = receptionistDashboardService;
    }

    @Override
    public List<LeTanThongKeNhanhDto> layThongKeNhanh() {
        return receptionistDashboardService.layThongKeNhanh();
    }

    @Override
    public List<LeTanPhongDto> layPhongChoDatPhong() {
        return receptionistDashboardService.layPhongChoDatPhong();
    }

    @Override
    public List<LeTanDichVuDto> layDanhSachDichVu() {
        return receptionistDashboardService.layDanhSachDichVu();
    }

    @Override
    public List<LeTanKhuyenMaiDto> layDanhSachKhuyenMai() {
        return receptionistDashboardService.layDanhSachKhuyenMai();
    }

    @Override
    public List<LeTanSuDungDichVuDto> layDanhSachSuDungDichVu() {
        return receptionistDashboardService.layDanhSachSuDungDichVu();
    }

    @Override
    public List<LeTanChiTietDatPhongDto> layDanhSachChiTietDatPhong() {
        return receptionistDashboardService.layDanhSachChiTietDatPhong();
    }

    @Override
    public TrangDuLieuDto<LeTanCheckInDto> layTrangCheckIn(int trang, int kichThuoc) {
        return receptionistDashboardService.layTrangCheckIn(trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang,
                                                              int kichThuoc,
                                                              String boLocTrangThaiThanhToan,
                                                              String sapXep) {
        return receptionistDashboardService.layTrangCheckOut(trang, kichThuoc, boLocTrangThaiThanhToan, sapXep);
    }

    @Override
    public TrangDuLieuDto<LeTanPhongDto> layTrangQuanLyPhong(int trang, int kichThuoc) {
        return receptionistDashboardService.layTrangQuanLyPhong(trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang,
                                                              int kichThuoc,
                                                              String boLocTrangThaiDatPhong,
                                                              String sapXep) {
        return receptionistDashboardService.layTrangDatPhong(trang, kichThuoc, boLocTrangThaiDatPhong, sapXep);
    }

    @Override
    public TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang, int kichThuoc, String sapXep) {
        return receptionistDashboardService.layTrangDangOThemDichVu(trang, kichThuoc, sapXep);
    }

    @Override
    public TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang,
                                                                            int kichThuoc,
                                                                            String boLocTrangThaiThanhToan,
                                                                            String sapXep) {
        return receptionistDashboardService.layTrangHoaDonThanhToan(trang, kichThuoc, boLocTrangThaiThanhToan, sapXep);
    }

    @Override
    public List<LeTanCheckInDto> layCheckInSapToi(int gioiHan) {
        return receptionistDashboardService.layCheckInSapToi(gioiHan);
    }

    @Override
    public List<LeTanCheckOutDto> layCheckOutSapToi(int gioiHan) {
        return receptionistDashboardService.layCheckOutSapToi(gioiHan);
    }

    @Override
    public List<LeTanDatPhongDto> layDatPhongGanNhat(int gioiHan) {
        return receptionistDashboardService.layDatPhongGanNhat(gioiHan);
    }
}
