package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

import java.util.List;

public class TrangDuLieuDto<T> {
    private final List<T> danhSach;
    private final int trangHienTai;
    private final int tongTrang;
    private final int tongBanGhi;
    private final int kichThuocTrang;
    private final boolean coTrangTruoc;
    private final boolean coTrangSau;

    public TrangDuLieuDto(List<T> danhSach,
                          int trangHienTai,
                          int tongTrang,
                          int tongBanGhi,
                          int kichThuocTrang,
                          boolean coTrangTruoc,
                          boolean coTrangSau) {
        this.danhSach = danhSach;
        this.trangHienTai = trangHienTai;
        this.tongTrang = tongTrang;
        this.tongBanGhi = tongBanGhi;
        this.kichThuocTrang = kichThuocTrang;
        this.coTrangTruoc = coTrangTruoc;
        this.coTrangSau = coTrangSau;
    }

    public List<T> getDanhSach() {
        return danhSach;
    }

    public int getTrangHienTai() {
        return trangHienTai;
    }

    public int getTongTrang() {
        return tongTrang;
    }

    public int getTongBanGhi() {
        return tongBanGhi;
    }

    public int getKichThuocTrang() {
        return kichThuocTrang;
    }

    public boolean isCoTrangTruoc() {
        return coTrangTruoc;
    }

    public boolean isCoTrangSau() {
        return coTrangSau;
    }
}
