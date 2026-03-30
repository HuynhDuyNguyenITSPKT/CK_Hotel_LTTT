package hcmute.system.hotel.cknhom11qlhotel.service;

public interface IDichVuService {

    void themDichVuTrongThoiGianO(Long datPhongId,
                                  Long dichVuId,
                                  Integer soLuong,
                                  Long phongId,
                                  boolean apDungTatCaPhong,
                                  Long nhanVienId);

    void xoaDichVuDaThem(Long suDungDichVuId, Long nhanVienId);
}
