package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.entity.SuDungDichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.repository.ChiTietDatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IDichVuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DichVuServiceImpl implements IDichVuService {

    private final DatPhongRepository datPhongRepository;
    private final DichVuRepository dichVuRepository;
    private final ChiTietDatPhongRepository chiTietDatPhongRepository;
    private final SuDungDichVuRepository suDungDichVuRepository;
    private final NhanVienRepository nhanVienRepository;
    private final HoaDonRepository hoaDonRepository;

    public DichVuServiceImpl(DatPhongRepository datPhongRepository,
                             DichVuRepository dichVuRepository,
                             ChiTietDatPhongRepository chiTietDatPhongRepository,
                             SuDungDichVuRepository suDungDichVuRepository,
                             NhanVienRepository nhanVienRepository,
                             HoaDonRepository hoaDonRepository) {
        this.datPhongRepository = datPhongRepository;
        this.dichVuRepository = dichVuRepository;
        this.chiTietDatPhongRepository = chiTietDatPhongRepository;
        this.suDungDichVuRepository = suDungDichVuRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.hoaDonRepository = hoaDonRepository;
    }

    @Override
    public void themDichVuTrongThoiGianO(Long datPhongId,
                                         Long dichVuId,
                                         Integer soLuong,
                                         Long phongId,
                                         boolean apDungTatCaPhong,
                                         Long nhanVienId) {
        if (datPhongId == null || dichVuId == null) {
            throw new IllegalArgumentException("Dữ liệu thêm dịch vụ không hợp lệ");
        }
        if (soLuong == null || soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng dịch vụ phải lớn hơn 0");
        }

        DatPhong datPhong = datPhongRepository.findById(datPhongId)
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

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongRepository.findByDatPhongIdOrderByIdDesc(datPhongId);
        if (danhSachChiTiet.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy chi tiết đặt phòng để gán dịch vụ");
        }

        Map<Long, ChiTietDatPhong> mapChiTietTheoPhong = danhSachChiTiet.stream()
                .filter(chiTiet -> chiTiet.getPhong() != null && chiTiet.getPhong().getId() != null)
                .collect(java.util.stream.Collectors.toMap(
                        chiTiet -> chiTiet.getPhong().getId(),
                        chiTiet -> chiTiet,
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
        if (mapChiTietTheoPhong.isEmpty()) {
            throw new IllegalArgumentException("Không có phòng hợp lệ để gán dịch vụ");
        }

        LocalDateTime thoiDiemThem = LocalDateTime.now();
        if (apDungTatCaPhong) {
            for (ChiTietDatPhong chiTiet : mapChiTietTheoPhong.values()) {
                taoBanGhiSuDungDichVu(datPhong, chiTiet.getPhong(), dichVu, soLuong, thoiDiemThem);
            }
        } else {
            if (phongId == null) {
                throw new IllegalArgumentException("Vui lòng chọn phòng áp dụng dịch vụ");
            }
            ChiTietDatPhong chiTietDaChon = mapChiTietTheoPhong.get(phongId);
            if (chiTietDaChon == null || chiTietDaChon.getPhong() == null) {
                throw new IllegalArgumentException("Phòng được chọn không thuộc booking này");
            }
            taoBanGhiSuDungDichVu(datPhong, chiTietDaChon.getPhong(), dichVu, soLuong, thoiDiemThem);
        }

        NhanVien nhanVien = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
    }

    private void taoBanGhiSuDungDichVu(DatPhong datPhong,
                                       Phong phong,
                                       DichVu dichVu,
                                       Integer soLuong,
                                       LocalDateTime thoiDiem) {
        if (datPhong == null || phong == null || phong.getId() == null || dichVu == null) {
            throw new IllegalArgumentException("Dữ liệu thêm dịch vụ không hợp lệ");
        }

        SuDungDichVu suDungDichVu = new SuDungDichVu();
        suDungDichVu.setDatPhong(datPhong);
        suDungDichVu.setPhong(phong);
        suDungDichVu.setDichVu(dichVu);
        suDungDichVu.setSoLuong(soLuong);
        suDungDichVu.setThoiDiem(thoiDiem != null ? thoiDiem : LocalDateTime.now());
        suDungDichVuRepository.save(suDungDichVu);
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

        List<ChiTietDatPhong> danhSachChiTiet = chiTietDatPhongRepository.findByDatPhongIdOrderByIdDesc(datPhong.getId());
        NhanVien nhanVien = timNhanVienTheoId(nhanVienId).orElse(datPhong.getNhanVien());
        capNhatTongTienHoaDonTheoThucTe(datPhong, danhSachChiTiet, nhanVien);
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
}
