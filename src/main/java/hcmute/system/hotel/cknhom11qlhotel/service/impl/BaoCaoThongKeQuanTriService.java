package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.DashboardStatsResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PaymentReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RevenueChartResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ThanhToan;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.util.ReportExportUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class BaoCaoThongKeQuanTriService {

    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final KhachHangRepository khachHangRepository;
    private final PhongRepository phongRepository;
    private final DichVuRepository dichVuRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final NhanVienRepository nhanVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final ReportExportUtil reportExportUtil;

    public BaoCaoThongKeQuanTriService(HoaDonRepository hoaDonRepository,
                                 ThanhToanRepository thanhToanRepository,
                                 KhachHangRepository khachHangRepository,
                                 PhongRepository phongRepository,
                                 DichVuRepository dichVuRepository,
                                 KhuyenMaiRepository khuyenMaiRepository,
                                 NhanVienRepository nhanVienRepository,
                                 TaiKhoanRepository taiKhoanRepository,
                                 ReportExportUtil reportExportUtil) {
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.khachHangRepository = khachHangRepository;
        this.phongRepository = phongRepository;
        this.dichVuRepository = dichVuRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.reportExportUtil = reportExportUtil;
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponse getDashboardStats() {
        BigDecimal doanhThu = hoaDonRepository.sumTongTien();
        return new DashboardStatsResponse(
                hoaDonRepository.count(),
                doanhThu == null ? BigDecimal.ZERO : doanhThu,
                khachHangRepository.count(),
                phongRepository.count(),
                dichVuRepository.count(),
                khuyenMaiRepository.count(),
                nhanVienRepository.count(),
                taiKhoanRepository.count()
        );
    }

    @Transactional(readOnly = true)
    public RevenueChartResponse getRevenueChart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        Map<YearMonth, BigDecimal> groupedRevenue = hoaDonRepository.findAllByOrderByNgayTaoAsc().stream()
                .collect(LinkedHashMap::new,
                        (acc, invoice) -> acc.merge(
                                YearMonth.from(invoice.getNgayTao()),
                                invoice.getTongTien(),
                                BigDecimal::add),
                        Map::putAll);

        List<YearMonth> sortedKeys = groupedRevenue.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .toList();

        List<String> labels = sortedKeys.stream().map(formatter::format).toList();
        List<BigDecimal> values = sortedKeys.stream().map(groupedRevenue::get).toList();
        return new RevenueChartResponse(labels, values);
    }

    @Transactional(readOnly = true)
    public List<InvoiceReportResponse> getInvoicesByFilters(Integer day, Integer month, Integer year) {
        Predicate<HoaDon> filter = buildInvoiceFilter(day, month, year);
        return hoaDonRepository.findAllByOrderByNgayTaoDesc().stream()
                .filter(filter)
                .map(this::toInvoiceReport)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PaymentReportResponse> getPaymentsByFilters(Integer day, Integer month, Integer year) {
        Predicate<ThanhToan> filter = buildPaymentFilter(day, month, year);
        return thanhToanRepository.findAllByOrderByNgayThanhToanDesc().stream()
                .filter(filter)
                .map(this::toPaymentReport)
                .toList();
    }

    @Transactional(readOnly = true)
    public byte[] exportInvoicesExcel(Integer day, Integer month, Integer year) {
        List<InvoiceReportResponse> rows = getInvoicesByFilters(day, month, year);
        return reportExportUtil.exportToExcel(
                "hoa_don",
                List.of("Ma hoa don", "Ma dat phong", "Nhan vien", "Tong tien", "Ngay tao"),
                rows.stream().map(this::invoiceRow).toList());
    }

    @Transactional(readOnly = true)
    public byte[] exportPaymentsExcel(Integer day, Integer month, Integer year) {
        List<PaymentReportResponse> rows = getPaymentsByFilters(day, month, year);
        return reportExportUtil.exportToExcel(
                "thanh_toan",
                List.of("Ma thanh toan", "Ma hoa don", "Phuong thuc", "So tien", "Ngay thanh toan"),
                rows.stream().map(this::paymentRow).toList());
    }

    @Transactional(readOnly = true)
    public byte[] exportInvoicesPdf(Integer day, Integer month, Integer year) {
        List<InvoiceReportResponse> rows = getInvoicesByFilters(day, month, year);
        return reportExportUtil.exportToPdf(
                "Bao cao hoa don",
                List.of("Ma hoa don", "Ma dat phong", "Nhan vien", "Tong tien", "Ngay tao"),
                rows.stream().map(this::invoiceRow).toList());
    }

    @Transactional(readOnly = true)
    public byte[] exportPaymentsPdf(Integer day, Integer month, Integer year) {
        List<PaymentReportResponse> rows = getPaymentsByFilters(day, month, year);
        return reportExportUtil.exportToPdf(
                "Bao cao thanh toan",
                List.of("Ma thanh toan", "Ma hoa don", "Phuong thuc", "So tien", "Ngay thanh toan"),
                rows.stream().map(this::paymentRow).toList());
    }

    private InvoiceReportResponse toInvoiceReport(HoaDon hoaDon) {
        return new InvoiceReportResponse(
                hoaDon.getId(),
                hoaDon.getDatPhong() != null ? hoaDon.getDatPhong().getId() : null,
                hoaDon.getNhanVien() != null ? hoaDon.getNhanVien().getTen() : "",
                hoaDon.getTongTien(),
                hoaDon.getNgayTao());
    }

    private PaymentReportResponse toPaymentReport(ThanhToan thanhToan) {
        return new PaymentReportResponse(
                thanhToan.getId(),
                thanhToan.getHoaDon() != null ? thanhToan.getHoaDon().getId() : null,
                thanhToan.getPhuongThuc(),
                thanhToan.getSoTien(),
                thanhToan.getNgayThanhToan());
    }

    private Predicate<HoaDon> buildInvoiceFilter(Integer day, Integer month, Integer year) {
        return invoice -> {
            LocalDate date = invoice.getNgayTao().toLocalDate();
            boolean yearOk = year == null || date.getYear() == year;
            boolean monthOk = month == null || date.getMonthValue() == month;
            boolean dayOk = day == null || date.getDayOfMonth() == day;
            return yearOk && monthOk && dayOk;
        };
    }

    private Predicate<ThanhToan> buildPaymentFilter(Integer day, Integer month, Integer year) {
        return payment -> {
            LocalDate date = payment.getNgayThanhToan().toLocalDate();
            boolean yearOk = year == null || date.getYear() == year;
            boolean monthOk = month == null || date.getMonthValue() == month;
            boolean dayOk = day == null || date.getDayOfMonth() == day;
            return yearOk && monthOk && dayOk;
        };
    }

    private List<String> invoiceRow(InvoiceReportResponse row) {
        return List.of(
                String.valueOf(row.getHoaDonId()),
                row.getDatPhongId() == null ? "" : String.valueOf(row.getDatPhongId()),
                row.getNhanVien() == null ? "" : row.getNhanVien(),
                row.getTongTien() == null ? "0" : row.getTongTien().toPlainString(),
                row.getNgayTao() == null ? "" : row.getNgayTao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        );
    }

    private List<String> paymentRow(PaymentReportResponse row) {
        return List.of(
                String.valueOf(row.getThanhToanId()),
                row.getHoaDonId() == null ? "" : String.valueOf(row.getHoaDonId()),
                row.getPhuongThuc() == null ? "" : row.getPhuongThuc().name(),
                row.getSoTien() == null ? "0" : row.getSoTien().toPlainString(),
                row.getNgayThanhToan() == null ? "" : row.getNgayThanhToan().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        );
    }
}
