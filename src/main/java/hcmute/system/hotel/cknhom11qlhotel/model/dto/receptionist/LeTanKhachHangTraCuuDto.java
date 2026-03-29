package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

public class LeTanKhachHangTraCuuDto {
    private final Long khachHangId;
    private final String tenKhachHang;
    private final String sdt;
    private final String email;

    public LeTanKhachHangTraCuuDto(Long khachHangId, String tenKhachHang, String sdt, String email) {
        this.khachHangId = khachHangId;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.email = email;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }
}
