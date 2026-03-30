package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanKhachHangTraCuuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IDatPhongService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DatPhongServiceImpl implements IDatPhongService {

    private final DatPhongRepository datPhongRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;
    private final PhongRepository phongRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;

    public DatPhongServiceImpl(DatPhongRepository datPhongRepository,
                               ChiTietDatPhongRepository chiTietDatPhongRepository,
                               PhongRepository phongRepository,
                               KhachHangRepository khachHangRepository,
                               NhanVienRepository nhanVienRepository,
                               HoaDonRepository hoaDonRepository,
                               ThanhToanRepository thanhToanRepository) {
        this.datPhongRepository = datPhongRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.phongRepository = phongRepository;
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
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

        Map<Long, Phong> mapPhong = phongRepository.findAllById(phongIdsHopLe).stream()
                .collect(Collectors.toMap(Phong::getId, phong -> phong, (left, right) -> left));

        for (Long phongId : phongIdsHopLe) {
            Phong phong = mapPhong.get(phongId);
            if (phong == null) {
                throw new IllegalArgumentException("Không tìm thấy phòng được chọn");
            }
            if (phong.getTrangThai() != RoomStatus.AVAILABLE) {
                throw new IllegalArgumentException("Phòng " + phong.getSoPhong() + " đang ở trạng thái " + phong.getTrangThai() + ", không thể đặt");
            }

            boolean coLichTrung = chiTietDatPhongRepository.existsLichPhongTrung(
                    phong.getId(),
                    null,
                    form.getNgayNhan(),
                    form.getNgayTra(),
                    Set.of(
                            BookingStatus.PENDING,
                            BookingStatus.CONFIRMED,
                            BookingStatus.CHECKED_IN,
                            BookingStatus.CHECKED_OUT
                    )
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
        datPhong = datPhongRepository.save(datPhong);

        for (Long phongId : phongIdsHopLe) {
            Phong phong = mapPhong.get(phongId);
            ChiTietDatPhong chiTietDatPhong = new ChiTietDatPhong();
            chiTietDatPhong.setDatPhong(datPhong);
            chiTietDatPhong.setPhong(phong);
            chiTietDatPhong.setGia(layGiaPhongCoBan(phong));
            chiTietDatPhongRepository.save(chiTietDatPhong);
        }
    }

    @Override
    public void huyDatPhong(Long datPhongId, Long nhanVienId) {
        if (datPhongId == null) {
            throw new IllegalArgumentException("Đặt phòng không hợp lệ");
        }

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt phòng"));

        if (!Set.of(BookingStatus.PENDING, BookingStatus.CONFIRMED).contains(datPhong.getTrangThai())) {
            throw new IllegalArgumentException("Chỉ được hủy đặt phòng ở trạng thái PENDING/CONFIRMED");
        }

        datPhong.setTrangThai(BookingStatus.CANCELLED);
        timNhanVienTheoId(nhanVienId).ifPresent(datPhong::setNhanVien);
        datPhongRepository.save(datPhong);

        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null || hoaDon.getId() == null) {
            return;
        }

        BigDecimal tongDaThanhToan = thanhToanRepository.tongThanhToanTheoHoaDonId(hoaDon.getId());
        if (tongDaThanhToan == null) {
            tongDaThanhToan = BigDecimal.ZERO;
        }

        if (tongDaThanhToan.compareTo(BigDecimal.ZERO) <= 0) {
            hoaDon.setKhuyenMais(new HashSet<>());
            hoaDonRepository.delete(hoaDon);
            return;
        }

        hoaDon.setTongTien(tongDaThanhToan);
        hoaDon.setKhuyenMais(new HashSet<>());
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public Optional<LeTanKhachHangTraCuuDto> timKhachHangTheoSdt(String sdt) {
        if (laRong(sdt)) {
            return Optional.empty();
        }

        String sdtChuan = sdt.trim();
        return khachHangRepository.findBySdt(sdtChuan)
                .map(khachHang -> new LeTanKhachHangTraCuuDto(
                        khachHang.getId(),
                        khachHang.getTen(),
                        khachHang.getSdt(),
                        khachHang.getEmail()
                ));
    }

    private Optional<NhanVien> timNhanVienTheoId(Long nhanVienId) {
        if (nhanVienId == null) {
            return Optional.empty();
        }
        return nhanVienRepository.findByIdWithTaiKhoan(nhanVienId);
    }

    private KhachHang timHoacTaoKhachHang(String tenKhachHang, String sdt, String email) {
        String tenChuan = tenKhachHang.trim();
        String sdtChuan = sdt.trim();
        String emailChuan = laRong(email) ? null : email.trim();

        Optional<KhachHang> theoSdt = khachHangRepository.findBySdt(sdtChuan);
        if (theoSdt.isPresent()) {
            KhachHang khachHang = theoSdt.get();

            boolean khopTen = soSanhChuoiKhongPhanBietHoaThuong(khachHang.getTen(), tenChuan);
            boolean khopEmail = soSanhEmail(khachHang.getEmail(), emailChuan);
            if (!khopTen || !khopEmail) {
                throw new IllegalArgumentException("SĐT đã tồn tại với thông tin khách hàng khác. Chỉ được dùng đúng tên/email đã lưu cho SĐT này hoặc đổi sang SĐT khác");
            }

            if (!laRong(emailChuan)) {
                Optional<KhachHang> theoEmail = khachHangRepository.findByEmailIgnoreCase(emailChuan);
                if (theoEmail.isPresent() && !Objects.equals(theoEmail.get().getId(), khachHang.getId())) {
                    throw new IllegalArgumentException("Email đã tồn tại ở khách hàng khác, vui lòng kiểm tra lại SĐT");
                }
            }
            return khachHang;
        }

        if (!laRong(emailChuan)) {
            Optional<KhachHang> theoEmail = khachHangRepository.findByEmailIgnoreCase(emailChuan);
            if (theoEmail.isPresent()) {
                throw new IllegalArgumentException("Email đã tồn tại ở khách hàng khác, vui lòng kiểm tra lại SĐT");
            }
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setTen(tenChuan);
        khachHang.setSdt(sdtChuan);
        khachHang.setEmail(emailChuan);
        return khachHangRepository.save(khachHang);
    }

    private boolean soSanhChuoiKhongPhanBietHoaThuong(String left, String right) {
        String leftValue = left == null ? "" : left.trim();
        String rightValue = right == null ? "" : right.trim();
        if (leftValue.isEmpty() && rightValue.isEmpty()) {
            return true;
        }
        return leftValue.equalsIgnoreCase(rightValue);
    }

    private boolean soSanhEmail(String left, String right) {
        String leftValue = left == null ? "" : left.trim();
        String rightValue = right == null ? "" : right.trim();
        if (leftValue.isEmpty() && rightValue.isEmpty()) {
            return true;
        }
        return leftValue.equalsIgnoreCase(rightValue);
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

        LocalDate homNay = LocalDate.now();
        if (form.getNgayNhan().isBefore(homNay)) {
            throw new IllegalArgumentException("Ngay nhan khong duoc nho hon ngay hien tai");
        }
    }

    private BigDecimal layGiaPhongCoBan(Phong phong) {
        if (phong == null || phong.getLoaiPhong() == null || phong.getLoaiPhong().getGiaCoBan() == null) {
            return BigDecimal.ZERO;
        }
        return phong.getLoaiPhong().getGiaCoBan();
    }

    private boolean laRong(String value) {
        return value == null || value.isBlank();
    }
}
