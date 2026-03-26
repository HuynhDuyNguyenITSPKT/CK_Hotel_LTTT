package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import java.math.BigDecimal;

public record DashboardStatsResponse(
        long totalHoaDon,
        BigDecimal tongDoanhThu,
        long totalKhachHang,
        long totalPhong,
        long totalDichVu,
        long totalKhuyenMai,
        long totalNhanVien,
        long totalTaiKhoan
) {
}

