package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequest {

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String ten;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String sdt;

    @Email(message = "Email không hợp lệ")
    private String email;

    public CustomerRequest() {
    }

    public CustomerRequest(String ten, String sdt, String email) {
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
