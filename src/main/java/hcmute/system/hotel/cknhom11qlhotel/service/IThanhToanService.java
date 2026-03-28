package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;

import java.math.BigDecimal;

public interface IThanhToanService {

    void thucHienThanhToan(Long datPhongId,
                           BigDecimal soTienThanhToan,
                           PaymentMethod phuongThuc,
                           Long nhanVienId);

    void apDungMaKhuyenMai(Long datPhongId, String maKhuyenMai, Long nhanVienId);

    void apDungKhuyenMaiVaThanhToan(Long datPhongId,
                                    String maKhuyenMai,
                                    BigDecimal soTienThanhToan,
                                    PaymentMethod phuongThuc,
                                    Long nhanVienId);
}
