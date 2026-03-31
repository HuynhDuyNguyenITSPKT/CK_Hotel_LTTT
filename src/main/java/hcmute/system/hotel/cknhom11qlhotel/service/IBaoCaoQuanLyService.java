package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PaymentReportResponse;

import java.util.List;

public interface IBaoCaoQuanLyService {

    List<InvoiceReportResponse> layBaoCaoHoaDon(Integer day, Integer month, Integer year);

    List<PaymentReportResponse> layBaoCaoThanhToan(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoHoaDonExcel(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoThanhToanExcel(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoHoaDonPdf(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoThanhToanPdf(Integer day, Integer month, Integer year);
}
