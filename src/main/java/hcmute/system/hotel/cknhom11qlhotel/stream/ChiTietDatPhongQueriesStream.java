package hcmute.system.hotel.cknhom11qlhotel.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.ChiTietDatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ChiTietDatPhongQueriesStream {

    private final ObjectStreamSupport objectStreamSupport;

    public ChiTietDatPhongQueriesStream(ObjectStreamSupport objectStreamSupport) {
        this.objectStreamSupport = objectStreamSupport;
    }

    public Map<Long, ChiTietDatPhong> mapChiTietMoiNhatTheoDatPhong(List<Object> duLieu) {
        Map<Long, ChiTietDatPhong> ketQua = new HashMap<>();
        objectStreamSupport.streamTheoLoai(duLieu, ChiTietDatPhong.class)
                .forEach(chiTiet -> {
                    DatPhong datPhong = chiTiet.getDatPhong();
                    if (datPhong == null || datPhong.getId() == null) {
                        return;
                    }
                    ChiTietDatPhong hienTai = ketQua.get(datPhong.getId());
                    if (hienTai == null || soSanhChiTiet(chiTiet, hienTai) > 0) {
                        ketQua.put(datPhong.getId(), chiTiet);
                    }
                });
        return ketQua;
    }

    public Map<Long, List<ChiTietDatPhong>> mapDanhSachTheoDatPhongId(List<Object> duLieu) {
        Map<Long, List<ChiTietDatPhong>> ketQua = new HashMap<>();

        objectStreamSupport.streamTheoLoai(duLieu, ChiTietDatPhong.class)
                .forEach(chiTiet -> {
                    DatPhong datPhong = chiTiet.getDatPhong();
                    if (datPhong == null || datPhong.getId() == null) {
                        return;
                    }
                    ketQua.computeIfAbsent(datPhong.getId(), key -> new ArrayList<>()).add(chiTiet);
                });

        ketQua.values().forEach(danhSach -> danhSach.sort(Comparator
            .comparing(this::ngayNhanCuaChiTiet, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(ChiTietDatPhong::getId, Comparator.nullsLast(Comparator.naturalOrder()))));

        return ketQua;
    }

    public List<ChiTietDatPhong> locDanhSachTheoDatPhongId(List<Object> duLieu, Long datPhongId) {
        if (datPhongId == null) {
            return List.of();
        }
        return objectStreamSupport.streamTheoLoai(duLieu, ChiTietDatPhong.class)
                .filter(chiTiet -> chiTiet.getDatPhong() != null
                        && chiTiet.getDatPhong().getId() != null
                        && chiTiet.getDatPhong().getId().equals(datPhongId))
                .sorted(Comparator
                    .comparing(this::ngayNhanCuaChiTiet, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(ChiTietDatPhong::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    public List<ChiTietDatPhong> locTheoNgayHieuLuc(List<ChiTietDatPhong> danhSachChiTiet, LocalDate ngay) {
        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty() || ngay == null) {
            return List.of();
        }

        return danhSachChiTiet.stream()
            .filter(chiTiet -> ngayNhanCuaChiTiet(chiTiet) != null
                && ngayTraCuaChiTiet(chiTiet) != null
                && !ngay.isBefore(ngayNhanCuaChiTiet(chiTiet))
                && ngay.isBefore(ngayTraCuaChiTiet(chiTiet)))
                .sorted(Comparator
                .comparing(this::ngayNhanCuaChiTiet, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(ChiTietDatPhong::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    public List<ChiTietDatPhong> danhSachOrEmpty(Map<Long, List<ChiTietDatPhong>> mapChiTietTheoDatPhong, Long datPhongId) {
        if (mapChiTietTheoDatPhong == null || datPhongId == null) {
            return List.of();
        }
        return mapChiTietTheoDatPhong.getOrDefault(datPhongId, Collections.emptyList());
    }

    public List<ChiTietDatPhong> locTheoNgayNhan(List<Object> duLieu, LocalDate ngay) {
        return objectStreamSupport.streamTheoLoai(duLieu, ChiTietDatPhong.class)
                .filter(chiTiet -> ngay != null && ngay.equals(ngayNhanCuaChiTiet(chiTiet)))
                .toList();
    }

    public boolean coLichTrung(List<Object> duLieu,
                               Long phongId,
                               LocalDate ngayNhanMoi,
                               LocalDate ngayTraMoi,
                               Set<BookingStatus> tapTrangThaiBoQua) {
        return objectStreamSupport.streamTheoLoai(duLieu, ChiTietDatPhong.class)
                .filter(chiTiet -> chiTiet.getPhong() != null
                        && chiTiet.getPhong().getId() != null
                        && chiTiet.getPhong().getId().equals(phongId))
                .filter(chiTiet -> {
                    DatPhong datPhong = chiTiet.getDatPhong();
                    return datPhong != null && !tapTrangThaiBoQua.contains(datPhong.getTrangThai());
                })
                .anyMatch(chiTiet -> giaoNgay(ngayNhanCuaChiTiet(chiTiet), ngayTraCuaChiTiet(chiTiet), ngayNhanMoi, ngayTraMoi));
    }

    public Optional<ChiTietDatPhong> timTheoDatPhongId(List<Object> duLieu, Long datPhongId) {
        return objectStreamSupport.streamTheoLoai(duLieu, ChiTietDatPhong.class)
                .filter(chiTiet -> chiTiet.getDatPhong() != null
                        && chiTiet.getDatPhong().getId() != null
                        && chiTiet.getDatPhong().getId().equals(datPhongId))
                .max(Comparator.comparing(ChiTietDatPhong::getId, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    private int soSanhChiTiet(ChiTietDatPhong a, ChiTietDatPhong b) {
        Long idA = a.getId();
        Long idB = b.getId();
        if (idA == null && idB == null) {
            return 0;
        }
        if (idA == null) {
            return -1;
        }
        if (idB == null) {
            return 1;
        }
        return idA.compareTo(idB);
    }

    private LocalDate ngayNhanCuaChiTiet(ChiTietDatPhong chiTietDatPhong) {
        if (chiTietDatPhong == null || chiTietDatPhong.getDatPhong() == null) {
            return null;
        }
        return chiTietDatPhong.getDatPhong().getNgayNhan();
    }

    private LocalDate ngayTraCuaChiTiet(ChiTietDatPhong chiTietDatPhong) {
        if (chiTietDatPhong == null || chiTietDatPhong.getDatPhong() == null) {
            return null;
        }
        return chiTietDatPhong.getDatPhong().getNgayTra();
    }

    private boolean giaoNgay(LocalDate ngayNhanA,
                             LocalDate ngayTraA,
                             LocalDate ngayNhanB,
                             LocalDate ngayTraB) {
        if (ngayNhanA == null || ngayTraA == null || ngayNhanB == null || ngayTraB == null) {
            return false;
        }
        return !(ngayTraA.isBefore(ngayNhanB) || ngayTraB.isBefore(ngayNhanA));
    }
}
