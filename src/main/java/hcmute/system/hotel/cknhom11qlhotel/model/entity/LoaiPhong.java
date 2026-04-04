package hcmute.system.hotel.cknhom11qlhotel.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loai_phong")
public class LoaiPhong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_loai", nullable = false, length = 120)
    private String tenLoai;

    @Column(name = "mo_ta", length = 1000)
    private String moTa;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "gia_co_ban", nullable = false, precision = 15, scale = 2)
    private BigDecimal giaCoBan;

    @OneToMany(mappedBy = "loaiPhong")
    private List<Phong> phongList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(BigDecimal giaCoBan) {
        this.giaCoBan = giaCoBan;
    }

    public List<Phong> getPhongList() {
        return phongList;
    }

    public void setPhongList(List<Phong> phongList) {
        this.phongList = phongList;
    }
}

