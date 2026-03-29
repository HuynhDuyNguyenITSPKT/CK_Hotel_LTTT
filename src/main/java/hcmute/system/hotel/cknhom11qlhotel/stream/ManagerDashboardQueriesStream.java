package hcmute.system.hotel.cknhom11qlhotel.stream;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.DatPhong;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ManagerDashboardQueriesStream {

    public long demKhachHangDuyNhat(List<DatPhong> danhSachDatPhong) {
        return danhSachDatPhong.stream()
                .map(this::toKhachHangId)
                .filter(this::isKhachHangIdHopLe)
                .distinct()
                .count();
    }

    public long demSoPhongDatTheoNgay(List<DatPhong> danhSachDatPhong, LocalDate ngayMoc) {
        return danhSachDatPhong.stream()
                .filter(Objects::nonNull)
                .filter(this::coNgayDat)
                .filter(datPhong -> datPhong.getNgayDat().toLocalDate().isEqual(ngayMoc))
                .mapToLong(this::demSoPhongTrongDatPhong)
                .sum();
    }

    public Map<LocalDate, Long> mapSoPhongDatTheoNgay(List<DatPhong> danhSachDatPhong) {
        return danhSachDatPhong.stream()
                .filter(this::coNgayDat)
                .collect(Collectors.groupingBy(
                        datPhong -> datPhong.getNgayDat().toLocalDate(),
                        Collectors.summingLong(this::demSoPhongTrongDatPhong)
                ));
    }

        public Map<LocalDate, Long> mapSoKhachDatTheoNgayNhan(List<DatPhong> danhSachDatPhong) {
        return danhSachDatPhong.stream()
            .filter(datPhong -> datPhong != null && datPhong.getNgayNhan() != null)
            .collect(Collectors.groupingBy(DatPhong::getNgayNhan, Collectors.counting()));
        }

    private Long toKhachHangId(DatPhong datPhong) {
        if (datPhong == null || datPhong.getKhachHang() == null || datPhong.getKhachHang().getId() == null) {
            return -1L;
        }
        return datPhong.getKhachHang().getId();
    }

    private boolean isKhachHangIdHopLe(Long khachHangId) {
        return khachHangId != null && khachHangId > 0;
    }

    private boolean coNgayDat(DatPhong datPhong) {
        return datPhong != null && datPhong.getNgayDat() != null;
    }

    private long demSoPhongTrongDatPhong(DatPhong datPhong) {
        return datPhong.getChiTietDatPhongs() == null ? 0L : datPhong.getChiTietDatPhongs().size();
    }
}
