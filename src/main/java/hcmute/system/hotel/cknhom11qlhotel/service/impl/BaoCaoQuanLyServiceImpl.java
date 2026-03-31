package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PaymentReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.service.IBaoCaoQuanLyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BaoCaoQuanLyServiceImpl implements IBaoCaoQuanLyService {

    private final BaoCaoTongHopService baoCaoTongHopService;

    public BaoCaoQuanLyServiceImpl(BaoCaoTongHopService baoCaoTongHopService) {
        this.baoCaoTongHopService = baoCaoTongHopService;
    }

    @Override
    public List<InvoiceReportResponse> layBaoCaoHoaDon(Integer day, Integer month, Integer year) {
        return baoCaoTongHopService.layBaoCaoHoaDon(day, month, year);
    }

    @Override
    public List<PaymentReportResponse> layBaoCaoThanhToan(Integer day, Integer month, Integer year) {
        return baoCaoTongHopService.layBaoCaoThanhToan(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoHoaDonExcel(Integer day, Integer month, Integer year) {
        return baoCaoTongHopService.xuatBaoCaoHoaDonExcel(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoThanhToanExcel(Integer day, Integer month, Integer year) {
        return baoCaoTongHopService.xuatBaoCaoThanhToanExcel(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoHoaDonPdf(Integer day, Integer month, Integer year) {
        return baoCaoTongHopService.xuatBaoCaoHoaDonPdf(day, month, year);
    }

    @Override
    public byte[] xuatBaoCaoThanhToanPdf(Integer day, Integer month, Integer year) {
        return baoCaoTongHopService.xuatBaoCaoThanhToanPdf(day, month, year);
    }
}
