package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PaymentReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.service.IAdminManagementService;
import hcmute.system.hotel.cknhom11qlhotel.service.IManagerReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ManagerReportServiceImpl implements IManagerReportService {

    private final IAdminManagementService adminManagementService;

    public ManagerReportServiceImpl(IAdminManagementService adminManagementService) {
        this.adminManagementService = adminManagementService;
    }

    @Override
    public List<InvoiceReportResponse> layBaoCaoHoaDon(Integer day, Integer month, Integer year) {
        return adminManagementService.getInvoicesByFilters(day, month, year);
    }

    @Override
    public List<PaymentReportResponse> layBaoCaoThanhToan(Integer day, Integer month, Integer year) {
        return adminManagementService.getPaymentsByFilters(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoHoaDonExcel(Integer day, Integer month, Integer year) {
        return adminManagementService.exportInvoicesExcel(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoThanhToanExcel(Integer day, Integer month, Integer year) {
        return adminManagementService.exportPaymentsExcel(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoHoaDonPdf(Integer day, Integer month, Integer year) {
        return adminManagementService.exportInvoicesPdf(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoThanhToanPdf(Integer day, Integer month, Integer year) {
        return adminManagementService.exportPaymentsPdf(day, month, year);
    }
}
