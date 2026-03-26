package hcmute.system.hotel.cknhom11qlhotel.model.enity;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phong")
public class Phong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "so_phong", nullable = false, unique = true, length = 30)
    private String soPhong;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false, length = 30)
    private RoomStatus trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_phong_id", nullable = false)
    private LoaiPhong loaiPhong;

    @OneToMany(mappedBy = "phong")
    private List<ChiTietDatPhong> chiTietDatPhongs = new ArrayList<>();

    @OneToMany(mappedBy = "phong")
    private List<SuDungDichVu> suDungDichVus = new ArrayList<>();

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

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public List<ChiTietDatPhong> getChiTietDatPhongs() {
        return chiTietDatPhongs;
    }

    public void setChiTietDatPhongs(List<ChiTietDatPhong> chiTietDatPhongs) {
        this.chiTietDatPhongs = chiTietDatPhongs;
    }

    public List<SuDungDichVu> getSuDungDichVus() {
        return suDungDichVus;
    }

    public void setSuDungDichVus(List<SuDungDichVu> suDungDichVus) {
        this.suDungDichVus = suDungDichVus;
    }
}

