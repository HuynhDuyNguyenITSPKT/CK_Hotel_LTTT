package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PaymentReportResponse;

import java.util.List;

public interface IManagerReportService {

    List<InvoiceReportResponse> layBaoCaoHoaDon(Integer day, Integer month, Integer year);

    List<PaymentReportResponse> layBaoCaoThanhToan(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoHoaDonExcel(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoThanhToanExcel(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoHoaDonPdf(Integer day, Integer month, Integer year);

    byte[] xuatBaoCaoThanhToanPdf(Integer day, Integer month, Integer year);
}
