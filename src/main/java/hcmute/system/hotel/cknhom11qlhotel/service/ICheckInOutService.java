package hcmute.system.hotel.cknhom11qlhotel.service;

public interface ICheckInOutService {

    void thucHienCheckIn(Long datPhongId, Long nhanVienId);

    void thucHienCheckOut(Long datPhongId, Long nhanVienId);
}
