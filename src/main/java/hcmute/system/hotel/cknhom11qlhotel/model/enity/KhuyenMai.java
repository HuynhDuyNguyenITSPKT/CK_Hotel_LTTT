package hcmute.system.hotel.cknhom11qlhotel.model.enity;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "khuyen_mai")
public class KhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten", nullable = false, length = 120)
    private String ten;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_giam", nullable = false, length = 20)
    private DiscountType loaiGiam;

    @Column(name = "gia_tri", nullable = false, precision = 15, scale = 2)
    private BigDecimal giaTri;

    @ManyToMany(mappedBy = "khuyenMais")
    private Set<HoaDon> hoaDons = new HashSet<>();

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

    public DiscountType getLoaiGiam() {
        return loaiGiam;
    }

    public void setLoaiGiam(DiscountType loaiGiam) {
        this.loaiGiam = loaiGiam;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public Set<HoaDon> getHoaDons() {
        return hoaDons;
    }

    public void setHoaDons(Set<HoaDon> hoaDons) {
        this.hoaDons = hoaDons;
    }
}

