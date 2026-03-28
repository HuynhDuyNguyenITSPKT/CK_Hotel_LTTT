package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeTanSuDungDichVuDto {
    private final Long suDungDichVuId;
    private final Long datPhongId;
    private final String maDatPhong;
    private final String tenDichVu;
    private final String soPhong;
    private final Integer soLuong;
    private final BigDecimal donGia;
    private final BigDecimal thanhTien;
    private final LocalDateTime thoiDiem;

    public LeTanSuDungDichVuDto(Long suDungDichVuId,
                                Long datPhongId,
                                String maDatPhong,
                                String tenDichVu,
                                String soPhong,
                                Integer soLuong,
                                BigDecimal donGia,
                                BigDecimal thanhTien,
                                LocalDateTime thoiDiem) {
        this.suDungDichVuId = suDungDichVuId;
        this.datPhongId = datPhongId;
        this.maDatPhong = maDatPhong;
        this.tenDichVu = tenDichVu;
        this.soPhong = soPhong;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.thoiDiem = thoiDiem;
    }

    public Long getDatPhongId() {
        return datPhongId;
    }

    public Long getSuDungDichVuId() {
        return suDungDichVuId;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public LocalDateTime getThoiDiem() {
        return thoiDiem;
    }
}