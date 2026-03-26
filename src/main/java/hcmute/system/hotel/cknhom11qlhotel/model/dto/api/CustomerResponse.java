package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

public class CustomerResponse {
    private Long id;
    private String ten;
    private String sdt;
    private String email;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String ten, String sdt, String email) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
