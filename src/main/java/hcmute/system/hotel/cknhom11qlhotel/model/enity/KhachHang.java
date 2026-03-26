package hcmute.system.hotel.cknhom11qlhotel.model.enity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten", nullable = false, length = 120)
    private String ten;

    @Column(name = "sdt", nullable = false, unique = true, length = 20)
    private String sdt;

    @Column(name = "email", unique = true, length = 120)
    private String email;

    @OneToMany(mappedBy = "khachHang")
    private List<DatPhong> datPhongs = new ArrayList<>();

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

    public List<DatPhong> getDatPhongs() {
        return datPhongs;
    }

    public void setDatPhongs(List<DatPhong> datPhongs) {
        this.datPhongs = datPhongs;
    }
}

