package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;

import java.time.LocalDate;

public class LeTanCheckInDto {
    private final Long datPhongId;
    private final String maDatPhong;
    private final String tenKhachHang;
    private final String soDienThoai;
    private final String soPhong;
    private final LocalDate ngayNhan;
    private final LocalDate ngayTra;
    private final BookingStatus trangThai;

    public LeTanCheckInDto(Long datPhongId,
                           String maDatPhong,
                           String tenKhachHang,
                           String soDienThoai,
                           String soPhong,
                           LocalDate ngayNhan,
                           LocalDate ngayTra,
                           BookingStatus trangThai) {
        this.datPhongId = datPhongId;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.soPhong = soPhong;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
    }

    public Long getDatPhongId() {
        return datPhongId;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public BookingStatus getTrangThai() {
        return trangThai;
    }
}
