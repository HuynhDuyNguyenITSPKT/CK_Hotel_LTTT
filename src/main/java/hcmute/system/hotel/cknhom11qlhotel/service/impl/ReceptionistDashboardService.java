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
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanThongKeNhanhDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.TrangDuLieuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.SuDungDichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ThanhToan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import hcmute.system.hotel.cknhom11qlhotel.stream.ChiTietDatPhongQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.DatPhongQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.HoaDonQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.KhachHangQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.NhanVienQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.PhongQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.SuDungDichVuQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.stream.ThanhToanQueriesStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceptionistDashboardService implements IReceptionistDashboardService {

    private final DatPhongRepository datPhongRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;
    private final PhongRepository phongRepository;
    private final DichVuRepository dichVuRepository;
    private final SuDungDichVuRepository suDungDichVuRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final KhachHangRepository khachHangRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final NhanVienRepository nhanVienRepository;

    private final DatPhongQueriesStream datPhongQueriesStream;
    private final ChiTietDatPhongQueriesStream chiTietDatPhongQueriesStream;
    private final PhongQueriesStream phongQueriesStream;
    private final SuDungDichVuQueriesStream suDungDichVuQueriesStream;
    private final HoaDonQueriesStream hoaDonQueriesStream;
    private final ThanhToanQueriesStream thanhToanQueriesStream;
    private final KhachHangQueriesStream khachHangQueriesStream;
    private final NhanVienQueriesStream nhanVienQueriesStream;

    public ReceptionistDashboardService(DatPhongRepository datPhongRepository,
                                        ChiTietDatPhongRepository chiTietDatPhongRepository,
                                        PhongRepository phongRepository,
                                        DichVuRepository dichVuRepository,
                                        SuDungDichVuRepository suDungDichVuRepository,
                                        HoaDonRepository hoaDonRepository,
                                        ThanhToanRepository thanhToanRepository,
                                        KhachHangRepository khachHangRepository,
                                        KhuyenMaiRepository khuyenMaiRepository,
                                        NhanVienRepository nhanVienRepository,
                                        DatPhongQueriesStream datPhongQueriesStream,
                                        ChiTietDatPhongQueriesStream chiTietDatPhongQueriesStream,
                                        PhongQueriesStream phongQueriesStream,
                                        SuDungDichVuQueriesStream suDungDichVuQueriesStream,
                                        HoaDonQueriesStream hoaDonQueriesStream,
                                        ThanhToanQueriesStream thanhToanQueriesStream,
                                        KhachHangQueriesStream khachHangQueriesStream,
                                        NhanVienQueriesStream nhanVienQueriesStream) {
        this.datPhongRepository = datPhongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.phongRepository = phongRepository;
        this.dichVuRepository = dichVuRepository;
        this.suDungDichVuRepository = suDungDichVuRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.khachHangRepository = khachHangRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.datPhongQueriesStream = datPhongQueriesStream;
        this.chiTietDatPhongQueriesStream = chiTietDatPhongQueriesStream;
        this.phongQueriesStream = phongQueriesStream;
        this.suDungDichVuQueriesStream = suDungDichVuQueriesStream;
        this.hoaDonQueriesStream = hoaDonQueriesStream;
        this.thanhToanQueriesStream = thanhToanQueriesStream;
        this.khachHangQueriesStream = khachHangQueriesStream;
        this.nhanVienQueriesStream = nhanVienQueriesStream;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanThongKeNhanhDto> layThongKeNhanh() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        List<HoaDon> hoaDons = hoaDonRepository.findAllByOrderByNgayTaoDesc();

        LocalDate homNay = LocalDate.now();
        long tongCheckInHomNay = datPhongs.stream()
            .filter(datPhong -> datPhong.getTrangThai() != BookingStatus.CANCELLED)
            .filter(datPhong -> homNay.equals(datPhong.getNgayNhan()))
                .count();

        long tongCheckOutHomNay = datPhongs.stream()
            .filter(datPhong -> datPhong.getTrangThai() != BookingStatus.CANCELLED)
            .filter(datPhong -> homNay.equals(datPhong.getNgayTra()))
            .count();

        long tongPhongSanSang = phongQueriesStream.locTheoTrangThai(
                toObjectList(phongs),
                Set.of(RoomStatus.AVAILABLE)
        ).size();

        long tongYeuCauDangXuLy = datPhongQueriesStream.locTheoTrangThai(
                toObjectList(datPhongs),
                Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        ).size();

        BigDecimal tongDoanhThu = hoaDonQueriesStream.tongHoaDon(toObjectList(hoaDons));

        return List.of(
                new LeTanThongKeNhanhDto("Check-in hom nay", String.valueOf(tongCheckInHomNay), "Luot check-in den theo lich", "fa-solid fa-door-open", "from-cyan-500 to-blue-600"),
                new LeTanThongKeNhanhDto("Check-out hom nay", String.valueOf(tongCheckOutHomNay), "Luot can hoan tat tra phong", "fa-solid fa-door-closed", "from-emerald-500 to-teal-600"),
                new LeTanThongKeNhanhDto("Phong san sang", String.valueOf(tongPhongSanSang), "Phong trang thai AVAILABLE", "fa-solid fa-bed", "from-indigo-500 to-violet-600"),
                new LeTanThongKeNhanhDto("Yeu cau dang xu ly", String.valueOf(tongYeuCauDangXuLy), "Booking PENDING/CONFIRMED", "fa-solid fa-list-check", "from-amber-500 to-orange-600"),
                new LeTanThongKeNhanhDto("Tong doanh thu", dinhDangTien(tongDoanhThu), "Tong hoa don da ghi nhan", "fa-solid fa-file-invoice-dollar", "from-rose-500 to-pink-600")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanCheckInDto> layCheckInSapToi(int gioiHan) {
        return duLieuCheckIn().stream().limit(Math.max(1, gioiHan)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanCheckOutDto> layCheckOutSapToi(int gioiHan) {
        return duLieuCheckOut().stream().limit(Math.max(1, gioiHan)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanDatPhongDto> layDatPhongGanNhat(int gioiHan) {
        return duLieuDatPhong().stream().limit(Math.max(1, gioiHan)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanCheckInDto> layTrangCheckIn(int trang, int kichThuoc) {
        return phanTrang(duLieuCheckIn(), trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang, int kichThuoc) {
        return layTrangCheckOut(trang, kichThuoc, null, "moi-nhat");
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang,
                                                              int kichThuoc,
                                                              String boLocTrangThaiThanhToan,
                                                              String sapXep) {
        List<LeTanCheckOutDto> duLieu = apDungSapXepCheckOut(duLieuCheckOut(), sapXep);
        if (boLocTrangThaiThanhToan != null && !boLocTrangThaiThanhToan.isBlank()) {
            String boLoc = boLocTrangThaiThanhToan.trim().toLowerCase(Locale.ROOT);
            duLieu = duLieu.stream()
                .filter(item -> khopBoLocTrangThaiThanhToan(item.getTrangThaiThanhToan(), boLoc))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanPhongDto> layTrangQuanLyPhong(int trang, int kichThuoc) {
        return phanTrang(duLieuPhong(), trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang, int kichThuoc) {
        return layTrangDatPhong(trang, kichThuoc, null, "moi-nhat");
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang,
                                                              int kichThuoc,
                                                              String boLocTrangThaiDatPhong,
                                                              String sapXep) {
        List<LeTanDatPhongDto> duLieu = apDungSapXepDatPhong(duLieuDatPhong(), sapXep);
        if (boLocTrangThaiDatPhong != null && !boLocTrangThaiDatPhong.isBlank()) {
            String boLoc = boLocTrangThaiDatPhong.trim().toUpperCase(Locale.ROOT);
            duLieu = duLieu.stream()
                    .filter(item -> item.getTrangThai() != null && item.getTrangThai().name().equals(boLoc))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang, int kichThuoc) {
        return layTrangHoaDonThanhToan(trang, kichThuoc, null, "moi-nhat");
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang,
                                                                            int kichThuoc,
                                                                            String boLocTrangThaiThanhToan,
                                                                            String sapXep) {
        List<LeTanHoaDonThanhToanDto> duLieu = apDungSapXepHoaDon(duLieuHoaDonThanhToan(), sapXep);
        if (boLocTrangThaiThanhToan != null && !boLocTrangThaiThanhToan.isBlank()) {
            String boLoc = boLocTrangThaiThanhToan.trim().toLowerCase(Locale.ROOT);
            duLieu = duLieu.stream()
                .filter(item -> khopBoLocTrangThaiThanhToan(item.getTrangThaiThanhToan(), boLoc))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang, int kichThuoc) {
        return layTrangDangOThemDichVu(trang, kichThuoc, "moi-nhat");
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang, int kichThuoc, String sapXep) {
        List<LeTanDangOThemDichVuDto> duLieu = apDungSapXepDangO(duLieuDangOThemDichVu(), sapXep);
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanPhongDto> layPhongChoDatPhong() {
        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        return phongs.stream()
                .filter(phong -> phong.getTrangThai() == RoomStatus.AVAILABLE)
                .map(this::toLeTanPhongDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanDichVuDto> layDanhSachDichVu() {
        return dichVuRepository.findAllByOrderByIdDesc().stream()
                .map(dichVu -> new LeTanDichVuDto(dichVu.getId(), dichVu.getTen(), dichVu.getImageUrl(), dichVu.getGia()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanKhuyenMaiDto> layDanhSachKhuyenMai() {
        return khuyenMaiRepository.findAllByOrderByIdDesc().stream()
                .map(item -> new LeTanKhuyenMaiDto(
                        item.getId(),
                        toMaKhuyenMai(item.getId()),
                        item.getTen(),
                        item.getLoaiGiam() != null ? item.getLoaiGiam().name() : "",
                        item.getGiaTri()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanSuDungDichVuDto> layDanhSachSuDungDichVu() {
        return suDungDichVuRepository.findAllByOrderByThoiDiemDesc().stream()
                .map(item -> {
                    Long datPhongId = item.getDatPhong() != null ? item.getDatPhong().getId() : null;
                    BigDecimal donGia = item.getDichVu() != null && item.getDichVu().getGia() != null
                            ? item.getDichVu().getGia()
                            : BigDecimal.ZERO;
                    int soLuong = item.getSoLuong() == null ? 0 : Math.max(0, item.getSoLuong());
                    BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));

                    return new LeTanSuDungDichVuDto(
                            item.getId(),
                            datPhongId,
                            toMaDatPhong(datPhongId),
                            item.getDichVu() != null ? item.getDichVu().getTen() : "",
                            item.getPhong() != null ? item.getPhong().getSoPhong() : "",
                            soLuong,
                            donGia,
                            thanhTien,
                            item.getThoiDiem()
                    );
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanChiTietDatPhongDto> layDanhSachChiTietDatPhong() {
        return chiTietDatPhongRepository.findAllByOrderByIdDesc().stream()
                .filter(item -> item.getDatPhong() != null && item.getDatPhong().getId() != null)
                .sorted(Comparator
                        .comparing((ChiTietDatPhong item) -> item.getDatPhong().getId(), Comparator.reverseOrder())
                        .thenComparing(ChiTietDatPhong::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(item -> {
                    DatPhong datPhong = item.getDatPhong();
                    return new LeTanChiTietDatPhongDto(
                            item.getId(),
                            datPhong.getId(),
                            toMaDatPhong(datPhong.getId()),
                            item.getPhong() != null ? item.getPhong().getSoPhong() : "",
                            item.getGia() == null ? BigDecimal.ZERO : item.getGia(),
                            datPhong.getNgayNhan(),
                            datPhong.getNgayTra()
                    );
                })
                .toList();
    }

    @Override
    public void thucHienCheckIn(Long datPhongId, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        DatPhong datPhong = datPhongQueriesStream.timTheoId(toObjectList(datPhongs), datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (!Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED).contains(datPhong.getTrangThai())) {
            throw new IllegalArgumentException("Đặt phòng này không ở trạng thái có thể check-in");
        }

        if (datPhong.getNgayNhan() == null || datPhong.getNgayTra() == null) {
            throw new IllegalArgumentException("Đặt phòng chưa có ngày nhận/trả hợp lệ");
        }
        if (!datPhong.getNgayTra().isAfter(datPhong.getNgayNhan())) {
            throw new IllegalArgumentException("Ngày trả phải sau ngày nhận");
        }

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng");
        }

        LocalDate homNay = LocalDate.now();
        if (homNay.isBefore(datPhong.getNgayNhan())) {
            throw new IllegalArgumentException("Chưa đến ngày nhận phòng");
        }
        if (!homNay.isBefore(datPhong.getNgayTra())) {
            throw new IllegalArgumentException("Đặt phòng đã qua ngày check-in hợp lệ");
        }

        List<Phong> phongCanCheckIn = danhSachChiTiet.stream()
                .map(ChiTietDatPhong::getPhong)
                .filter(Objects::nonNull)
                .filter(phong -> phong.getId() != null)
                .collect(Collectors.toMap(Phong::getId, phong -> phong, (left, right) -> left))
                .values()
                .stream()
                .toList();

        if (phongCanCheckIn.isEmpty()) {
            throw new IllegalArgumentException("Không có phòng hợp lệ để check-in");
        }

        for (Phong phong : phongCanCheckIn) {
            if (phong.getTrangThai() != RoomStatus.AVAILABLE) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " đang ở trạng thái " + phong.getTrangThai() + ", không thể check-in");
            }
            if (coBookingCheckedInKhacTrungLichPhong(phong.getId(), datPhongId, datPhong.getNgayNhan(), datPhong.getNgayTra())) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " đang có khách lưu trú ở booking khác");
            }
        }

        datPhong.setTrangThai(BookingStatus.CHECKED_IN);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);

        phongCanCheckIn.forEach(phong -> {
            phong.setTrangThai(RoomStatus.OCCUPIED);
            phongRepository.save(phong);
        });

        datPhongRepository.save(datPhong);

        NhanVien nhanVienThaoTac = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVienThaoTac);
    }

    @Override
    public void thucHienCheckOut(Long datPhongId, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        DatPhong datPhong = datPhongQueriesStream.timTheoId(toObjectList(datPhongs), datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ đặt phòng đã check-in mới được check-out");
        }

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng");
        }

        List<Phong> phongDangO = danhSachChiTiet.stream()
                .map(ChiTietDatPhong::getPhong)
                .filter(Objects::nonNull)
                .filter(phong -> phong.getId() != null)
                .collect(Collectors.toMap(Phong::getId, phong -> phong, (left, right) -> left))
                .values()
                .stream()
                .toList();

        for (Phong phong : phongDangO) {
            if (phong.getTrangThai() != RoomStatus.OCCUPIED) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " chưa ở trạng thái OCCUPIED, không thể check-out");
            }
        }

        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null) {
            throw new IllegalArgumentException("Chưa có hóa đơn. Vui lòng thực hiện thanh toán trước khi check-out");
        }

        NhanVien nhanVienThaoTac = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        hoaDon = capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVienThaoTac);

        BigDecimal tongHoaDon = hoaDon.getTongTien() == null ? BigDecimal.ZERO : hoaDon.getTongTien();
        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = thanhToanQueriesStream.tongThanhToanTheoHoaDon(toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc()));
        BigDecimal tongDaThanhToan = mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);

        if (tongDaThanhToan.compareTo(tongHoaDon) < 0) {
            BigDecimal conLai = tongHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);
            throw new IllegalArgumentException("Hóa đơn chưa thanh toán đủ. Còn lại: " + dinhDangTien(conLai) + " VND");
        }

        datPhong.setTrangThai(BookingStatus.CHECKED_OUT);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);

        phongDangO.forEach(phong -> {
            phong.setTrangThai(RoomStatus.CLEANING);
            phongRepository.save(phong);
        });

        datPhongRepository.save(datPhong);
    }

    @Override
    public void thucHienThanhToan(Long datPhongId,
                                  BigDecimal soTienThanhToan,
                                  PaymentMethod phuongThuc,
                                  Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }
        if (phuongThuc == null) {
            throw new IllegalArgumentException("Vui lòng chọn phương thức thanh toán");
        }
        if (soTienThanhToan == null || soTienThanhToan.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số tiền thanh toán phải lớn hơn 0");
        }

        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        DatPhong datPhong = datPhongQueriesStream.timTheoId(toObjectList(datPhongs), datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ đặt phòng đang CHECKED_IN mới được thu tiền");
        }

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng");
        }

        HoaDon hoaDon = capNhatTongTienHoaDonTheoThucTe(
            datPhong,
            danhSachChiTiet,
            timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien())
        );

        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = thanhToanQueriesStream.tongThanhToanTheoHoaDon(toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc()));
        BigDecimal tongDaThanhToan = mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);
        BigDecimal tongHoaDon = hoaDon.getTongTien() == null ? BigDecimal.ZERO : hoaDon.getTongTien();
        BigDecimal soTienConLai = tongHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);

        if (soTienConLai.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hóa đơn này đã thanh toán đủ");
        }
        if (soTienThanhToan.compareTo(soTienConLai) > 0) {
            throw new IllegalArgumentException("Số tiền vượt quá công nợ còn lại: " + dinhDangTien(soTienConLai) + " VND");
        }

        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setHoaDon(hoaDon);
        thanhToan.setSoTien(soTienThanhToan);
        thanhToan.setPhuongThuc(phuongThuc);
        thanhToan.setNgayThanhToan(LocalDateTime.now());
        thanhToanRepository.save(thanhToan);
    }

    @Override
    public void themDichVuTrongThoiGianO(Long datPhongId, Long dichVuId, Integer soLuong, Long nhanVienId) {
        if (datPhongId == null || dichVuId == null) {
            throw new IllegalArgumentException("Dữ liệu thêm dịch vụ không hợp lệ");
        }
        if (soLuong == null || soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng dịch vụ phải lớn hơn 0");
        }

        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        DatPhong datPhong = datPhongQueriesStream.timTheoId(toObjectList(datPhongs), datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ booking CHECKED_IN mới được thêm dịch vụ");
        }
        if (datPhong.getNgayNhan() == null || datPhong.getNgayTra() == null) {
            throw new IllegalArgumentException("Đặt phòng chưa có ngày lưu trú hợp lệ");
        }

        LocalDate homNay = LocalDate.now();
        if (homNay.isBefore(datPhong.getNgayNhan()) || !homNay.isBefore(datPhong.getNgayTra())) {
            throw new IllegalArgumentException("Booking không còn trong thời gian lưu trú để thêm dịch vụ");
        }

        DichVu dichVu = dichVuRepository.findById(dichVuId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ"));

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng để gán dịch vụ");
        }

        ChiTietDatPhong chiTietHieuLuc = danhSachChiTiet.stream()
            .filter(chiTiet -> chiTiet.getPhong() != null && chiTiet.getPhong().getId() != null)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Không có phòng hợp lệ để gán dịch vụ"));

        SuDungDichVu suDungDichVu = new SuDungDichVu();
        suDungDichVu.setDatPhong(datPhong);
        suDungDichVu.setPhong(chiTietHieuLuc.getPhong());
        suDungDichVu.setDichVu(dichVu);
        suDungDichVu.setSoLuong(soLuong);
        suDungDichVu.setThoiDiem(LocalDateTime.now());
        suDungDichVuRepository.save(suDungDichVu);

        NhanVien nhanVien = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
    }

    @Override
    public void xoaDichVuDaThem(Long suDungDichVuId, Long nhanVienId) {
        if (suDungDichVuId == null) {
            throw new IllegalArgumentException("Dịch vụ cần xóa không hợp lệ");
        }

        SuDungDichVu suDungDichVu = suDungDichVuRepository.findById(suDungDichVuId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ đã thêm"));

        DatPhong datPhong = suDungDichVu.getDatPhong();
        if (datPhong == null || datPhong.getId() == null) {
            throw new IllegalArgumentException("Dịch vụ không gắn với đặt phòng hợp lệ");
        }
        if (datPhong.getTrangThai() == BookingStatus.CHECKED_OUT || datPhong.getTrangThai() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Đặt phòng đã kết thúc, không thể xóa dịch vụ");
        }

        suDungDichVuRepository.delete(suDungDichVu);

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(
            toObjectList(chiTietDatPhongRepository.findAllByOrderByIdDesc()),
                datPhong.getId()
        );
        NhanVien nhanVien = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
    }

    @Override
    public void huyDatPhong(Long datPhongId, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        DatPhong datPhong = datPhongQueriesStream.timTheoId(
                        toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()),
                        datPhongId
                )
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (!Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED).contains(datPhong.getTrangThai())) {
            throw new IllegalArgumentException("Chỉ được hủy đặt phòng ở trạng thái PENDING/CONFIRMED");
        }

        datPhong.setTrangThai(BookingStatus.CANCELLED);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);
        datPhongRepository.save(datPhong);

        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon != null) {
            BigDecimal tongDaThanhToan = thanhToanQueriesStream.tongThanhToanTheoHoaDon(
                    toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc())
            ).getOrDefault(hoaDon.getId(), BigDecimal.ZERO);

            if (tongDaThanhToan.compareTo(BigDecimal.ZERO) <= 0) {
                hoaDon.setKhuyenMais(new HashSet<>());
                hoaDonRepository.delete(hoaDon);
            } else {
                hoaDon.setTongTien(tongDaThanhToan);
                hoaDon.setKhuyenMais(new HashSet<>());
                hoaDonRepository.save(hoaDon);
            }
        }
    }

    @Override
    public void apDungMaKhuyenMai(Long datPhongId, String maKhuyenMai, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        DatPhong datPhong = datPhongQueriesStream.timTheoId(toObjectList(datPhongs), datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ booking CHECKED_IN mới được áp dụng khuyến mãi");
        }

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(
            toObjectList(chiTietDatPhongRepository.findAllByOrderByIdDesc()),
                datPhongId
        );
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không có chi tiết đặt phòng để áp dụng khuyến mãi");
        }

        NhanVien nhanVien = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        HoaDon hoaDon = capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);

        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            hoaDon.setKhuyenMais(new HashSet<>());
            hoaDonRepository.save(hoaDon);
            capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
            return;
        }

        KhuyenMai khuyenMai = timKhuyenMaiTheoMa(maKhuyenMai)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã khuyến mãi hợp lệ"));

        Set<KhuyenMai> khuyenMaiApDung = new HashSet<>();
        khuyenMaiApDung.add(khuyenMai);
        hoaDon.setKhuyenMais(khuyenMaiApDung);
        hoaDonRepository.save(hoaDon);

        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
    }

    @Override
    public void apDungKhuyenMaiVaThanhToan(Long datPhongId,
                                           String maKhuyenMai,
                                           BigDecimal soTienThanhToan,
                                           PaymentMethod phuongThuc,
                                           Long nhanVienId) {
        apDungMaKhuyenMai(datPhongId, maKhuyenMai, nhanVienId);

        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        DatPhong datPhong = datPhongQueriesStream.timTheoId(toObjectList(datPhongs), datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));
        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null || hoaDon.getId() == null) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn để thanh toán");
        }

        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = thanhToanQueriesStream.tongThanhToanTheoHoaDon(
                toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc())
        );
        BigDecimal tongDaThanhToan = mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);
        BigDecimal tongHoaDon = hoaDon.getTongTien() == null ? BigDecimal.ZERO : hoaDon.getTongTien();
        BigDecimal soTienConLai = tongHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);

        if (soTienConLai.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hóa đơn này đã thanh toán đủ");
        }

        BigDecimal soTienThanhToanThucTe = soTienThanhToan;
        if (soTienThanhToanThucTe != null && soTienThanhToanThucTe.compareTo(soTienConLai) > 0) {
            soTienThanhToanThucTe = soTienConLai;
        }

        thucHienThanhToan(datPhongId, soTienThanhToanThucTe, phuongThuc, nhanVienId);
    }

    @Override
    public void capNhatTrangThaiPhong(Long phongId, RoomStatus trangThai) {
        if (phongId == null || trangThai == null) {
            throw new IllegalArgumentException("Du lieu cap nhat phong khong hop le");
        }

        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        Phong phong = phongQueriesStream.timTheoId(toObjectList(phongs), phongId)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay phong"));

        boolean coKhachDangO = coBookingCheckedInKhacTrungLichPhong(phongId, null, LocalDate.now(), LocalDate.now().plusDays(1));
        if (coKhachDangO && trangThai != RoomStatus.OCCUPIED) {
            throw new IllegalArgumentException("Phòng đang có khách lưu trú, không thể chuyển sang trạng thái " + trangThai);
        }
        if (!coKhachDangO && trangThai == RoomStatus.OCCUPIED) {
            throw new IllegalArgumentException("Không có booking CHECKED_IN hiệu lực cho phòng này");
        }

        phong.setTrangThai(trangThai);
        phongRepository.save(phong);
    }

    @Override
    public void taoDatPhong(LeTanTaoDatPhongFormDto form, Long nhanVienId) {
        kiemTraFormDatPhong(form);

        NhanVien nhanVien = timNhanVienTheoId(nhanVienId)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay nhan vien dang thao tac"));

        List<Long> phongIdsHopLe = form.getPhongIds().stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        if (phongIdsHopLe.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất một phòng");
        }

        Map<Long, Phong> mapPhong = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc())).stream()
                .collect(Collectors.toMap(Phong::getId, phong -> phong, (left, right) -> left));

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();

        for (Long phongId : phongIdsHopLe) {
            Phong phong = mapPhong.get(phongId);
            if (phong == null) {
                throw new IllegalArgumentException("Không tìm thấy phòng được chọn");
            }
            if (phong.getTrangThai() != RoomStatus.AVAILABLE) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " đang ở trạng thái " + phong.getTrangThai() + ", không thể đặt");
            }

            boolean coLichTrung = chiTietDatPhongQueriesStream.coLichTrung(
                    toObjectList(chiTietDatPhongs),
                    phong.getId(),
                    form.getNgayNhan(),
                    form.getNgayTra(),
                    Set.of(BookingStatus.CANCELLED)
            );
            if (coLichTrung) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " bị trùng lịch trong khoảng ngày đã chọn");
            }
        }

        KhachHang khachHang = timHoacTaoKhachHang(form.getTenKhachHang(), form.getSdt(), form.getEmail());

        DatPhong datPhong = new DatPhong();
        datPhong.setKhachHang(khachHang);
        datPhong.setNhanVien(nhanVien);
        datPhong.setNgayDat(LocalDateTime.now());
        datPhong.setNgayNhan(form.getNgayNhan());
        datPhong.setNgayTra(form.getNgayTra());
        datPhong.setTrangThai(BookingStatus.CONFIRMED);
        datPhongRepository.save(datPhong);

        for (Long phongId : phongIdsHopLe) {
            Phong phong = mapPhong.get(phongId);
            ChiTietDatPhong chiTietDatPhong = new ChiTietDatPhong();
            chiTietDatPhong.setDatPhong(datPhong);
            chiTietDatPhong.setPhong(phong);
            chiTietDatPhong.setGia(layGiaPhongCoBan(phong));
            chiTietDatPhongRepository.save(chiTietDatPhong);
        }
    }

    private List<LeTanCheckInDto> duLieuCheckIn() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<DatPhong> datPhongLoc = datPhongQueriesStream.locTheoTrangThai(
                toObjectList(datPhongs),
                Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));

        return datPhongLoc.stream()
            .map(datPhong -> toLeTanCheckInDto(datPhong, chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId())))
                .filter(dto -> dto.getSoPhong() != null && !dto.getSoPhong().isBlank())
                .sorted(Comparator
                        .comparing(LeTanCheckInDto::getNgayNhan, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(LeTanCheckInDto::getMaDatPhong, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    private List<LeTanCheckOutDto> duLieuCheckOut() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<DatPhong> datPhongLoc = datPhongQueriesStream.locTheoTrangThai(
                toObjectList(datPhongs),
                Set.of(BookingStatus.CHECKED_IN, BookingStatus.CHECKED_OUT)
        );

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));
        Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong = suDungDichVuQueriesStream.tongTienTheoDatPhong(toObjectList(suDungDichVuRepository.findAllByOrderByThoiDiemDesc()));

        Map<Long, HoaDon> mapHoaDonTheoDatPhong = hoaDonQueriesStream.mapTheoDatPhongId(toObjectList(hoaDonRepository.findAllByOrderByNgayTaoDesc()));
        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = thanhToanQueriesStream.tongThanhToanTheoHoaDon(toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc()));

        return datPhongLoc.stream()
            .map(datPhong -> toLeTanCheckOutDto(
                datPhong,
                chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId()),
                mapTongTienDichVuTheoDatPhong,
                mapHoaDonTheoDatPhong,
                mapTongThanhToanTheoHoaDon
            ))
                .filter(dto -> dto.getSoPhong() != null && !dto.getSoPhong().isBlank())
                .sorted(Comparator
                        .comparing(LeTanCheckOutDto::getNgayTra, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(LeTanCheckOutDto::getMaDatPhong, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    private List<LeTanDatPhongDto> duLieuDatPhong() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));

        return datPhongs.stream()
            .map(datPhong -> toLeTanDatPhongDto(datPhong, chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId())))
                .toList();
    }

    private List<LeTanDangOThemDichVuDto> duLieuDangOThemDichVu() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));

        return datPhongs.stream()
                .filter(datPhong -> datPhong.getTrangThai() == BookingStatus.CHECKED_IN)
                .map(datPhong -> {
                    List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId());

                    return new LeTanDangOThemDichVuDto(
                            datPhong.getId(),
                            toMaDatPhong(datPhong.getId()),
                            datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                            hopNhatDanhSachPhong(danhSachChiTiet),
                    datPhong.getNgayNhan(),
                    datPhong.getNgayTra()
                    );
                })
                .toList();
    }

    private List<LeTanHoaDonThanhToanDto> duLieuHoaDonThanhToan() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByIdDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));
        Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong = suDungDichVuQueriesStream.tongTienTheoDatPhong(toObjectList(suDungDichVuRepository.findAllByOrderByThoiDiemDesc()));

        Map<Long, HoaDon> mapHoaDonTheoDatPhong = hoaDonQueriesStream.mapTheoDatPhongId(toObjectList(hoaDonRepository.findAllByOrderByNgayTaoDesc()));
        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = thanhToanQueriesStream.tongThanhToanTheoHoaDon(toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc()));

        return datPhongs.stream()
            .filter(datPhong -> Set.of(BookingStatus.CHECKED_IN, BookingStatus.CHECKED_OUT).contains(datPhong.getTrangThai()))
            .map(datPhong -> {
                List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId());
                HoaDon hoaDon = mapHoaDonTheoDatPhong.get(datPhong.getId());
                BigDecimal tongTienTruocKhuyenMai = tinhTongTienDatPhong(danhSachChiTiet)
                    .add(mapTongTienDichVuTheoDatPhong.getOrDefault(datPhong.getId(), BigDecimal.ZERO));
                BigDecimal tongTienHoaDonTinhToan = apDungKhuyenMai(
                    tongTienTruocKhuyenMai,
                    hoaDon != null ? hoaDon.getKhuyenMais() : Set.of()
                );
                BigDecimal tongTienHoaDon = hoaDon != null && hoaDon.getTongTien() != null
                        ? hoaDon.getTongTien()
                        : tongTienHoaDonTinhToan;
                BigDecimal tongDaThanhToan = hoaDon == null || hoaDon.getId() == null
                        ? BigDecimal.ZERO
                        : mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);
                BigDecimal soTienConLai = tongTienHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);
                ThongTinThanhToan thongTinThanhToan = xacDinhTrangThaiThanhToan(hoaDon, tongDaThanhToan);
                String maKhuyenMai = hoaDon == null
                        ? "--"
                        : hoaDon.getKhuyenMais().stream()
                                .map(km -> toMaKhuyenMai(km.getId()))
                                .sorted(String::compareToIgnoreCase)
                                .collect(Collectors.joining(", "));
                if (maKhuyenMai.isBlank()) {
                    maKhuyenMai = "--";
                }

                return new LeTanHoaDonThanhToanDto(
                    datPhong.getId(),
                    hoaDon != null ? hoaDon.getId() : null,
                    toMaHoaDon(hoaDon != null ? hoaDon.getId() : null),
                    toMaDatPhong(datPhong.getId()),
                    datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                    hopNhatDanhSachPhong(danhSachChiTiet),
                    maKhuyenMai,
                    hoaDon != null ? hoaDon.getNgayTao() : null,
                    tongTienTruocKhuyenMai,
                    tongTienHoaDon,
                    tongDaThanhToan,
                    soTienConLai,
                    thongTinThanhToan.nhan(),
                    thongTinThanhToan.sacThai(),
                    datPhong.getTrangThai()
                );
            })
            .sorted(Comparator
                .comparing(LeTanHoaDonThanhToanDto::getNgayTaoHoaDon, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(LeTanHoaDonThanhToanDto::getMaDatPhong, Comparator.nullsLast(String::compareToIgnoreCase)))
            .toList();
    }

    private List<LeTanPhongDto> duLieuPhong() {
        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        return phongs.stream().map(this::toLeTanPhongDto).toList();
    }

    private LeTanCheckInDto toLeTanCheckInDto(DatPhong datPhong, List<ChiTietDatPhong> danhSachChiTiet) {

        return new LeTanCheckInDto(
                datPhong.getId(),
                toMaDatPhong(datPhong.getId()),
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getSdt() : "",
                hopNhatDanhSachPhong(danhSachChiTiet),
                datPhong.getNgayNhan(),
                datPhong.getNgayTra(),
                datPhong.getTrangThai()
        );
    }

    private LeTanCheckOutDto toLeTanCheckOutDto(DatPhong datPhong,
                                                List<ChiTietDatPhong> danhSachChiTiet,
                                                Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong,
                                                Map<Long, HoaDon> mapHoaDonTheoDatPhong,
                                                Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon) {
        BigDecimal tongTienTruocKhuyenMai = tinhTongTienDatPhong(danhSachChiTiet)
            .add(mapTongTienDichVuTheoDatPhong.getOrDefault(datPhong.getId(), BigDecimal.ZERO));

        HoaDon hoaDon = mapHoaDonTheoDatPhong.get(datPhong.getId());
        BigDecimal tongTienSauKhuyenMaiTinhToan = apDungKhuyenMai(
            tongTienTruocKhuyenMai,
            hoaDon != null ? hoaDon.getKhuyenMais() : Set.of()
        );
        BigDecimal tongDaThanhToan = hoaDon == null || hoaDon.getId() == null
                ? BigDecimal.ZERO
                : mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);
        BigDecimal tongHoaDon = (hoaDon != null && hoaDon.getTongTien() != null)
            ? hoaDon.getTongTien()
            : tongTienSauKhuyenMaiTinhToan;
        if (tongHoaDon.compareTo(BigDecimal.ZERO) < 0) {
            tongHoaDon = BigDecimal.ZERO;
        }
        BigDecimal soTienConLai = tongHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);
        boolean daThanhToanDu = soTienConLai.compareTo(BigDecimal.ZERO) <= 0;
        ThongTinThanhToan thongTinThanhToan = xacDinhTrangThaiThanhToan(hoaDon, tongDaThanhToan);

        return new LeTanCheckOutDto(
                datPhong.getId(),
                toMaDatPhong(datPhong.getId()),
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                hopNhatDanhSachPhong(danhSachChiTiet),
                datPhong.getNgayTra(),
                tongHoaDon,
                tongDaThanhToan,
                soTienConLai,
                thongTinThanhToan.nhan(),
                thongTinThanhToan.sacThai(),
                daThanhToanDu,
                datPhong.getTrangThai()
        );
    }

    private LeTanDatPhongDto toLeTanDatPhongDto(DatPhong datPhong, List<ChiTietDatPhong> danhSachChiTiet) {
        return new LeTanDatPhongDto(
                datPhong.getId(),
                toMaDatPhong(datPhong.getId()),
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getSdt() : "",
                hopNhatDanhSachPhong(danhSachChiTiet),
                datPhong.getNgayDat(),
            datPhong.getNgayNhan(),
            datPhong.getNgayTra(),
                datPhong.getTrangThai()
        );
    }

    private LeTanPhongDto toLeTanPhongDto(Phong phong) {
        return new LeTanPhongDto(
                phong.getId(),
                phong.getSoPhong(),
                phong.getLoaiPhong() != null ? phong.getLoaiPhong().getTenLoai() : "",
                layGiaPhongCoBan(phong),
                phong.getTrangThai(),
                phong.getImageUrl()
        );
    }

    private KhachHang timHoacTaoKhachHang(String tenKhachHang, String sdt, String email) {
        List<KhachHang> danhSachKhach = khachHangRepository.findAllByOrderByIdDesc();
        Optional<KhachHang> khachHangDaCo = khachHangQueriesStream.timTheoSdtHoacEmail(toObjectList(danhSachKhach), sdt, email);

        KhachHang khachHang = khachHangDaCo.orElseGet(KhachHang::new);
        khachHang.setTen(tenKhachHang.trim());
        khachHang.setSdt(sdt.trim());
        khachHang.setEmail((email == null || email.isBlank()) ? null : email.trim());
        return khachHangRepository.save(khachHang);
    }

    private Optional<NhanVien> timNhanVienTheoId(Long nhanVienId) {
        if (nhanVienId == null) {
            return Optional.empty();
        }
        List<NhanVien> nhanViens = nhanVienRepository.findAllWithTaiKhoan();
        return nhanVienQueriesStream.timTheoId(toObjectList(nhanViens), nhanVienId);
    }

    private boolean coBookingCheckedInKhacTrungLichPhong(Long phongId,
                                                         Long datPhongIdBoQua,
                                                         LocalDate ngayNhan,
                                                         LocalDate ngayTra) {
        if (phongId == null || ngayNhan == null || ngayTra == null) {
            return false;
        }

        return chiTietDatPhongRepository.findAllByOrderByIdDesc().stream()
                .filter(chiTiet -> chiTiet.getPhong() != null
                        && chiTiet.getPhong().getId() != null
                        && chiTiet.getPhong().getId().equals(phongId))
                .map(ChiTietDatPhong::getDatPhong)
                .filter(Objects::nonNull)
                .filter(datPhong -> datPhong.getTrangThai() == BookingStatus.CHECKED_IN)
                .filter(datPhong -> datPhongIdBoQua == null || !Objects.equals(datPhong.getId(), datPhongIdBoQua))
                .anyMatch(datPhong -> giaoKhoangNgay(datPhong.getNgayNhan(), datPhong.getNgayTra(), ngayNhan, ngayTra));
    }

    private boolean giaoKhoangNgay(LocalDate ngayNhanA,
                                   LocalDate ngayTraA,
                                   LocalDate ngayNhanB,
                                   LocalDate ngayTraB) {
        if (ngayNhanA == null || ngayTraA == null || ngayNhanB == null || ngayTraB == null) {
            return false;
        }
        return ngayNhanA.isBefore(ngayTraB) && ngayNhanB.isBefore(ngayTraA);
    }

    private BigDecimal tinhTongTienDichVu(Long datPhongId) {
        if (datPhongId == null) {
            return BigDecimal.ZERO;
        }

        List<SuDungDichVu> danhSachSuDung = suDungDichVuQueriesStream.locTheoDatPhongId(
                toObjectList(suDungDichVuRepository.findAllByOrderByThoiDiemDesc()),
                datPhongId
        );

        return danhSachSuDung.stream()
                .map(item -> {
                    BigDecimal gia = item.getDichVu() == null || item.getDichVu().getGia() == null
                            ? BigDecimal.ZERO
                            : item.getDichVu().getGia();
                    int soLuong = item.getSoLuong() == null ? 0 : Math.max(0, item.getSoLuong());
                    return gia.multiply(BigDecimal.valueOf(soLuong));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private HoaDon capNhatTongTienHoaDonTheoThucTe(DatPhong datPhong,
                                                   List<ChiTietDatPhong> danhSachChiTiet,
                                                   NhanVien nhanVienThaoTac) {
        BigDecimal tongTienTruocKhuyenMai = tinhTongTienDatPhong(danhSachChiTiet)
                .add(tinhTongTienDichVu(datPhong.getId()));

        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null) {
            hoaDon = new HoaDon();
            hoaDon.setDatPhong(datPhong);
            hoaDon.setNhanVien(nhanVienThaoTac != null ? nhanVienThaoTac : datPhong.getNhanVien());
            hoaDon.setTongTien(tongTienTruocKhuyenMai);
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon = hoaDonRepository.save(hoaDon);
            datPhong.setHoaDon(hoaDon);
            datPhongRepository.save(datPhong);
            return hoaDon;
        }

        BigDecimal tongTienSauKhuyenMai = apDungKhuyenMai(tongTienTruocKhuyenMai, hoaDon.getKhuyenMais());

        if (hoaDon.getTongTien() == null || hoaDon.getTongTien().compareTo(tongTienSauKhuyenMai) != 0) {
            hoaDon.setTongTien(tongTienSauKhuyenMai);
            if (nhanVienThaoTac != null) {
                hoaDon.setNhanVien(nhanVienThaoTac);
            }
            hoaDon = hoaDonRepository.save(hoaDon);
        }

        return hoaDon;
    }

    private void kiemTraFormDatPhong(LeTanTaoDatPhongFormDto form) {
        if (form == null) {
            throw new IllegalArgumentException("Du lieu dat phong khong hop le");
        }
        if (laRong(form.getTenKhachHang()) || laRong(form.getSdt())) {
            throw new IllegalArgumentException("Ten khach hang va so dien thoai la bat buoc");
        }
        if (form.getPhongIds() == null || form.getPhongIds().isEmpty() || form.getNgayNhan() == null || form.getNgayTra() == null) {
            throw new IllegalArgumentException("Vui long chon phong, ngay nhan va ngay tra");
        }
        if (!form.getNgayTra().isAfter(form.getNgayNhan())) {
            throw new IllegalArgumentException("Ngay tra phai sau ngay nhan");
        }
    }

    private <T> TrangDuLieuDto<T> phanTrang(List<T> duLieu, int trang, int kichThuoc) {
        int kichThuocAnToan = Math.max(3, Math.min(kichThuoc, 20));
        int tongBanGhi = duLieu == null ? 0 : duLieu.size();
        int tongTrang = Math.max(1, (int) Math.ceil((double) tongBanGhi / kichThuocAnToan));
        int trangHienTai = Math.max(1, Math.min(trang, tongTrang));

        long boQua = (long) (trangHienTai - 1) * kichThuocAnToan;
        List<T> danhSachTrang = tongBanGhi == 0
                ? List.of()
                : duLieu.stream().skip(boQua).limit(kichThuocAnToan).toList();

        return new TrangDuLieuDto<>(
                danhSachTrang,
                trangHienTai,
                tongTrang,
                tongBanGhi,
                kichThuocAnToan,
                trangHienTai > 1,
                trangHienTai < tongTrang
        );
    }

    private BigDecimal tinhTienPhong(ChiTietDatPhong chiTietDatPhong) {
        if (chiTietDatPhong == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal gia = chiTietDatPhong.getGia() == null ? BigDecimal.ZERO : chiTietDatPhong.getGia();
        DatPhong datPhong = chiTietDatPhong.getDatPhong();
        LocalDate ngayNhan = datPhong != null ? datPhong.getNgayNhan() : null;
        LocalDate ngayTra = datPhong != null ? datPhong.getNgayTra() : null;

        long soDem = 1;
        if (ngayNhan != null && ngayTra != null) {
            long tinhToan = ChronoUnit.DAYS.between(ngayNhan, ngayTra);
            soDem = Math.max(1, tinhToan);
        }
        return gia.multiply(BigDecimal.valueOf(soDem));
    }

    private BigDecimal tinhTongTienDatPhong(List<ChiTietDatPhong> danhSachChiTiet) {
        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return danhSachChiTiet.stream()
                .map(this::tinhTienPhong)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal layGiaPhongCoBan(Phong phong) {
        if (phong == null || phong.getLoaiPhong() == null || phong.getLoaiPhong().getGiaCoBan() == null) {
            return BigDecimal.ZERO;
        }
        return phong.getLoaiPhong().getGiaCoBan();
    }

        private List<LeTanCheckOutDto> apDungSapXepCheckOut(List<LeTanCheckOutDto> duLieu, String sapXep) {
        Comparator<LeTanCheckOutDto> comparator = Comparator
            .comparing(LeTanCheckOutDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));

        String sapXepAnToan = sapXep == null ? "moi-nhat" : sapXep.trim().toLowerCase(Locale.ROOT);
        switch (sapXepAnToan) {
            case "cu-nhat" -> comparator = Comparator
                .comparing(LeTanCheckOutDto::getDatPhongId, Comparator.nullsLast(Comparator.naturalOrder()));
            case "ngay-tra-tang" -> comparator = Comparator
                .comparing(LeTanCheckOutDto::getNgayTra, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(LeTanCheckOutDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            case "ngay-tra-giam" -> comparator = Comparator
                .comparing(LeTanCheckOutDto::getNgayTra, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(LeTanCheckOutDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            case "con-lai-giam" -> comparator = Comparator
                .comparing(LeTanCheckOutDto::getSoTienConLai, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(LeTanCheckOutDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            case "con-lai-tang" -> comparator = Comparator
                .comparing(LeTanCheckOutDto::getSoTienConLai, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(LeTanCheckOutDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            default -> {
            }
        }

        return duLieu.stream().sorted(comparator).toList();
        }

        private List<LeTanDatPhongDto> apDungSapXepDatPhong(List<LeTanDatPhongDto> duLieu, String sapXep) {
        Comparator<LeTanDatPhongDto> comparator = Comparator
            .comparing(LeTanDatPhongDto::getNgayDat, Comparator.nullsLast(Comparator.reverseOrder()))
            .thenComparing(LeTanDatPhongDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));

        String sapXepAnToan = sapXep == null ? "moi-nhat" : sapXep.trim().toLowerCase(Locale.ROOT);
        switch (sapXepAnToan) {
            case "cu-nhat" -> comparator = Comparator
                .comparing(LeTanDatPhongDto::getNgayDat, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(LeTanDatPhongDto::getDatPhongId, Comparator.nullsLast(Comparator.naturalOrder()));
            case "ngay-nhan-tang" -> comparator = Comparator
                .comparing(LeTanDatPhongDto::getNgayNhan, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(LeTanDatPhongDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            case "ngay-nhan-giam" -> comparator = Comparator
                .comparing(LeTanDatPhongDto::getNgayNhan, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(LeTanDatPhongDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            default -> {
            }
        }

        return duLieu.stream().sorted(comparator).toList();
        }

        private List<LeTanHoaDonThanhToanDto> apDungSapXepHoaDon(List<LeTanHoaDonThanhToanDto> duLieu, String sapXep) {
        Comparator<LeTanHoaDonThanhToanDto> comparator = Comparator
            .comparing(LeTanHoaDonThanhToanDto::getNgayTaoHoaDon, Comparator.nullsLast(Comparator.reverseOrder()))
            .thenComparing(LeTanHoaDonThanhToanDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));

        String sapXepAnToan = sapXep == null ? "moi-nhat" : sapXep.trim().toLowerCase(Locale.ROOT);
        switch (sapXepAnToan) {
            case "cu-nhat" -> comparator = Comparator
                .comparing(LeTanHoaDonThanhToanDto::getNgayTaoHoaDon, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(LeTanHoaDonThanhToanDto::getDatPhongId, Comparator.nullsLast(Comparator.naturalOrder()));
            case "con-lai-giam" -> comparator = Comparator
                .comparing(LeTanHoaDonThanhToanDto::getSoTienConLai, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(LeTanHoaDonThanhToanDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            case "con-lai-tang" -> comparator = Comparator
                .comparing(LeTanHoaDonThanhToanDto::getSoTienConLai, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(LeTanHoaDonThanhToanDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            default -> {
            }
        }

        return duLieu.stream().sorted(comparator).toList();
        }

        private List<LeTanDangOThemDichVuDto> apDungSapXepDangO(List<LeTanDangOThemDichVuDto> duLieu, String sapXep) {
        Comparator<LeTanDangOThemDichVuDto> comparator = Comparator
            .comparing(LeTanDangOThemDichVuDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));

        String sapXepAnToan = sapXep == null ? "moi-nhat" : sapXep.trim().toLowerCase(Locale.ROOT);
        switch (sapXepAnToan) {
            case "cu-nhat" -> comparator = Comparator
                .comparing(LeTanDangOThemDichVuDto::getDatPhongId, Comparator.nullsLast(Comparator.naturalOrder()));
            case "khach-a-z" -> comparator = Comparator
                .comparing(LeTanDangOThemDichVuDto::getTenKhachHang, Comparator.nullsLast(String::compareToIgnoreCase))
                .thenComparing(LeTanDangOThemDichVuDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            case "khach-z-a" -> comparator = Comparator
                .comparing(LeTanDangOThemDichVuDto::getTenKhachHang, Comparator.nullsLast(String::compareToIgnoreCase)).reversed()
                .thenComparing(LeTanDangOThemDichVuDto::getDatPhongId, Comparator.nullsLast(Comparator.reverseOrder()));
            default -> {
            }
        }

        return duLieu.stream().sorted(comparator).toList();
        }

        private boolean khopBoLocTrangThaiThanhToan(String trangThaiThanhToan, String boLoc) {
        if (trangThaiThanhToan == null || boLoc == null || boLoc.isBlank()) {
            return true;
        }

        String trangThai = trangThaiThanhToan.toLowerCase(Locale.ROOT);
        return switch (boLoc) {
            case "chua-thanh-toan" -> trangThai.contains("chưa thanh toán");
            case "thanh-toan-mot-phan" -> trangThai.contains("một phần");
            case "da-thanh-toan" -> trangThai.contains("đã thanh toán") || trangThai.contains("đã đối soát");
            default -> trangThai.contains(boLoc);
        };
        }

    private ThongTinThanhToan xacDinhTrangThaiThanhToan(HoaDon hoaDon, BigDecimal tongDaThanhToan) {
        if (hoaDon == null) {
            return new ThongTinThanhToan("Chưa tạo hóa đơn", "slate");
        }

        BigDecimal tongTien = hoaDon.getTongTien() == null ? BigDecimal.ZERO : hoaDon.getTongTien();
        BigDecimal daThanhToan = tongDaThanhToan == null ? BigDecimal.ZERO : tongDaThanhToan;

        if (tongTien.compareTo(BigDecimal.ZERO) == 0) {
            return new ThongTinThanhToan("Đã đối soát", "emerald");
        }
        if (daThanhToan.compareTo(tongTien) >= 0) {
            return new ThongTinThanhToan("Đã thanh toán", "emerald");
        }
        if (daThanhToan.compareTo(BigDecimal.ZERO) > 0) {
            return new ThongTinThanhToan("Thanh toán một phần", "sky");
        }
        return new ThongTinThanhToan("Chưa thanh toán", "amber");
    }

    private String toMaDatPhong(Long datPhongId) {
        long giaTri = datPhongId == null ? 0L : datPhongId;
        return String.format("DP-%05d", giaTri);
    }

    private String toMaHoaDon(Long hoaDonId) {
        if (hoaDonId == null) {
            return "Chưa tạo";
        }
        return String.format("HD-%05d", hoaDonId);
    }

    private String toMaKhuyenMai(Long khuyenMaiId) {
        long giaTri = khuyenMaiId == null ? 0L : khuyenMaiId;
        return String.format("KM-%05d", giaTri);
    }

    private Optional<KhuyenMai> timKhuyenMaiTheoMa(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return Optional.empty();
        }

        String ma = maKhuyenMai.trim().toUpperCase(Locale.ROOT);
        List<KhuyenMai> danhSach = khuyenMaiRepository.findAllByOrderByIdDesc();
        if (ma.startsWith("KM-")) {
            try {
                long id = Long.parseLong(ma.substring(3));
                Optional<KhuyenMai> theoId = danhSach.stream()
                        .filter(item -> item.getId() != null && item.getId() == id)
                        .findFirst();
                if (theoId.isPresent()) {
                    return theoId;
                }
            } catch (NumberFormatException ignored) {
                // Fall through to name-based matching.
            }
        }

        return danhSach.stream()
                .filter(item -> item.getTen() != null && item.getTen().trim().equalsIgnoreCase(maKhuyenMai.trim()))
                .findFirst();
    }

    private BigDecimal apDungKhuyenMai(BigDecimal tongTienTruocKhuyenMai, Set<KhuyenMai> danhSachKhuyenMai) {
        BigDecimal tongSauGiam = tongTienTruocKhuyenMai == null
                ? BigDecimal.ZERO
                : tongTienTruocKhuyenMai.max(BigDecimal.ZERO).setScale(0, RoundingMode.HALF_UP);
        if (danhSachKhuyenMai == null || danhSachKhuyenMai.isEmpty()) {
            return tongSauGiam;
        }

        for (KhuyenMai khuyenMai : danhSachKhuyenMai) {
            if (khuyenMai == null || khuyenMai.getLoaiGiam() == null || khuyenMai.getGiaTri() == null) {
                continue;
            }

            BigDecimal mucGiam;
            if (khuyenMai.getLoaiGiam() == DiscountType.PERCENT) {
                BigDecimal tyLe = khuyenMai.getGiaTri();
                if (tyLe.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                if (tyLe.compareTo(BigDecimal.valueOf(100)) > 0) {
                    tyLe = BigDecimal.valueOf(100);
                }
                mucGiam = tongSauGiam.multiply(tyLe)
                        .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
            } else {
                mucGiam = khuyenMai.getGiaTri().max(BigDecimal.ZERO).setScale(0, RoundingMode.HALF_UP);
            }

            tongSauGiam = tongSauGiam.subtract(mucGiam);
            if (tongSauGiam.compareTo(BigDecimal.ZERO) < 0) {
                tongSauGiam = BigDecimal.ZERO;
            }
        }

        return tongSauGiam.setScale(0, RoundingMode.HALF_UP);
    }

    private String hopNhatDanhSachPhong(List<ChiTietDatPhong> danhSachChiTiet) {
        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
            return "";
        }

        return danhSachChiTiet.stream()
                .map(ChiTietDatPhong::getPhong)
                .filter(phong -> phong != null && phong.getSoPhong() != null && !phong.getSoPhong().isBlank())
                .map(Phong::getSoPhong)
                .distinct()
                .collect(Collectors.joining(", "));
    }

    private String dinhDangTien(BigDecimal soTien) {
        BigDecimal giaTri = soTien == null ? BigDecimal.ZERO : soTien;
        return giaTri.setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    private boolean laRong(String value) {
        return value == null || value.isBlank();
    }

    private List<Object> toObjectList(List<?> danhSach) {
        if (danhSach == null || danhSach.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(danhSach);
    }

    private static class ThongTinThanhToan {
        private final String nhan;
        private final String sacThai;

        private ThongTinThanhToan(String nhan, String sacThai) {
            this.nhan = nhan;
            this.sacThai = sacThai;
        }

        public String nhan() {
            return nhan;
        }

        public String sacThai() {
            return sacThai;
        }
    }
}
