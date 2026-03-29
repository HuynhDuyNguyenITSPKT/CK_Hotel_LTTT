package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.manager.ManagerBookingTrendDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.manager.ManagerDashboardStatsDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.manager.ManagerGuestBookingWindowDto;

public interface IBangDieuKhienQuanLyService {

    ManagerDashboardStatsDto layThongKeTongQuan();

    ManagerBookingTrendDto layTrendPhongDatTheoNgay(int soNgay);

    ManagerGuestBookingWindowDto layBieuDoKhachDatTheoKhoangNgay(int soNgayTruoc, int soNgaySau);
}
