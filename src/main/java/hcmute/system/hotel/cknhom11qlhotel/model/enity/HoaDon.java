package hcmute.system.hotel.cknhom11qlhotel.model.enity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hoa_don")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dat_phong_id", nullable = false, unique = true)
    private DatPhong datPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhan_vien_id", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "tong_tien", nullable = false, precision = 15, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @OneToMany(mappedBy = "hoaDon")
    private List<ThanhToan> thanhToans = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "hoa_don_khuyen_mai",
            joinColumns = @JoinColumn(name = "hoa_don_id"),
            inverseJoinColumns = @JoinColumn(name = "khuyen_mai_id")
    )
    private Set<KhuyenMai> khuyenMais = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DatPhong getDatPhong() {
        return datPhong;
    }

    public void setDatPhong(DatPhong datPhong) {
        this.datPhong = datPhong;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public List<ThanhToan> getThanhToans() {
        return thanhToans;
    }

    public void setThanhToans(List<ThanhToan> thanhToans) {
        this.thanhToans = thanhToans;
    }

    public Set<KhuyenMai> getKhuyenMais() {
        return khuyenMais;
    }

    public void setKhuyenMais(Set<KhuyenMai> khuyenMais) {
        this.khuyenMais = khuyenMais;
    }
}

