package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PaymentReportResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BaoCaoTongHopService {

    private final BaoCaoThongKeQuanTriService adminAnalyticsService;

    public BaoCaoTongHopService(BaoCaoThongKeQuanTriService adminAnalyticsService) {
        this.adminAnalyticsService = adminAnalyticsService;
    }

    public List<InvoiceReportResponse> layBaoCaoHoaDon(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.getInvoicesByFilters(day, month, year);
    }

    public List<PaymentReportResponse> layBaoCaoThanhToan(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.getPaymentsByFilters(day, month, year);
    }

    public byte[] xuatBaoCaoHoaDonExcel(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportInvoicesExcel(day, month, year);
    }

    public byte[] xuatBaoCaoThanhToanExcel(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportPaymentsExcel(day, month, year);
    }

    public byte[] xuatBaoCaoHoaDonPdf(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportInvoicesPdf(day, month, year);
    }

    public byte[] xuatBaoCaoThanhToanPdf(Integer day, Integer month, Integer year) {
        return adminAnalyticsService.exportPaymentsPdf(day, month, year);
    }
}
