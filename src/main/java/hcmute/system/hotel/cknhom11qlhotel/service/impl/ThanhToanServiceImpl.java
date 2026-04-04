package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.ThanhToan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IHoaDonTinhTienService;
import hcmute.system.hotel.cknhom11qlhotel.service.IThanhToanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final IHoaDonTinhTienService hoaDonTinhTienService;

    public ThanhToanServiceImpl(DatPhongRepository datPhongRepository,
                                ChiTietDatPhongRepository chiTietDatPhongRepository,
                                HoaDonRepository hoaDonRepository,
                                ThanhToanRepository thanhToanRepository,
                                KhuyenMaiRepository khuyenMaiRepository,
                                NhanVienRepository nhanVienRepository,
                                IHoaDonTinhTienService hoaDonTinhTienService) {
        this.datPhongRepository = datPhongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.hoaDonTinhTienService = hoaDonTinhTienService;
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

        // Luon dong bo tong hoa don theo du lieu thuc te truoc khi ghi nhan thanh toan.
        HoaDon hoaDon = hoaDonTinhTienService.capNhatTongTienHoaDonTheoThucTe(
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
            throw new IllegalArgumentException("Số tiền vượt quá công nợ còn lại: " + hoaDonTinhTienService.dinhDangTien(soTienConLai) + " VND");
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
        HoaDon hoaDon = hoaDonTinhTienService.capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);

        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            hoaDon.setKhuyenMais(new HashSet<>());
            hoaDonRepository.save(hoaDon);
            hoaDonTinhTienService.capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
            return;
        }

        KhuyenMai khuyenMai = timKhuyenMaiTheoMa(maKhuyenMai)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã khuyến mãi hợp lệ"));

        kiemTraKhuyenMaiDaSuDungTheoSdt(datPhong, khuyenMai);

        Set<KhuyenMai> khuyenMaiApDung = new HashSet<>();
        khuyenMaiApDung.add(khuyenMai);
        hoaDon.setKhuyenMais(khuyenMaiApDung);
        hoaDonRepository.save(hoaDon);

        hoaDonTinhTienService.capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
    }

    @Override
    public void apDungKhuyenMaiVaThanhToan(Long datPhongId,
                                           String maKhuyenMai,
                                           BigDecimal soTienThanhToan,
                                           PaymentMethod phuongThuc,
                                           Long nhanVienId) {
        // Apply promotion first so remaining debt is calculated on the discounted total.
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
        // Cap received amount to current debt to avoid overpayment records.
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
        // Accept both KM-<id> and exact promotion name from UI input.
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
}
