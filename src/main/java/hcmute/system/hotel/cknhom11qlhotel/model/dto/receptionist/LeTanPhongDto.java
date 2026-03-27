package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;

import java.math.BigDecimal;

public class LeTanPhongDto {
    private final Long phongId;
    private final String soPhong;
    private final String tenLoaiPhong;
    private final BigDecimal giaCoBan;
    private final RoomStatus trangThai;
    private final String imageUrl;

    public LeTanPhongDto(Long phongId,
                         String soPhong,
                         String tenLoaiPhong,
                         BigDecimal giaCoBan,
                         RoomStatus trangThai,
                         String imageUrl) {
        this.phongId = phongId;
        this.soPhong = soPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.giaCoBan = giaCoBan;
        this.trangThai = trangThai;
        this.imageUrl = imageUrl;
    }

    public Long getPhongId() {
        return phongId;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public BigDecimal getGiaCoBan() {
        return giaCoBan;
    }

    public RoomStatus getTrangThai() {
        return trangThai;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
