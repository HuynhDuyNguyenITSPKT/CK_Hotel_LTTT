package hcmute.system.hotel.cknhom11qlhotel.model.entity;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dat_phong")
public class DatPhong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhan_vien_id", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "ngay_dat", nullable = false)
    private LocalDateTime ngayDat;

    @Column(name = "ngay_nhan")
    private LocalDate ngayNhan;

    @Column(name = "ngay_tra")
    private LocalDate ngayTra;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false, length = 30)
    private BookingStatus trangThai;

    @OneToMany(mappedBy = "datPhong")
    private List<ChiTietDatPhong> chiTietDatPhongs = new ArrayList<>();

    @OneToMany(mappedBy = "datPhong")
    private List<SuDungDichVu> suDungDichVus = new ArrayList<>();

    @OneToOne(mappedBy = "datPhong")
    private HoaDon hoaDon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(LocalDate ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public BookingStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(BookingStatus trangThai) {
        this.trangThai = trangThai;
    }

    public List<ChiTietDatPhong> getChiTietDatPhongs() {
        return chiTietDatPhongs;
    }

    public void setChiTietDatPhongs(List<ChiTietDatPhong> chiTietDatPhongs) {
        this.chiTietDatPhongs = chiTietDatPhongs;
    }

    public List<SuDungDichVu> getSuDungDichVus() {
        return suDungDichVus;
    }

    public void setSuDungDichVus(List<SuDungDichVu> suDungDichVus) {
        this.suDungDichVus = suDungDichVus;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }
}

