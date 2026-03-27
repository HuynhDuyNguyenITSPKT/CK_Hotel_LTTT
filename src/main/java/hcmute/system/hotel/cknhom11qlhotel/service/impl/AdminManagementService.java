package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.DashboardStatsResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.InvoiceReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PaymentReportResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RevenueChartResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomTypeRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.ThanhToan;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.repository.DichVuRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.HoaDonRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhachHangRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.KhuyenMaiRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.LoaiPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.ThanhToanRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.CloudinaryUploadService;
import hcmute.system.hotel.cknhom11qlhotel.service.IAdminManagementService;
import hcmute.system.hotel.cknhom11qlhotel.util.ReportExportUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
public class AdminManagementService implements IAdminManagementService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;
    private final PhongRepository phongRepository;
    private final LoaiPhongRepository loaiPhongRepository;
    private final DichVuRepository dichVuRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final KhachHangRepository khachHangRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final ReportExportUtil reportExportUtil;
    private final CloudinaryUploadService cloudinaryUploadService;

    public AdminManagementService(TaiKhoanRepository taiKhoanRepository,
                                  NhanVienRepository nhanVienRepository,
                                  PhongRepository phongRepository,
                                  LoaiPhongRepository loaiPhongRepository,
                                  DichVuRepository dichVuRepository,
                                  KhuyenMaiRepository khuyenMaiRepository,
                                  KhachHangRepository khachHangRepository,
                                  HoaDonRepository hoaDonRepository,
                                  ThanhToanRepository thanhToanRepository,
                                  ReportExportUtil reportExportUtil,
                                  CloudinaryUploadService cloudinaryUploadService) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.phongRepository = phongRepository;
        this.loaiPhongRepository = loaiPhongRepository;
        this.dichVuRepository = dichVuRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.khachHangRepository = khachHangRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.reportExportUtil = reportExportUtil;
        this.cloudinaryUploadService = cloudinaryUploadService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminAccountView> getAccounts() {
        return taiKhoanRepository.findAllByOrderByIdDesc().stream()
                .map(t -> new AdminAccountView(
                        t.getId(),
                        t.getUsername(),
                        t.getEmail(),
                        t.getTrangThai(),
                        t.getNhanVien() != null ? t.getNhanVien().getTen() : "",
                        t.getNhanVien() != null ? t.getNhanVien().getRole() : null
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminEmployeeView> getEmployees() {
        return nhanVienRepository.findAllWithTaiKhoan().stream()
                .map(n -> new AdminEmployeeView(
                        n.getId(),
                        n.getTen(),
                        n.getRole(),
                        n.getTaiKhoan().getId(),
                        n.getTaiKhoan().getUsername(),
                        n.getTaiKhoan().getEmail(),
                        n.getTaiKhoan().getTrangThai()
                ))
                .toList();
    }

    @Override
    @Transactional
    public void createEmployeeWithAccount(CreateEmployeeForm form) {
        validateCreateForm(form);

        String username = form.getUsername().trim();
        String email = form.getEmail().trim();

        if (taiKhoanRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }
        if (taiKhoanRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setUsername(username);
        taiKhoan.setEmail(email);
        taiKhoan.setPassword(form.getPassword().trim());
        taiKhoan.setTrangThai(AccountStatus.ACTIVE);
        taiKhoanRepository.save(taiKhoan);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen(form.getEmployeeName().trim());
        nhanVien.setRole(form.getRole());
        nhanVien.setTaiKhoan(taiKhoan);
        nhanVienRepository.save(nhanVien);
    }

    @Override
    @Transactional
    public void updateAccountStatus(Long accountId, AccountStatus status) {
        if (accountId == null || status == null) {
            throw new IllegalArgumentException("Dữ liệu cập nhật không hợp lệ");
        }

        TaiKhoan taiKhoan = taiKhoanRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));
        taiKhoan.setTrangThai(status);
    }

    @Override
    @Transactional
    public void updateEmployeeRole(Long employeeId, EmployeeRole role) {
        if (employeeId == null || role == null) {
            throw new IllegalArgumentException("Dữ liệu cập nhật không hợp lệ");
        }

        NhanVien nhanVien = nhanVienRepository.findByIdWithTaiKhoan(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));
        nhanVien.setRole(role);
    }

    @Override
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

    @Override
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


    @Override
    @Transactional(readOnly = true)
    public List<InvoiceReportResponse> getInvoicesByFilters(Integer day, Integer month, Integer year) {
        Predicate<HoaDon> filter = buildInvoiceFilter(day, month, year);
        return hoaDonRepository.findAllByOrderByNgayTaoDesc().stream()
                .filter(filter)
                .map(this::toInvoiceReport)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentReportResponse> getPaymentsByFilters(Integer day, Integer month, Integer year) {
        Predicate<ThanhToan> filter = buildPaymentFilter(day, month, year);
        return thanhToanRepository.findAllByOrderByNgayThanhToanDesc().stream()
                .filter(filter)
                .map(this::toPaymentReport)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportInvoicesExcel(Integer day, Integer month, Integer year) {
        List<InvoiceReportResponse> rows = getInvoicesByFilters(day, month, year);
        return reportExportUtil.exportToExcel(
                "hoa_don",
                List.of("Ma hoa don", "Ma dat phong", "Nhan vien", "Tong tien", "Ngay tao"),
                rows.stream().map(this::invoiceRow).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportPaymentsExcel(Integer day, Integer month, Integer year) {
        List<PaymentReportResponse> rows = getPaymentsByFilters(day, month, year);
        return reportExportUtil.exportToExcel(
                "thanh_toan",
                List.of("Ma thanh toan", "Ma hoa don", "Phuong thuc", "So tien", "Ngay thanh toan"),
                rows.stream().map(this::paymentRow).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportInvoicesPdf(Integer day, Integer month, Integer year) {
        List<InvoiceReportResponse> rows = getInvoicesByFilters(day, month, year);
        return reportExportUtil.exportToPdf(
                "Bao cao hoa don",
                List.of("Ma hoa don", "Ma dat phong", "Nhan vien", "Tong tien", "Ngay tao"),
                rows.stream().map(this::invoiceRow).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportPaymentsPdf(Integer day, Integer month, Integer year) {
        List<PaymentReportResponse> rows = getPaymentsByFilters(day, month, year);
        return reportExportUtil.exportToPdf(
                "Bao cao thanh toan",
                List.of("Ma thanh toan", "Ma hoa don", "Phuong thuc", "So tien", "Ngay thanh toan"),
                rows.stream().map(this::paymentRow).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getRooms() {
        return phongRepository.findAllByOrderByIdDesc().stream().map(this::toRoomResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoaiPhong> getRoomTypes() {
        return loaiPhongRepository.findAll().stream()
                .sorted(Comparator.comparing(LoaiPhong::getId))
                .toList();
    }

    @Override
    @Transactional
    public LoaiPhong createRoomType(RoomTypeRequest request, MultipartFile imageFile) {
        validateRoomTypeRequest(request);

        String tenLoai = request.getTenLoai().trim();
        if (loaiPhongRepository.existsByTenLoaiIgnoreCase(tenLoai)) {
            throw new IllegalArgumentException("Tên loại phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = new LoaiPhong();
        loaiPhong.setTenLoai(tenLoai);
        loaiPhong.setMoTa(trimToNull(request.getMoTa()));
        loaiPhong.setImageUrl(resolveImageUrl(request.getImageUrl(), imageFile, "hotel/room-types"));
        loaiPhong.setGiaCoBan(request.getGiaCoBan());
        return loaiPhongRepository.save(loaiPhong);
    }

    @Override
    @Transactional
    public LoaiPhong updateRoomType(Long roomTypeId, RoomTypeRequest request, MultipartFile imageFile) {
        validateRoomTypeRequest(request);

        LoaiPhong loaiPhong = loaiPhongRepository.findById(roomTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        String tenLoai = request.getTenLoai().trim();
        if (!tenLoai.equalsIgnoreCase(loaiPhong.getTenLoai()) && loaiPhongRepository.existsByTenLoaiIgnoreCase(tenLoai)) {
            throw new IllegalArgumentException("Tên loại phòng đã tồn tại");
        }

        String imageUrl = resolveImageUrl(request.getImageUrl(), imageFile, "hotel/room-types");

        loaiPhong.setTenLoai(tenLoai);
        loaiPhong.setMoTa(trimToNull(request.getMoTa()));
        loaiPhong.setImageUrl(imageUrl != null ? imageUrl : loaiPhong.getImageUrl());
        loaiPhong.setGiaCoBan(request.getGiaCoBan());
        return loaiPhong;
    }

    @Override
    @Transactional
    public void deleteRoomType(Long roomTypeId) {
        if (!loaiPhongRepository.existsById(roomTypeId)) {
            throw new IllegalArgumentException("Không tìm thấy loại phòng");
        }
        if (phongRepository.existsByLoaiPhong_Id(roomTypeId)) {
            throw new IllegalArgumentException("Không thể xóa loại phòng đang được sử dụng");
        }
        loaiPhongRepository.deleteById(roomTypeId);
    }

    @Override
    @Transactional
    public RoomResponse createRoom(RoomRequest request, MultipartFile imageFile) {
        validateRoomRequest(request);

        String soPhong = request.getSoPhong().trim();
        if (phongRepository.existsBySoPhong(soPhong)) {
            throw new IllegalArgumentException("Số phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getLoaiPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        Phong phong = new Phong();
        phong.setSoPhong(soPhong);
        phong.setImageUrl(resolveImageUrl(request.getImageUrl(), imageFile, "hotel/rooms"));
        phong.setTrangThai(request.getTrangThai());
        phong.setLoaiPhong(loaiPhong);
        return toRoomResponse(phongRepository.save(phong));
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(Long roomId, RoomRequest request, MultipartFile imageFile) {
        validateRoomRequest(request);

        Phong phong = phongRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng"));

        String soPhong = request.getSoPhong().trim();
        if (!soPhong.equalsIgnoreCase(phong.getSoPhong()) && phongRepository.existsBySoPhong(soPhong)) {
            throw new IllegalArgumentException("Số phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getLoaiPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        String imageUrl = resolveImageUrl(request.getImageUrl(), imageFile, "hotel/rooms");

        phong.setSoPhong(soPhong);
        phong.setImageUrl(imageUrl != null ? imageUrl : phong.getImageUrl());
        phong.setTrangThai(request.getTrangThai());
        phong.setLoaiPhong(loaiPhong);
        return toRoomResponse(phong);
    }

    @Override
    @Transactional
    public void deleteRoom(Long roomId) {
        if (!phongRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Không tìm thấy phòng");
        }
        phongRepository.deleteById(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponse> getServices() {
        return dichVuRepository.findAllByOrderByIdDesc().stream()
                .map(this::toServiceResponse)
                .toList();
    }

    @Override
    @Transactional
    public ServiceResponse createService(ServiceRequest request, MultipartFile imageFile) {
        validateServiceRequest(request);

        DichVu dichVu = new DichVu();
        dichVu.setTen(request.getTen().trim());
        dichVu.setImageUrl(resolveImageUrl(request.getImageUrl(), imageFile, "hotel/services"));
        dichVu.setGia(request.getGia());
        return toServiceResponse(dichVuRepository.save(dichVu));
    }

    @Override
    @Transactional
    public ServiceResponse updateService(Long serviceId, ServiceRequest request, MultipartFile imageFile) {
        validateServiceRequest(request);

        DichVu dichVu = dichVuRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ"));
        String imageUrl = resolveImageUrl(request.getImageUrl(), imageFile, "hotel/services");

        dichVu.setTen(request.getTen().trim());
        dichVu.setImageUrl(imageUrl != null ? imageUrl : dichVu.getImageUrl());
        dichVu.setGia(request.getGia());
        return toServiceResponse(dichVu);
    }

    @Override
    @Transactional
    public void deleteService(Long serviceId) {
        if (!dichVuRepository.existsById(serviceId)) {
            throw new IllegalArgumentException("Không tìm thấy dịch vụ");
        }
        dichVuRepository.deleteById(serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> getPromotions() {
        return khuyenMaiRepository.findAllByOrderByIdDesc().stream()
                .map(this::toPromotionResponse)
                .toList();
    }

    @Override
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        validatePromotionRequest(request);

        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTen(request.getTen().trim());
        khuyenMai.setLoaiGiam(request.getLoaiGiam());
        khuyenMai.setGiaTri(request.getGiaTri());
        return toPromotionResponse(khuyenMaiRepository.save(khuyenMai));
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        validatePromotionRequest(request);

        KhuyenMai khuyenMai = khuyenMaiRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khuyến mãi"));
        khuyenMai.setTen(request.getTen().trim());
        khuyenMai.setLoaiGiam(request.getLoaiGiam());
        khuyenMai.setGiaTri(request.getGiaTri());
        return toPromotionResponse(khuyenMai);
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        if (!khuyenMaiRepository.existsById(promotionId)) {
            throw new IllegalArgumentException("Không tìm thấy khuyến mãi");
        }
        khuyenMaiRepository.deleteById(promotionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomers() {
        return khachHangRepository.findAllByOrderByIdDesc().stream()
                .map(this::toCustomerResponse)
                .toList();
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        validateCustomerRequest(request);

        String sdt = request.getSdt().trim();
        if (khachHangRepository.existsBySdt(sdt)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        String email = trimToNull(request.getEmail());
        if (email != null && khachHangRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setTen(request.getTen().trim());
        khachHang.setSdt(sdt);
        khachHang.setEmail(email);
        return toCustomerResponse(khachHangRepository.save(khachHang));
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        validateCustomerRequest(request);

        KhachHang khachHang = khachHangRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));

        String sdt = request.getSdt().trim();
        if (!sdt.equalsIgnoreCase(khachHang.getSdt()) && khachHangRepository.existsBySdt(sdt)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        String email = trimToNull(request.getEmail());
        if (email != null && !email.equalsIgnoreCase(trimToNull(khachHang.getEmail()))
                && khachHangRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        khachHang.setTen(request.getTen().trim());
        khachHang.setSdt(sdt);
        khachHang.setEmail(email);
        return toCustomerResponse(khachHang);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        if (!khachHangRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }
        khachHangRepository.deleteById(customerId);
    }

    private RoomResponse toRoomResponse(Phong phong) {
        return new RoomResponse(
                phong.getId(),
                phong.getSoPhong(),
                phong.getTrangThai(),
                phong.getImageUrl(),
                phong.getLoaiPhong().getId(),
                phong.getLoaiPhong().getTenLoai(),
                phong.getLoaiPhong().getMoTa());
    }

    private ServiceResponse toServiceResponse(DichVu dichVu) {
        return new ServiceResponse(dichVu.getId(), dichVu.getTen(), dichVu.getImageUrl(), dichVu.getGia());
    }

    private PromotionResponse toPromotionResponse(KhuyenMai khuyenMai) {
        return new PromotionResponse(khuyenMai.getId(), khuyenMai.getTen(), khuyenMai.getLoaiGiam(), khuyenMai.getGiaTri());
    }

    private CustomerResponse toCustomerResponse(KhachHang khachHang) {
        return new CustomerResponse(khachHang.getId(), khachHang.getTen(), khachHang.getSdt(), khachHang.getEmail());
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

    private void validateCreateForm(CreateEmployeeForm form) {
        if (form == null
                || isBlank(form.getEmployeeName())
                || isBlank(form.getUsername())
                || isBlank(form.getEmail())
                || isBlank(form.getPassword())
                || form.getRole() == null) {
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin");
        }
    }

    private void validateRoomRequest(RoomRequest request) {
        if (request == null || isBlank(request.getSoPhong()) || request.getTrangThai() == null || request.getLoaiPhongId() == null) {
            throw new IllegalArgumentException("Dữ liệu phòng không hợp lệ");
        }
    }

    private void validateRoomTypeRequest(RoomTypeRequest request) {
        if (request == null || isBlank(request.getTenLoai()) || request.getGiaCoBan() == null || request.getGiaCoBan().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu loại phòng không hợp lệ");
        }
    }

    private void validateServiceRequest(ServiceRequest request) {
        if (request == null || isBlank(request.getTen()) || request.getGia() == null || request.getGia().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu dịch vụ không hợp lệ");
        }
    }

    private void validatePromotionRequest(PromotionRequest request) {
        if (request == null || isBlank(request.getTen()) || request.getLoaiGiam() == null
                || request.getGiaTri() == null || request.getGiaTri().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu khuyến mãi không hợp lệ");
        }
    }

    private void validateCustomerRequest(CustomerRequest request) {
        if (request == null || isBlank(request.getTen()) || isBlank(request.getSdt())) {
            throw new IllegalArgumentException("Dữ liệu khách hàng không hợp lệ");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String resolveImageUrl(String fallbackUrl, MultipartFile imageFile, String folder) {
        String uploadedImageUrl = cloudinaryUploadService.uploadImage(imageFile, folder);
        if (!isBlank(uploadedImageUrl)) {
            return uploadedImageUrl;
        }
        return trimToNull(fallbackUrl);
    }
}
