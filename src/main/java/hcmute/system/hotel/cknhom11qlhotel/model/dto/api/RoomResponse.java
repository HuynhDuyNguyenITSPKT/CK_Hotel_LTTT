package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;

public class RoomResponse {
    private Long id;
    private String soPhong;
    private RoomStatus trangThai;
    private String imageUrl;
    private Long loaiPhongId;
    private String tenLoaiPhong;
    private String moTaLoaiPhong;

    public RoomResponse() {
    }

    public RoomResponse(Long id, String soPhong, RoomStatus trangThai, String imageUrl, Long loaiPhongId,
                        String tenLoaiPhong, String moTaLoaiPhong) {
        this.id = id;
        this.soPhong = soPhong;
        this.trangThai = trangThai;
        this.imageUrl = imageUrl;
        this.loaiPhongId = loaiPhongId;
        this.tenLoaiPhong = tenLoaiPhong;
        this.moTaLoaiPhong = moTaLoaiPhong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public RoomStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(RoomStatus trangThai) {
        this.trangThai = trangThai;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getLoaiPhongId() {
        return loaiPhongId;
    }

    public void setLoaiPhongId(Long loaiPhongId) {
        this.loaiPhongId = loaiPhongId;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public String getMoTaLoaiPhong() {
        return moTaLoaiPhong;
    }

    public void setMoTaLoaiPhong(String moTaLoaiPhong) {
        this.moTaLoaiPhong = moTaLoaiPhong;
    }
}
