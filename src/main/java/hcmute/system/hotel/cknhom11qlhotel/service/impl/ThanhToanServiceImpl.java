package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ThanhToan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IThanhToanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ThanhToanServiceImpl implements IThanhToanService {

    private final DatPhongRepository datPhongRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final NhanVienRepository nhanVienRepository;
    private final SuDungDichVuRepository suDungDichVuRepository;

    public ThanhToanServiceImpl(DatPhongRepository datPhongRepository,
                                ChiTietDatPhongRepository chiTietDatPhongRepository,
                                HoaDonRepository hoaDonRepository,
                                ThanhToanRepository thanhToanRepository,
                                KhuyenMaiRepository khuyenMaiRepository,
                                NhanVienRepository nhanVienRepository,
                                SuDungDichVuRepository suDungDichVuRepository) {
        this.datPhongRepository = datPhongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.suDungDichVuRepository = suDungDichVuRepository;
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

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ đặt phòng đang CHECKED_IN mới được thu tiền");
        }

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongRepository.findByDatPhongIdOrderByIdDesc(datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng");
        }

        HoaDon hoaDon = capNhatTongTienHoaDonTheoThucTe(
                datPhong,
                danhSachChiTiet,
                timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien())
        );

        BigDecimal tongDaThanhToan = thanhToanRepository.tongThanhToanTheoHoaDonId(hoaDon.getId());
        if (tongDaThanhToan == null) {
            tongDaThanhToan = BigDecimal.ZERO;
        }
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
    public void apDungMaKhuyenMai(Long datPhongId, String maKhuyenMai, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (datPhong.getTrangThai() != BookingStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Chỉ booking CHECKED_IN mới được áp dụng khuyến mãi");
        }

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongRepository.findByDatPhongIdOrderByIdDesc(datPhongId);
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

        kiemTraKhuyenMaiDaSuDungTheoSdt(datPhong, khuyenMai);

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

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));
        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null || hoaDon.getId() == null) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn để thanh toán");
        }

        BigDecimal tongDaThanhToan = thanhToanRepository.tongThanhToanTheoHoaDonId(hoaDon.getId());
        if (tongDaThanhToan == null) {
            tongDaThanhToan = BigDecimal.ZERO;
        }

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

    private Optional<NhanVien> timNhanVienTheoId(Long nhanVienId) {
        if (nhanVienId == null) {
            return Optional.empty();
        }
        return nhanVienRepository.findByIdWithTaiKhoan(nhanVienId);
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

    private void kiemTraKhuyenMaiDaSuDungTheoSdt(DatPhong datPhong, KhuyenMai khuyenMai) {
        if (datPhong == null || khuyenMai == null || khuyenMai.getId() == null) {
            return;
        }
        if (datPhong.getKhachHang() == null || datPhong.getKhachHang().getSdt() == null || datPhong.getKhachHang().getSdt().isBlank()) {
            return;
        }

        String sdt = datPhong.getKhachHang().getSdt().trim();
        boolean daDung = hoaDonRepository.existsKhuyenMaiDaDungBoiSdt(sdt, khuyenMai.getId(), datPhong.getId());
        if (daDung) {
            throw new IllegalArgumentException("Mã khuyến mãi này đã được dùng cho SĐT " + sdt + ", không thể áp dụng lại");
        }
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
