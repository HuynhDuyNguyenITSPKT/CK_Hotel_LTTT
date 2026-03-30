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
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IDashboardQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardQueryServiceImpl implements IDashboardQueryService {

    private final DatPhongRepository datPhongRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;
    private final PhongRepository phongRepository;
    private final DichVuRepository dichVuRepository;
    private final SuDungDichVuRepository suDungDichVuRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;

    public DashboardQueryServiceImpl(DatPhongRepository datPhongRepository,
                                     ChiTietDatPhongRepository chiTietDatPhongRepository,
                                     PhongRepository phongRepository,
                                     DichVuRepository dichVuRepository,
                                     SuDungDichVuRepository suDungDichVuRepository,
                                     HoaDonRepository hoaDonRepository,
                                     ThanhToanRepository thanhToanRepository,
                                     KhuyenMaiRepository khuyenMaiRepository) {
        this.datPhongRepository = datPhongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.phongRepository = phongRepository;
        this.dichVuRepository = dichVuRepository;
        this.suDungDichVuRepository = suDungDichVuRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
    }

    @Override
    public List<LeTanThongKeNhanhDto> layThongKeNhanh() {
        LocalDate homNay = LocalDate.now();
        long tongCheckInHomNay = datPhongRepository.countByNgayNhanAndTrangThaiNot(homNay, BookingStatus.CANCELLED);
        long tongCheckOutHomNay = datPhongRepository.countByNgayTraAndTrangThaiNot(homNay, BookingStatus.CANCELLED);
        long tongPhongSanSang = phongRepository.countByTrangThai(RoomStatus.AVAILABLE);
        long tongYeuCauDangXuLy = datPhongRepository.countByTrangThaiIn(Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED));
        BigDecimal tongDoanhThu = hoaDonRepository.sumTongTien();

        return List.of(
                new LeTanThongKeNhanhDto("Check-in hom nay", String.valueOf(tongCheckInHomNay), "Luot check-in den theo lich", "fa-solid fa-door-open", "from-cyan-500 to-blue-600"),
                new LeTanThongKeNhanhDto("Check-out hom nay", String.valueOf(tongCheckOutHomNay), "Luot can hoan tat tra phong", "fa-solid fa-door-closed", "from-emerald-500 to-teal-600"),
                new LeTanThongKeNhanhDto("Phong san sang", String.valueOf(tongPhongSanSang), "Phong trang thai AVAILABLE", "fa-solid fa-bed", "from-indigo-500 to-violet-600"),
                new LeTanThongKeNhanhDto("Yeu cau dang xu ly", String.valueOf(tongYeuCauDangXuLy), "Booking PENDING/CONFIRMED", "fa-solid fa-list-check", "from-amber-500 to-orange-600"),
                new LeTanThongKeNhanhDto("Tong doanh thu", dinhDangTien(tongDoanhThu), "Tong hoa don da ghi nhan", "fa-solid fa-file-invoice-dollar", "from-rose-500 to-pink-600")
        );
    }

    @Override
    public List<LeTanPhongDto> layPhongChoDatPhong() {
        return phongRepository.findByTrangThaiOrderBySoPhongAsc(RoomStatus.AVAILABLE).stream()
                .map(this::toLeTanPhongDto)
                .toList();
    }

    @Override
    public List<LeTanDichVuDto> layDanhSachDichVu() {
        return dichVuRepository.findAllByOrderByIdDesc().stream()
                .map(dichVu -> new LeTanDichVuDto(dichVu.getId(), dichVu.getTen(), dichVu.getImageUrl(), dichVu.getGia()))
                .toList();
    }

    @Override
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
    public TrangDuLieuDto<LeTanCheckInDto> layTrangCheckIn(int trang,
                                                            int kichThuoc,
                                                            String tuKhoa,
                                                            String boLocTrangThai) {
        List<LeTanCheckInDto> duLieu = duLieuCheckIn();
        if (boLocTrangThai != null && !boLocTrangThai.isBlank()) {
            String trangThai = boLocTrangThai.trim().toUpperCase(Locale.ROOT);
            duLieu = duLieu.stream()
                    .filter(item -> item.getTrangThai() != null && item.getTrangThai().name().equals(trangThai))
                    .toList();
        }
        if (tuKhoa != null && !tuKhoa.isBlank()) {
            String keyword = tuKhoa.trim().toLowerCase(Locale.ROOT);
            duLieu = duLieu.stream()
                    .filter(item -> khopTuKhoaCheckIn(item, keyword))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanCheckOutDto> layTrangCheckOut(int trang,
                                                              int kichThuoc,
                                                              String boLocTrangThaiThanhToan,
                                                              String sapXep,
                                                              String tuKhoa) {
        List<LeTanCheckOutDto> duLieu = apDungSapXepCheckOut(duLieuCheckOut(), sapXep);
        if (boLocTrangThaiThanhToan != null && !boLocTrangThaiThanhToan.isBlank()) {
            String boLoc = boLocTrangThaiThanhToan.trim().toLowerCase(Locale.ROOT);
            Set<Long> datPhongIdsTheoBoLoc = timDatPhongIdsTheoBoLocTrangThaiThanhToan(boLoc);
            if (datPhongIdsTheoBoLoc != null) {
                duLieu = duLieu.stream()
                        .filter(item -> datPhongIdsTheoBoLoc.contains(item.getDatPhongId()))
                        .toList();
            } else {
                duLieu = duLieu.stream()
                        .filter(item -> khopBoLocTrangThaiThanhToan(item.getTrangThaiThanhToan(), boLoc))
                        .toList();
            }
        }
        if (tuKhoa != null && !tuKhoa.isBlank()) {
            String keyword = tuKhoa.trim().toLowerCase(Locale.ROOT);
            duLieu = duLieu.stream()
                    .filter(item -> khopTuKhoaCheckOut(item, keyword))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanPhongDto> layTrangQuanLyPhong(int trang, int kichThuoc) {
        return phanTrang(duLieuPhong(), trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang,
                                                              int kichThuoc,
                                                              String boLocTrangThaiDatPhong,
                                                              String sapXep) {
        List<LeTanDatPhongDto> duLieu = apDungSapXepDatPhong(duLieuDatPhong(boLocTrangThaiDatPhong), sapXep);
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanDangOThemDichVuDto> layTrangDangOThemDichVu(int trang,
                                                                            int kichThuoc,
                                                                            String sapXep,
                                                                            String tuKhoa,
                                                                            boolean chiNgayNhanHomNay) {
        List<LeTanDangOThemDichVuDto> duLieu = apDungSapXepDangO(duLieuDangOThemDichVu(), sapXep);
        if (tuKhoa != null && !tuKhoa.isBlank()) {
            String keyword = tuKhoa.trim().toLowerCase(Locale.ROOT);
            duLieu = duLieu.stream()
                    .filter(item -> khopTuKhoaDangO(item, keyword))
                    .toList();
        }
        if (chiNgayNhanHomNay) {
            LocalDate homNay = LocalDate.now();
            duLieu = duLieu.stream()
                    .filter(item -> homNay.equals(item.getNgayNhan()))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    public TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang,
                                                                            int kichThuoc,
                                                                            String boLocTrangThaiThanhToan,
                                                                            String sapXep,
                                                                            String tuKhoa) {
        List<LeTanHoaDonThanhToanDto> duLieu = apDungSapXepHoaDon(duLieuHoaDonThanhToan(), sapXep);
        if (boLocTrangThaiThanhToan != null && !boLocTrangThaiThanhToan.isBlank()) {
            String boLoc = boLocTrangThaiThanhToan.trim().toLowerCase(Locale.ROOT);
            Set<Long> datPhongIdsTheoBoLoc = timDatPhongIdsTheoBoLocTrangThaiThanhToan(boLoc);
            if (datPhongIdsTheoBoLoc != null) {
                duLieu = duLieu.stream()
                        .filter(item -> datPhongIdsTheoBoLoc.contains(item.getDatPhongId()))
                        .toList();
            } else {
                duLieu = duLieu.stream()
                        .filter(item -> khopBoLocTrangThaiThanhToan(item.getTrangThaiThanhToan(), boLoc))
                        .toList();
            }
        }
        if (tuKhoa != null && !tuKhoa.isBlank()) {
            String keyword = tuKhoa.trim().toLowerCase(Locale.ROOT);
            duLieu = duLieu.stream()
                    .filter(item -> khopTuKhoaHoaDon(item, keyword))
                    .toList();
        }
        return phanTrang(duLieu, trang, kichThuoc);
    }

    @Override
    public List<LeTanCheckInDto> layCheckInSapToi(int gioiHan) {
        return duLieuCheckIn().stream().limit(Math.max(1, gioiHan)).toList();
    }

    @Override
    public List<LeTanCheckOutDto> layCheckOutSapToi(int gioiHan) {
        return duLieuCheckOut().stream().limit(Math.max(1, gioiHan)).toList();
    }

    @Override
    public List<LeTanDatPhongDto> layDatPhongGanNhat(int gioiHan) {
        List<DatPhong> datPhongs = datPhongRepository.findTop6ByOrderByNgayDatDesc();
        return apDungSapXepDatPhong(toLeTanDatPhongDtos(datPhongs), "moi-nhat").stream()
                .limit(Math.max(1, gioiHan))
                .toList();
    }

    private List<LeTanCheckInDto> duLieuCheckIn() {
        List<DatPhong> datPhongs = datPhongRepository.findByTrangThaiInOrderByNgayNhanAscIdAsc(
                Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = mapChiTietTheoDatPhongIds(layDanhSachDatPhongIds(datPhongs));

        return datPhongs.stream()
                .map(datPhong -> toLeTanCheckInDto(datPhong, mapChiTiet.getOrDefault(datPhong.getId(), List.of())))
                .filter(dto -> dto.getSoPhong() != null && !dto.getSoPhong().isBlank())
                .sorted(Comparator
                        .comparing(LeTanCheckInDto::getNgayNhan, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(LeTanCheckInDto::getMaDatPhong, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    private List<LeTanCheckOutDto> duLieuCheckOut() {
        List<DatPhong> datPhongs = datPhongRepository.findByTrangThaiInOrderByNgayTraAscIdAsc(
                Set.of(BookingStatus.CHECKED_IN, BookingStatus.CHECKED_OUT)
        );
        List<Long> datPhongIds = layDanhSachDatPhongIds(datPhongs);

        Map<Long, List<ChiTietDatPhong>> mapChiTiet = mapChiTietTheoDatPhongIds(datPhongIds);
        Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong = mapTongTienDichVuTheoDatPhong(datPhongIds);

        Map<Long, HoaDon> mapHoaDonTheoDatPhong = hoaDonRepository.findByDatPhongIdIn(datPhongIds).stream()
                .filter(hoaDon -> hoaDon.getDatPhong() != null && hoaDon.getDatPhong().getId() != null)
                .collect(Collectors.toMap(hoaDon -> hoaDon.getDatPhong().getId(), Function.identity(), (left, right) -> left));

        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = mapTongThanhToanTheoHoaDon(
                mapHoaDonTheoDatPhong.values().stream()
                        .map(HoaDon::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );

        return datPhongs.stream()
                .map(datPhong -> toLeTanCheckOutDto(
                        datPhong,
                        mapChiTiet.getOrDefault(datPhong.getId(), List.of()),
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

    private List<LeTanDatPhongDto> duLieuDatPhong(String boLocTrangThaiDatPhong) {
        List<DatPhong> datPhongs;
        if (boLocTrangThaiDatPhong == null || boLocTrangThaiDatPhong.isBlank()) {
            datPhongs = datPhongRepository.findAllByOrderByNgayDatDesc();
        } else {
            try {
                BookingStatus trangThai = BookingStatus.valueOf(boLocTrangThaiDatPhong.trim().toUpperCase(Locale.ROOT));
                datPhongs = datPhongRepository.findByTrangThaiOrderByNgayDatDesc(trangThai);
            } catch (IllegalArgumentException ignored) {
                datPhongs = List.of();
            }
        }
        return toLeTanDatPhongDtos(datPhongs);
    }

    private List<LeTanDatPhongDto> toLeTanDatPhongDtos(List<DatPhong> datPhongs) {
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = mapChiTietTheoDatPhongIds(layDanhSachDatPhongIds(datPhongs));
        return datPhongs.stream()
                .map(datPhong -> toLeTanDatPhongDto(datPhong, mapChiTiet.getOrDefault(datPhong.getId(), List.of())))
                .toList();
    }

    private List<LeTanDangOThemDichVuDto> duLieuDangOThemDichVu() {
        List<DatPhong> datPhongs = datPhongRepository.findByTrangThaiOrderByIdDesc(BookingStatus.CHECKED_IN);
        List<Long> datPhongIds = layDanhSachDatPhongIds(datPhongs);
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = mapChiTietTheoDatPhongIds(datPhongIds);
        Map<Long, Integer> mapTongSoLuongDichVuTheoDatPhong = mapTongSoLuongDichVuTheoDatPhong(datPhongIds);

        return datPhongs.stream()
                .map(datPhong -> {
                    List<ChiTietDatPhong> danhSachChiTiet = mapChiTiet.getOrDefault(datPhong.getId(), List.of());
                    return new LeTanDangOThemDichVuDto(
                            datPhong.getId(),
                            toMaDatPhong(datPhong.getId()),
                            datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                    datPhong.getKhachHang() != null ? datPhong.getKhachHang().getSdt() : "",
                            hopNhatDanhSachPhong(danhSachChiTiet),
                    mapTongSoLuongDichVuTheoDatPhong.getOrDefault(datPhong.getId(), 0),
                    dongGoiDanhSachPhong(danhSachChiTiet),
                            datPhong.getNgayNhan(),
                            datPhong.getNgayTra()
                    );
                })
                .toList();
    }

    private List<LeTanHoaDonThanhToanDto> duLieuHoaDonThanhToan() {
        List<DatPhong> datPhongs = datPhongRepository.findByTrangThaiInOrderByNgayDatDesc(
                Set.of(BookingStatus.CHECKED_IN, BookingStatus.CHECKED_OUT)
        );
        List<Long> datPhongIds = layDanhSachDatPhongIds(datPhongs);

        Map<Long, List<ChiTietDatPhong>> mapChiTiet = mapChiTietTheoDatPhongIds(datPhongIds);
        Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong = mapTongTienDichVuTheoDatPhong(datPhongIds);

        Map<Long, HoaDon> mapHoaDonTheoDatPhong = hoaDonRepository.findByDatPhongIdIn(datPhongIds).stream()
                .filter(hoaDon -> hoaDon.getDatPhong() != null && hoaDon.getDatPhong().getId() != null)
                .collect(Collectors.toMap(hoaDon -> hoaDon.getDatPhong().getId(), Function.identity(), (left, right) -> left));

        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = mapTongThanhToanTheoHoaDon(
                mapHoaDonTheoDatPhong.values().stream()
                        .map(HoaDon::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );

        return datPhongs.stream()
                .map(datPhong -> {
                    List<ChiTietDatPhong> danhSachChiTiet = mapChiTiet.getOrDefault(datPhong.getId(), List.of());
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
                        Set<KhuyenMai> danhSachKhuyenMai = hoaDon != null ? hoaDon.getKhuyenMais() : Set.of();
                    String maKhuyenMai = hoaDon == null
                            ? "--"
                            : danhSachKhuyenMai.stream()
                            .map(km -> toMaKhuyenMai(km.getId()))
                            .sorted(String::compareToIgnoreCase)
                            .collect(Collectors.joining(", "));
                    if (maKhuyenMai.isBlank()) {
                        maKhuyenMai = "--";
                    }
                        String thongTinKhuyenMai = toThongTinKhuyenMai(danhSachKhuyenMai);
                        BigDecimal tongMucGiamKhuyenMai = tongTienTruocKhuyenMai.subtract(tongTienHoaDon).max(BigDecimal.ZERO);

                    return new LeTanHoaDonThanhToanDto(
                            datPhong.getId(),
                            hoaDon != null ? hoaDon.getId() : null,
                            toMaHoaDon(hoaDon != null ? hoaDon.getId() : null),
                            toMaDatPhong(datPhong.getId()),
                            datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                            hopNhatDanhSachPhong(danhSachChiTiet),
                            maKhuyenMai,
                            thongTinKhuyenMai,
                            hoaDon != null ? hoaDon.getNgayTao() : null,
                            tongTienTruocKhuyenMai,
                            tongMucGiamKhuyenMai,
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
        return phongRepository.findAllByOrderBySoPhongAsc().stream().map(this::toLeTanPhongDto).toList();
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
            datPhong.getKhachHang() != null ? datPhong.getKhachHang().getSdt() : "",
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

    private boolean khopTuKhoaCheckIn(LeTanCheckInDto item, String keyword) {
        return chuaTuKhoa(item.getMaDatPhong(), keyword)
                || chuaTuKhoa(item.getTenKhachHang(), keyword)
                || chuaTuKhoa(item.getSoDienThoai(), keyword)
                || chuaTuKhoa(item.getSoPhong(), keyword)
                || chuaTuKhoa(item.getTrangThai() != null ? item.getTrangThai().name() : "", keyword);
    }

    private boolean khopTuKhoaCheckOut(LeTanCheckOutDto item, String keyword) {
        return chuaTuKhoa(item.getMaDatPhong(), keyword)
                || chuaTuKhoa(item.getTenKhachHang(), keyword)
                || chuaTuKhoa(item.getSoDienThoai(), keyword)
                || chuaTuKhoa(item.getSoPhong(), keyword)
                || chuaTuKhoa(item.getTrangThaiDatPhong() != null ? item.getTrangThaiDatPhong().name() : "", keyword);
    }

    private boolean khopTuKhoaDangO(LeTanDangOThemDichVuDto item, String keyword) {
        return chuaTuKhoa(item.getMaDatPhong(), keyword)
                || chuaTuKhoa(item.getTenKhachHang(), keyword)
                || chuaTuKhoa(item.getSoDienThoai(), keyword)
                || chuaTuKhoa(item.getSoPhong(), keyword);
    }

    private boolean khopTuKhoaHoaDon(LeTanHoaDonThanhToanDto item, String keyword) {
        return chuaTuKhoa(item.getMaHoaDon(), keyword)
                || chuaTuKhoa(item.getMaDatPhong(), keyword)
                || chuaTuKhoa(item.getTenKhachHang(), keyword)
                || chuaTuKhoa(item.getDanhSachPhong(), keyword)
                || chuaTuKhoa(item.getMaKhuyenMai(), keyword)
                || chuaTuKhoa(item.getThongTinKhuyenMai(), keyword)
                || chuaTuKhoa(item.getTrangThaiDatPhong() != null ? item.getTrangThaiDatPhong().name() : "", keyword);
    }

    private boolean chuaTuKhoa(String source, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }
        if (source == null || source.isBlank()) {
            return false;
        }
        return source.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private Set<Long> timDatPhongIdsTheoBoLocTrangThaiThanhToan(String boLoc) {
        Set<Long> hoaDonIds = switch (boLoc) {
            case "chua-thanh-toan" -> hoaDonRepository.findHoaDonIdsChuaThanhToan();
            case "thanh-toan-mot-phan" -> hoaDonRepository.findHoaDonIdsThanhToanMotPhan();
            case "da-thanh-toan" -> hoaDonRepository.findHoaDonIdsDaThanhToan();
            default -> null;
        };

        if (hoaDonIds == null) {
            return null;
        }
        if (hoaDonIds.isEmpty()) {
            return Set.of();
        }

        return hoaDonRepository.findAllById(hoaDonIds).stream()
                .map(HoaDon::getDatPhong)
                .filter(Objects::nonNull)
                .map(DatPhong::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
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

    private Map<Long, List<ChiTietDatPhong>> mapChiTietTheoDatPhongIds(List<Long> datPhongIds) {
        if (datPhongIds == null || datPhongIds.isEmpty()) {
            return Map.of();
        }
        return chiTietDatPhongRepository.findByDatPhongIdIn(datPhongIds).stream()
                .filter(chiTiet -> chiTiet.getDatPhong() != null && chiTiet.getDatPhong().getId() != null)
                .collect(Collectors.groupingBy(chiTiet -> chiTiet.getDatPhong().getId()));
    }

    private List<Long> layDanhSachDatPhongIds(List<DatPhong> datPhongs) {
        if (datPhongs == null || datPhongs.isEmpty()) {
            return List.of();
        }
        return datPhongs.stream()
                .map(DatPhong::getId)
                .filter(Objects::nonNull)
                .toList();
    }

    private Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong(List<Long> datPhongIds) {
        if (datPhongIds == null || datPhongIds.isEmpty()) {
            return Map.of();
        }

        return suDungDichVuRepository.tongTienTheoDanhSachDatPhongIds(datPhongIds).stream()
                .filter(row -> row != null && row.length >= 2)
                .collect(Collectors.toMap(
                        row -> toLong(row[0]),
                        row -> toBigDecimal(row[1]),
                        (left, right) -> right
                ));
    }

    private Map<Long, Integer> mapTongSoLuongDichVuTheoDatPhong(List<Long> datPhongIds) {
        if (datPhongIds == null || datPhongIds.isEmpty()) {
            return Map.of();
        }

        return suDungDichVuRepository.tongSoLuongTheoDanhSachDatPhongIds(datPhongIds).stream()
                .filter(row -> row != null && row.length >= 2)
                .collect(Collectors.toMap(
                        row -> toLong(row[0]),
                        row -> toInt(row[1]),
                        (left, right) -> right
                ));
    }

    private Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon(List<Long> hoaDonIds) {
        if (hoaDonIds == null || hoaDonIds.isEmpty()) {
            return Map.of();
        }

        return thanhToanRepository.tongThanhToanTheoDanhSachHoaDon(hoaDonIds).stream()
                .filter(row -> row != null && row.length >= 2)
                .collect(Collectors.toMap(
                        row -> toLong(row[0]),
                        row -> toBigDecimal(row[1]),
                        (left, right) -> right
                ));
    }

    private BigDecimal tinhTongTienDatPhong(List<ChiTietDatPhong> danhSachChiTiet) {
        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return danhSachChiTiet.stream()
                .map(this::tinhTienPhong)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    private BigDecimal layGiaPhongCoBan(Phong phong) {
        if (phong == null || phong.getLoaiPhong() == null || phong.getLoaiPhong().getGiaCoBan() == null) {
            return BigDecimal.ZERO;
        }
        return phong.getLoaiPhong().getGiaCoBan();
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

    private String dongGoiDanhSachPhong(List<ChiTietDatPhong> danhSachChiTiet) {
        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
            return "";
        }

        return danhSachChiTiet.stream()
                .filter(chiTiet -> chiTiet.getPhong() != null
                        && chiTiet.getPhong().getId() != null
                        && chiTiet.getPhong().getSoPhong() != null
                        && !chiTiet.getPhong().getSoPhong().isBlank())
                .map(chiTiet -> chiTiet.getPhong().getId() + ":" + chiTiet.getPhong().getSoPhong())
                .distinct()
                .collect(Collectors.joining("|"));
    }

    private String toThongTinKhuyenMai(Set<KhuyenMai> danhSachKhuyenMai) {
        if (danhSachKhuyenMai == null || danhSachKhuyenMai.isEmpty()) {
            return "--";
        }

        String moTa = danhSachKhuyenMai.stream()
                .filter(Objects::nonNull)
                .map(this::toMoTaKhuyenMai)
                .filter(item -> item != null && !item.isBlank())
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.joining(" | "));
        return moTa.isBlank() ? "--" : moTa;
    }

    private String toMoTaKhuyenMai(KhuyenMai khuyenMai) {
        if (khuyenMai == null) {
            return "";
        }

        String maKhuyenMai = toMaKhuyenMai(khuyenMai.getId());
        String tenKhuyenMai = khuyenMai.getTen() == null ? "" : khuyenMai.getTen().trim();
        String thongTinGiam = "";

        if (khuyenMai.getLoaiGiam() == DiscountType.PERCENT) {
            BigDecimal tyLe = khuyenMai.getGiaTri() == null ? BigDecimal.ZERO : khuyenMai.getGiaTri();
            thongTinGiam = "Giảm " + tyLe.stripTrailingZeros().toPlainString() + "%";
        } else if (khuyenMai.getLoaiGiam() == DiscountType.AMOUNT) {
            BigDecimal soTien = khuyenMai.getGiaTri() == null ? BigDecimal.ZERO : khuyenMai.getGiaTri();
            thongTinGiam = "Giảm " + dinhDangTien(soTien) + " VND";
        }

        if (tenKhuyenMai.isBlank()) {
            return thongTinGiam.isBlank() ? maKhuyenMai : (maKhuyenMai + " - " + thongTinGiam);
        }
        if (thongTinGiam.isBlank()) {
            return maKhuyenMai + " - " + tenKhuyenMai;
        }
        return maKhuyenMai + " - " + tenKhuyenMai + " (" + thongTinGiam + ")";
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

    private String dinhDangTien(BigDecimal soTien) {
        BigDecimal giaTri = soTien == null ? BigDecimal.ZERO : soTien;
        return giaTri.setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(value.toString());
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        if (value instanceof Number number) {
            return new BigDecimal(number.toString());
        }
        return new BigDecimal(value.toString());
    }

    private Integer toInt(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        return Integer.parseInt(value.toString());
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
