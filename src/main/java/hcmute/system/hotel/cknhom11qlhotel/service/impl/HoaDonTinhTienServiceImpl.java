package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.repository.DatPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.SuDungDichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IHoaDonTinhTienService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class HoaDonTinhTienServiceImpl implements IHoaDonTinhTienService {

    private final HoaDonRepository hoaDonRepository;
    private final DatPhongRepository datPhongRepository;
    private final SuDungDichVuRepository suDungDichVuRepository;

    public HoaDonTinhTienServiceImpl(HoaDonRepository hoaDonRepository,
                                     DatPhongRepository datPhongRepository,
                                     SuDungDichVuRepository suDungDichVuRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.datPhongRepository = datPhongRepository;
        this.suDungDichVuRepository = suDungDichVuRepository;
    }

    @Override
    public HoaDon capNhatTongTienHoaDonTheoThucTe(DatPhong datPhong,
                                                   List<ChiTietDatPhong> danhSachChiTiet,
                                                   NhanVien nhanVienThaoTac) {
        // Tong tien duoc tinh tu phong + dich vu, sau do moi tru khuyen mai tren hoa don hien tai.
        BigDecimal tongTienTruocKhuyenMai = tinhTongTienDatPhong(danhSachChiTiet)
                .add(tinhTongTienDichVu(datPhong.getId()));

        HoaDon hoaDon = datPhong.getHoaDon();
        if (hoaDon == null) {
            return taoHoaDonMoi(datPhong, nhanVienThaoTac, tongTienTruocKhuyenMai);
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

    @Override
    public String dinhDangTien(BigDecimal soTien) {
        BigDecimal giaTri = soTien == null ? BigDecimal.ZERO : soTien;
        return giaTri.setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    private HoaDon taoHoaDonMoi(DatPhong datPhong, NhanVien nhanVienThaoTac, BigDecimal tongTienTruocKhuyenMai) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setDatPhong(datPhong);
        hoaDon.setNhanVien(nhanVienThaoTac != null ? nhanVienThaoTac : datPhong.getNhanVien());
        hoaDon.setTongTien(tongTienTruocKhuyenMai);
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon = hoaDonRepository.save(hoaDon);

        datPhong.setHoaDon(hoaDon);
        datPhongRepository.save(datPhong);
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
