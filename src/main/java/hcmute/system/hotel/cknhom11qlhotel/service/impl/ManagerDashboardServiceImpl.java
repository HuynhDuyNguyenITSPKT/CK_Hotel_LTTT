package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.manager.ManagerBookingTrendDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.manager.ManagerDashboardStatsDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.manager.ManagerGuestBookingWindowDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IManagerDashboardService;
import hcmute.system.hotel.cknhom11qlhotel.stream.ManagerDashboardQueriesStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ManagerDashboardServiceImpl implements IManagerDashboardService {

    private static final DateTimeFormatter LABEL_FORMATTER = DateTimeFormatter.ofPattern("dd/MM");

    private final DatPhongRepository datPhongRepository;
    private final HoaDonRepository hoaDonRepository;
    private final PhongRepository phongRepository;
    private final ManagerDashboardQueriesStream managerDashboardQueriesStream;

    public ManagerDashboardServiceImpl(DatPhongRepository datPhongRepository,
                                       HoaDonRepository hoaDonRepository,
                                       PhongRepository phongRepository,
                                       ManagerDashboardQueriesStream managerDashboardQueriesStream) {
        this.datPhongRepository = datPhongRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.phongRepository = phongRepository;
        this.managerDashboardQueriesStream = managerDashboardQueriesStream;
    }

    @Override
    public ManagerDashboardStatsDto layThongKeTongQuan() {
        LocalDate homNay = LocalDate.now();
        LocalDate homQua = homNay.minusDays(1);

        long soKhachHomNay = demKhachHomNay(homNay);
        List<DatPhong> datPhongHaiNgay = timDatPhongTheoKhoangNgay(homQua.atStartOfDay(), homNay.plusDays(1).atStartOfDay());
        long soPhongDatHomNay = demSoPhongTheoNgay(datPhongHaiNgay, homNay);
        long soPhongDatHomQua = demSoPhongTheoNgay(datPhongHaiNgay, homQua);

        BigDecimal phanTramSoVoiHomQua = tinhPhanTramTangTruong(soPhongDatHomNay, soPhongDatHomQua);
        String nhanXuHuong = taoNhanXuHuong(soPhongDatHomNay, soPhongDatHomQua, phanTramSoVoiHomQua);

        return new ManagerDashboardStatsDto(
                soKhachHomNay,
                soPhongDatHomNay,
                soPhongDatHomQua,
                phanTramSoVoiHomQua,
                nhanXuHuong,
                toDoanhThuAnToan(hoaDonRepository.sumTongTien()),
                phongRepository.countByTrangThai(RoomStatus.AVAILABLE),
                phongRepository.countByTrangThai(RoomStatus.OCCUPIED),
                phongRepository.countByTrangThai(RoomStatus.CLEANING),
                phongRepository.countByTrangThai(RoomStatus.MAINTENANCE)
        );
    }

    @Override
    public ManagerBookingTrendDto layTrendPhongDatTheoNgay(int soNgay) {
        int soNgayAnToan = normalizeSoNgay(soNgay);
        LocalDate homNay = LocalDate.now();
        LocalDate ngayBatDau = homNay.minusDays(soNgayAnToan - 1L);

        List<DatPhong> datPhongTheoKhoangNgay = timDatPhongTheoKhoangNgay(
                ngayBatDau.atStartOfDay(),
                homNay.plusDays(1).atStartOfDay()
        );

        Map<LocalDate, Long> mapSoPhongTheoNgay = managerDashboardQueriesStream.mapSoPhongDatTheoNgay(datPhongTheoKhoangNgay);

        List<LocalDate> danhSachNgay = ngayBatDau.datesUntil(homNay.plusDays(1)).toList();
        List<String> labels = danhSachNgay.stream().map(this::toLabelNgay).toList();
        List<Long> values = danhSachNgay.stream().map(ngay -> mapSoPhongTheoNgay.getOrDefault(ngay, 0L)).toList();

        return new ManagerBookingTrendDto(labels, values);
    }

    @Override
    public ManagerGuestBookingWindowDto layBieuDoKhachDatTheoKhoangNgay(int soNgayTruoc, int soNgaySau) {
        int soNgayTruocAnToan = Math.max(0, soNgayTruoc);
        int soNgaySauAnToan = Math.max(0, soNgaySau);

        LocalDate homNay = LocalDate.now();
        LocalDate ngayBatDau = homNay.minusDays(soNgayTruocAnToan);
        LocalDate ngayKetThuc = homNay.plusDays(soNgaySauAnToan);

        List<DatPhong> datPhongTheoNgayNhan = datPhongRepository.findByNgayNhanBetweenAndTrangThaiNot(
                ngayBatDau,
                ngayKetThuc,
                BookingStatus.CANCELLED
        );
        Map<LocalDate, Long> mapSoKhachDatTheoNgay = managerDashboardQueriesStream.mapSoKhachDatTheoNgayNhan(datPhongTheoNgayNhan);

        List<LocalDate> danhSachNgay = taoDanhSachNgay(ngayBatDau, ngayKetThuc);
        List<String> labels = danhSachNgay.stream().map(this::toLabelNgay).toList();
        List<Long> values = danhSachNgay.stream().map(ngay -> mapSoKhachDatTheoNgay.getOrDefault(ngay, 0L)).toList();

        return new ManagerGuestBookingWindowDto(labels, values);
    }

    private int normalizeSoNgay(int soNgay) {
        return switch (soNgay) {
            case 14, 30 -> soNgay;
            default -> 7;
        };
    }

    private long demKhachHomNay(LocalDate homNay) {
        List<DatPhong> danhSachKhachHomNay = datPhongRepository.findByNgayNhanAndTrangThaiNot(homNay, BookingStatus.CANCELLED);
        return managerDashboardQueriesStream.demKhachHangDuyNhat(danhSachKhachHomNay);
    }

    private List<DatPhong> timDatPhongTheoKhoangNgay(LocalDateTime batDau, LocalDateTime ketThuc) {
        return datPhongRepository.findByNgayDatBetweenAndTrangThaiNot(batDau, ketThuc, BookingStatus.CANCELLED);
    }

    private long demSoPhongTheoNgay(List<DatPhong> danhSachDatPhong, LocalDate ngayMoc) {
        return managerDashboardQueriesStream.demSoPhongDatTheoNgay(danhSachDatPhong, ngayMoc);
    }

    private String toLabelNgay(LocalDate ngay) {
        return ngay.format(LABEL_FORMATTER);
    }

    private List<LocalDate> taoDanhSachNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        List<LocalDate> danhSachNgay = new java.util.ArrayList<>();
        LocalDate current = ngayBatDau;
        while (!current.isAfter(ngayKetThuc)) {
            danhSachNgay.add(current);
            current = current.plusDays(1);
        }
        return danhSachNgay;
    }

    private BigDecimal toDoanhThuAnToan(BigDecimal tongDoanhThu) {
        return tongDoanhThu == null ? BigDecimal.ZERO : tongDoanhThu;
    }

    private BigDecimal tinhPhanTramTangTruong(long homNay, long homQua) {
        if (homQua <= 0) {
            return homNay > 0 ? BigDecimal.valueOf(100) : BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(homNay - homQua)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(homQua), 1, RoundingMode.HALF_UP);
    }

    private String taoNhanXuHuong(long homNay, long homQua, BigDecimal phanTram) {
        if (homNay > homQua) {
            return "Tang " + phanTram.abs().setScale(1, RoundingMode.HALF_UP).toPlainString() + "% so voi hom qua";
        }
        if (homNay < homQua) {
            return "Giam " + phanTram.abs().setScale(1, RoundingMode.HALF_UP).toPlainString() + "% so voi hom qua";
        }
        return "Khong doi so voi hom qua";
    }
}
