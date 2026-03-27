package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanCheckInDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanCheckOutDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanDatPhongDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanDichVuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanHoaDonThanhToanDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanPhongDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanThongKeNhanhDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.TrangDuLieuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.SuDungDichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ThanhToan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.ChiTietDatPhongQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.DatPhongQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.HoaDonQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.KhachHangQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.NhanVienQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.PhongQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.SuDungDichVuQueriesStream;
import hcmute.system.hotel.cknhom11qlhotel.service.receptionist.stream.ThanhToanQueriesStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        List<HoaDon> hoaDons = hoaDonRepository.findAllByOrderByNgayTaoDesc();

        LocalDate homNay = LocalDate.now();
        long tongCheckInHomNay = chiTietDatPhongQueriesStream.locTheoNgayNhan(toObjectList(chiTietDatPhongs), homNay).size();

        long tongCheckOutHomNay = chiTietDatPhongs.stream()
                .filter(chiTiet -> homNay.equals(chiTiet.getNgayTra()))
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
        return phanTrang(duLieuCheckOut(), trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanPhongDto> layTrangQuanLyPhong(int trang, int kichThuoc) {
        return phanTrang(duLieuPhong(), trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanDatPhongDto> layTrangDatPhong(int trang, int kichThuoc) {
        return phanTrang(duLieuDatPhong(), trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public TrangDuLieuDto<LeTanHoaDonThanhToanDto> layTrangHoaDonThanhToan(int trang, int kichThuoc) {
        return phanTrang(duLieuHoaDonThanhToan(), trang, kichThuoc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanPhongDto> layPhongChoDatPhong() {
        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        return phongs.stream()
                .filter(phong -> phong.getTrangThai() != RoomStatus.MAINTENANCE)
                .map(this::toLeTanPhongDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeTanDichVuDto> layDanhSachDichVu() {
        return dichVuRepository.findAllByOrderByIdDesc().stream()
                .map(dichVu -> new LeTanDichVuDto(dichVu.getId(), dichVu.getTen(), dichVu.getGia()))
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

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng");
        }

        LocalDate homNay = LocalDate.now();
        List<ChiTietDatPhong> chiTietDangHieuLuc = chiTietDatPhongQueriesStream.locTheoNgayHieuLuc(danhSachChiTiet, homNay);
        if (chiTietDangHieuLuc.isEmpty()) {
            LocalDate ngayNhanSomNhat = danhSachChiTiet.stream()
                    .map(ChiTietDatPhong::getNgayNhan)
                    .filter(value -> value != null)
                    .min(LocalDate::compareTo)
                    .orElse(null);
            if (ngayNhanSomNhat != null && homNay.isBefore(ngayNhanSomNhat)) {
                throw new IllegalArgumentException("Chưa đến ngày nhận phòng");
            }
            throw new IllegalArgumentException("Đặt phòng đã qua ngày check-in hợp lệ");
        }

        datPhong.setTrangThai(BookingStatus.CHECKED_IN);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);

        chiTietDangHieuLuc.stream()
                .map(ChiTietDatPhong::getPhong)
                .filter(phong -> phong != null && phong.getId() != null)
                .collect(Collectors.toMap(Phong::getId, phong -> phong, (left, right) -> left))
                .values()
                .forEach(phong -> {
                    phong.setTrangThai(RoomStatus.OCCUPIED);
                    phongRepository.save(phong);
                });

        datPhongRepository.save(datPhong);
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

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng");
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

        danhSachChiTiet.stream()
                .map(ChiTietDatPhong::getPhong)
                .filter(phong -> phong != null && phong.getId() != null)
                .collect(Collectors.toMap(Phong::getId, phong -> phong, (left, right) -> left))
                .values()
                .forEach(phong -> {
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

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
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

        DichVu dichVu = dichVuRepository.findById(dichVuId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ"));

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.locDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs), datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng để gán dịch vụ");
        }

        LocalDate homNay = LocalDate.now();
        ChiTietDatPhong chiTietHieuLuc = timChiTietHieuLucTheoNgay(danhSachChiTiet, homNay)
                .orElseThrow(() -> new IllegalArgumentException("Không có phòng lưu trú hiệu lực trong ngày để thêm dịch vụ"));

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
    public void capNhatTrangThaiPhong(Long phongId, RoomStatus trangThai) {
        if (phongId == null || trangThai == null) {
            throw new IllegalArgumentException("Du lieu cap nhat phong khong hop le");
        }

        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        Phong phong = phongQueriesStream.timTheoId(toObjectList(phongs), phongId)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay phong"));

        phong.setTrangThai(trangThai);
        phongRepository.save(phong);
    }

    @Override
    public void taoDatPhong(LeTanTaoDatPhongFormDto form, Long nhanVienId) {
        kiemTraFormDatPhong(form);

        NhanVien nhanVien = timNhanVienTheoId(nhanVienId)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay nhan vien dang thao tac"));

        List<Phong> phongs = phongQueriesStream.sapXepTheoSoPhong(toObjectList(phongRepository.findAllByOrderByIdDesc()));
        Phong phong = phongQueriesStream.timTheoId(toObjectList(phongs), form.getPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay phong duoc chon"));

        if (phong.getTrangThai() == RoomStatus.MAINTENANCE) {
            throw new IllegalArgumentException("Phong dang bao tri, khong the dat");
        }

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        boolean coLichTrung = chiTietDatPhongQueriesStream.coLichTrung(
                toObjectList(chiTietDatPhongs),
                phong.getId(),
                form.getNgayNhan(),
                form.getNgayTra(),
                Set.of(BookingStatus.CANCELLED)
        );
        if (coLichTrung) {
            throw new IllegalArgumentException("Khoang ngay dat trung voi lich hien tai cua phong");
        }

        KhachHang khachHang = timHoacTaoKhachHang(form.getTenKhachHang(), form.getSdt(), form.getEmail());

        DatPhong datPhong = new DatPhong();
        datPhong.setKhachHang(khachHang);
        datPhong.setNhanVien(nhanVien);
        datPhong.setNgayDat(LocalDateTime.now());
        datPhong.setTrangThai(BookingStatus.CONFIRMED);
        datPhongRepository.save(datPhong);

        ChiTietDatPhong chiTietDatPhong = new ChiTietDatPhong();
        chiTietDatPhong.setDatPhong(datPhong);
        chiTietDatPhong.setPhong(phong);
        chiTietDatPhong.setNgayNhan(form.getNgayNhan());
        chiTietDatPhong.setNgayTra(form.getNgayTra());
        chiTietDatPhong.setGia(layGiaPhongCoBan(phong));
        chiTietDatPhongRepository.save(chiTietDatPhong);

        LocalDate homNay = LocalDate.now();
        if (!form.getNgayNhan().isAfter(homNay) && form.getNgayTra().isAfter(homNay)) {
            phong.setTrangThai(RoomStatus.OCCUPIED);
            phongRepository.save(phong);
        }
    }

    private List<LeTanCheckInDto> duLieuCheckIn() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<DatPhong> datPhongLoc = datPhongQueriesStream.locTheoTrangThai(
                toObjectList(datPhongs),
                Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
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

        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
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
        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));

        return datPhongs.stream()
            .map(datPhong -> toLeTanDatPhongDto(datPhong, chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId())))
                .toList();
    }

        private List<LeTanHoaDonThanhToanDto> duLieuHoaDonThanhToan() {
        List<DatPhong> datPhongs = datPhongQueriesStream.sapXepTheoNgayDatGiam(toObjectList(datPhongRepository.findAllByOrderByNgayDatDesc()));
        List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findAllByOrderByNgayNhanDesc();
        Map<Long, List<ChiTietDatPhong>> mapChiTiet = chiTietDatPhongQueriesStream.mapDanhSachTheoDatPhongId(toObjectList(chiTietDatPhongs));
            Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong = suDungDichVuQueriesStream.tongTienTheoDatPhong(toObjectList(suDungDichVuRepository.findAllByOrderByThoiDiemDesc()));

        Map<Long, HoaDon> mapHoaDonTheoDatPhong = hoaDonQueriesStream.mapTheoDatPhongId(toObjectList(hoaDonRepository.findAllByOrderByNgayTaoDesc()));
        Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon = thanhToanQueriesStream.tongThanhToanTheoHoaDon(toObjectList(thanhToanRepository.findAllByOrderByNgayThanhToanDesc()));

        return datPhongs.stream()
            .map(datPhong -> {
                List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongQueriesStream.danhSachOrEmpty(mapChiTiet, datPhong.getId());
                HoaDon hoaDon = mapHoaDonTheoDatPhong.get(datPhong.getId());
                BigDecimal tongTienHoaDonTinhToan = tinhTongTienDatPhong(danhSachChiTiet)
                        .add(mapTongTienDichVuTheoDatPhong.getOrDefault(datPhong.getId(), BigDecimal.ZERO));
                BigDecimal tongTienHoaDon = hoaDon != null && hoaDon.getTongTien() != null
                        ? hoaDon.getTongTien()
                        : tongTienHoaDonTinhToan;
                if (tongTienHoaDon.compareTo(tongTienHoaDonTinhToan) < 0) {
                    tongTienHoaDon = tongTienHoaDonTinhToan;
                }
                BigDecimal tongDaThanhToan = hoaDon == null || hoaDon.getId() == null
                        ? BigDecimal.ZERO
                        : mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);
                BigDecimal soTienConLai = tongTienHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);
                ThongTinThanhToan thongTinThanhToan = xacDinhTrangThaiThanhToan(hoaDon, tongDaThanhToan);

                return new LeTanHoaDonThanhToanDto(
                    datPhong.getId(),
                    hoaDon != null ? hoaDon.getId() : null,
                    toMaHoaDon(hoaDon != null ? hoaDon.getId() : null),
                    toMaDatPhong(datPhong.getId()),
                    datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                    hopNhatDanhSachPhong(danhSachChiTiet),
                    hoaDon != null ? hoaDon.getNgayTao() : null,
                    tongTienHoaDon,
                    tongDaThanhToan,
                    soTienConLai,
                    thongTinThanhToan.nhan(),
                    thongTinThanhToan.sacThai()
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
        LocalDate ngayNhan = danhSachChiTiet.stream()
            .map(ChiTietDatPhong::getNgayNhan)
            .filter(value -> value != null)
            .min(LocalDate::compareTo)
            .orElse(null);
        LocalDate ngayTra = danhSachChiTiet.stream()
            .map(ChiTietDatPhong::getNgayTra)
            .filter(value -> value != null)
            .max(LocalDate::compareTo)
            .orElse(null);

        return new LeTanCheckInDto(
                datPhong.getId(),
                toMaDatPhong(datPhong.getId()),
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getSdt() : "",
            hopNhatDanhSachPhong(danhSachChiTiet),
                ngayNhan,
                ngayTra,
                datPhong.getTrangThai()
        );
    }

    private LeTanCheckOutDto toLeTanCheckOutDto(DatPhong datPhong,
                                                List<ChiTietDatPhong> danhSachChiTiet,
                                                Map<Long, BigDecimal> mapTongTienDichVuTheoDatPhong,
                                                Map<Long, HoaDon> mapHoaDonTheoDatPhong,
                                                Map<Long, BigDecimal> mapTongThanhToanTheoHoaDon) {
        LocalDate ngayTra = danhSachChiTiet.stream()
            .map(ChiTietDatPhong::getNgayTra)
            .filter(value -> value != null)
            .max(LocalDate::compareTo)
            .orElse(null);
        BigDecimal tongTienTamTinh = tinhTongTienDatPhong(danhSachChiTiet)
            .add(mapTongTienDichVuTheoDatPhong.getOrDefault(datPhong.getId(), BigDecimal.ZERO));

        HoaDon hoaDon = mapHoaDonTheoDatPhong.get(datPhong.getId());
        BigDecimal tongDaThanhToan = hoaDon == null || hoaDon.getId() == null
                ? BigDecimal.ZERO
                : mapTongThanhToanTheoHoaDon.getOrDefault(hoaDon.getId(), BigDecimal.ZERO);
        BigDecimal tongHoaDon = (hoaDon != null && hoaDon.getTongTien() != null)
            ? hoaDon.getTongTien()
            : tongTienTamTinh;
        if (tongHoaDon.compareTo(tongTienTamTinh) < 0) {
            tongHoaDon = tongTienTamTinh;
        }
        BigDecimal soTienConLai = tongHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);
        boolean daThanhToanDu = soTienConLai.compareTo(BigDecimal.ZERO) <= 0;
        ThongTinThanhToan thongTinThanhToan = xacDinhTrangThaiThanhToan(hoaDon, tongDaThanhToan);

        return new LeTanCheckOutDto(
                datPhong.getId(),
                toMaDatPhong(datPhong.getId()),
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                hopNhatDanhSachPhong(danhSachChiTiet),
                ngayTra,
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
        LocalDate ngayNhan = danhSachChiTiet.stream()
                .map(ChiTietDatPhong::getNgayNhan)
                .filter(value -> value != null)
                .min(LocalDate::compareTo)
                .orElse(null);
        LocalDate ngayTra = danhSachChiTiet.stream()
                .map(ChiTietDatPhong::getNgayTra)
                .filter(value -> value != null)
                .max(LocalDate::compareTo)
                .orElse(null);

        return new LeTanDatPhongDto(
                datPhong.getId(),
                toMaDatPhong(datPhong.getId()),
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getTen() : "",
                datPhong.getKhachHang() != null ? datPhong.getKhachHang().getSdt() : "",
                hopNhatDanhSachPhong(danhSachChiTiet),
                datPhong.getNgayDat(),
                ngayNhan,
                ngayTra,
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

    private Optional<ChiTietDatPhong> timChiTietHieuLucTheoNgay(List<ChiTietDatPhong> danhSachChiTiet, LocalDate ngay) {
        List<ChiTietDatPhong> danhSach = chiTietDatPhongQueriesStream.locTheoNgayHieuLuc(danhSachChiTiet, ngay);
        if (danhSach.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(danhSach.get(0));
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
        BigDecimal tongTienThucTe = tinhTongTienDatPhong(danhSachChiTiet)
                .add(tinhTongTienDichVu(datPhong.getId()));

        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null) {
            hoaDon = new HoaDon();
            hoaDon.setDatPhong(datPhong);
            hoaDon.setNhanVien(nhanVienThaoTac != null ? nhanVienThaoTac : datPhong.getNhanVien());
            hoaDon.setTongTien(tongTienThucTe);
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon = hoaDonRepository.save(hoaDon);
            datPhong.setHoaDon(hoaDon);
            datPhongRepository.save(datPhong);
            return hoaDon;
        }

        if (hoaDon.getTongTien() == null || hoaDon.getTongTien().compareTo(tongTienThucTe) != 0) {
            hoaDon.setTongTien(tongTienThucTe);
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
        if (form.getPhongId() == null || form.getNgayNhan() == null || form.getNgayTra() == null) {
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
        LocalDate ngayNhan = chiTietDatPhong.getNgayNhan();
        LocalDate ngayTra = chiTietDatPhong.getNgayTra();

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
