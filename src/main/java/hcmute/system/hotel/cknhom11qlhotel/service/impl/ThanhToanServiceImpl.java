package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import hcmute.system.hotel.cknhom11qlhotel.service.IThanhToanService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ThanhToanServiceImpl implements IThanhToanService {

    private final IReceptionistDashboardService receptionistDashboardService;

    public ThanhToanServiceImpl(IReceptionistDashboardService receptionistDashboardService) {
        this.receptionistDashboardService = receptionistDashboardService;
    }

    @Override
    public void thucHienThanhToan(Long datPhongId,
                                  BigDecimal soTienThanhToan,
                                  PaymentMethod phuongThuc,
                                  Long nhanVienId) {
        receptionistDashboardService.thucHienThanhToan(datPhongId, soTienThanhToan, phuongThuc, nhanVienId);
    }

    @Override
    public void apDungMaKhuyenMai(Long datPhongId, String maKhuyenMai, Long nhanVienId) {
        receptionistDashboardService.apDungMaKhuyenMai(datPhongId, maKhuyenMai, nhanVienId);
    }

    @Override
    public void apDungKhuyenMaiVaThanhToan(Long datPhongId,
                                           String maKhuyenMai,
                                           BigDecimal soTienThanhToan,
                                           PaymentMethod phuongThuc,
                                           Long nhanVienId) {
        receptionistDashboardService.apDungKhuyenMaiVaThanhToan(datPhongId, maKhuyenMai, soTienThanhToan, phuongThuc, nhanVienId);
    }
}
