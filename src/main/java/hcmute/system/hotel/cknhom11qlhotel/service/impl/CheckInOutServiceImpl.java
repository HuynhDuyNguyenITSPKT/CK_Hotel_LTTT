package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.ICheckInOutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CheckInOutServiceImpl implements ICheckInOutService {

    private final DatPhongRepository datPhongRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;
    private final PhongRepository phongRepository;
    private final NhanVienRepository nhanVienRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final SuDungDichVuRepository suDungDichVuRepository;

    public CheckInOutServiceImpl(DatPhongRepository datPhongRepository,
                                 ChiTietDatPhongRepository chiTietDatPhongRepository,
                                 PhongRepository phongRepository,
                                 NhanVienRepository nhanVienRepository,
                                 HoaDonRepository hoaDonRepository,
                                 ThanhToanRepository thanhToanRepository,
                                 SuDungDichVuRepository suDungDichVuRepository) {
        this.datPhongRepository = datPhongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.phongRepository = phongRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.suDungDichVuRepository = suDungDichVuRepository;
    }

    @Override
    public void thucHienCheckIn(Long datPhongId, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
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

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongRepository.findByDatPhongIdOrderByIdDesc(datPhongId);
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
                .sorted(Comparator.comparing(Phong::getSoPhong, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();

        if (phongCanCheckIn.isEmpty()) {
            throw new IllegalArgumentException("Không có phòng hợp lệ để check-in");
        }

        for (Phong phong : phongCanCheckIn) {
            if (phong.getTrangThai() != RoomStatus.AVAILABLE) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " đang ở trạng thái " + phong.getTrangThai() + ", không thể check-in");
            }

            boolean coKhachDangO = chiTietDatPhongRepository.existsLichPhongTrung(
                    phong.getId(),
                    datPhongId,
                    datPhong.getNgayNhan(),
                    datPhong.getNgayTra(),
                    Set.of(BookingStatus.CHECKED_IN)
            );
            if (coKhachDangO) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " đang có khách lưu trú ở booking khác");
            }
        }

        datPhong.setTrangThai(BookingStatus.CHECKED_IN);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);

        phongCanCheckIn.forEach(phong -> phong.setTrangThai(RoomStatus.OCCUPIED));
        phongRepository.saveAll(phongCanCheckIn);

        datPhongRepository.save(datPhong);

        NhanVien nhanVienThaoTac = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVienThaoTac);
    }

    @Override
    public void thucHienCheckOut(Long datPhongId, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ đặt phòng đã check-in mới được check-out");
        }

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongRepository.findByDatPhongIdOrderByIdDesc(datPhongId);
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
        BigDecimal tongDaThanhToan = thanhToanRepository.tongThanhToanTheoHoaDonId(hoaDon.getId());
        if (tongDaThanhToan == null) {
            tongDaThanhToan = BigDecimal.ZERO;
        }

        if (tongDaThanhToan.compareTo(tongHoaDon) < 0) {
            BigDecimal conLai = tongHoaDon.subtract(tongDaThanhToan).max(BigDecimal.ZERO);
            throw new IllegalArgumentException("Hóa đơn chưa thanh toán đủ. Còn lại: " + dinhDangTien(conLai) + " VND");
        }

        datPhong.setTrangThai(BookingStatus.CHECKED_OUT);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);

        phongDangO.forEach(phong -> phong.setTrangThai(RoomStatus.CLEANING));
        phongRepository.saveAll(phongDangO);

        datPhongRepository.save(datPhong);
    }

    private Optional<NhanVien> timNhanVienTheoId(Long nhanVienId) {
        if (nhanVienId == null) {
            return Optional.empty();
        }
        return nhanVienRepository.findByIdWithTaiKhoan(nhanVienId);
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

    private BigDecimal tinhTongTienDichVu(Long datPhongId) {
        if (datPhongId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal tong = suDungDichVuRepository.tinhTongTienTheoDatPhongId(datPhongId);
        return tong == null ? BigDecimal.ZERO : tong;
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

    private String dinhDangTien(BigDecimal soTien) {
        BigDecimal giaTri = soTien == null ? BigDecimal.ZERO : soTien;
        return giaTri.setScale(0, RoundingMode.HALF_UP).toPlainString();
    }
}
