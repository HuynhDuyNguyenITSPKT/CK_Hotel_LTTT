package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomRequest {

    @NotBlank(message = "Số phòng không được để trống")
    private String soPhong;

    private String imageUrl;

    @NotNull(message = "Trạng thái phòng không được để trống")
    private RoomStatus trangThai;

    @NotNull(message = "Loại phòng không được để trống")
    private Long loaiPhongId;

    public RoomRequest() {
    }

    public RoomRequest(String soPhong, String imageUrl, RoomStatus trangThai, Long loaiPhongId) {
        this.soPhong = soPhong;
        this.imageUrl = imageUrl;
        this.trangThai = trangThai;
        this.loaiPhongId = loaiPhongId;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RoomStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(RoomStatus trangThai) {
        this.trangThai = trangThai;
    }

    public Long getLoaiPhongId() {
        return loaiPhongId;
    }

    public void setLoaiPhongId(Long loaiPhongId) {
        this.loaiPhongId = loaiPhongId;
    }
}
