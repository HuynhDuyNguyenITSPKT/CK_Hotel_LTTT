package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeTanChiTietDatPhongDto {
    private final Long chiTietDatPhongId;
    private final Long datPhongId;
    private final String maDatPhong;
    private final String soPhong;
    private final BigDecimal gia;
    private final LocalDate ngayNhan;
    private final LocalDate ngayTra;

    public LeTanChiTietDatPhongDto(Long chiTietDatPhongId,
                                   Long datPhongId,
                                   String maDatPhong,
                                   String soPhong,
                                   BigDecimal gia,
                                   LocalDate ngayNhan,
                                   LocalDate ngayTra) {
        this.chiTietDatPhongId = chiTietDatPhongId;
        this.datPhongId = datPhongId;
        this.maDatPhong = maDatPhong;
        this.soPhong = soPhong;
        this.gia = gia;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
    }

    public Long getChiTietDatPhongId() {
        return chiTietDatPhongId;
    }

    public Long getDatPhongId() {
        return datPhongId;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }
}
