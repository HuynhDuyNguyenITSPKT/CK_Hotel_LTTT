package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.DashboardStatsResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.DichVu;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhachHang;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.KhuyenMai;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
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
import hcmute.system.hotel.cknhom11qlhotel.repository.TaiKhoanRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    public AdminManagementService(TaiKhoanRepository taiKhoanRepository,
                                  NhanVienRepository nhanVienRepository,
                                  PhongRepository phongRepository,
                                  LoaiPhongRepository loaiPhongRepository,
                                  DichVuRepository dichVuRepository,
                                  KhuyenMaiRepository khuyenMaiRepository,
                                  KhachHangRepository khachHangRepository,
                                  HoaDonRepository hoaDonRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.phongRepository = phongRepository;
        this.loaiPhongRepository = loaiPhongRepository;
        this.dichVuRepository = dichVuRepository;
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.khachHangRepository = khachHangRepository;
        this.hoaDonRepository = hoaDonRepository;
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
    public List<RoomResponse> getRooms() {
        return phongRepository.findAllByOrderByIdDesc().stream().map(this::toRoomResponse).toList();
    }

    @Override
    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        validateRoomRequest(request);

        String soPhong = request.soPhong().trim();
        if (phongRepository.existsBySoPhong(soPhong)) {
            throw new IllegalArgumentException("Số phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.loaiPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        Phong phong = new Phong();
        phong.setSoPhong(soPhong);
        phong.setImageUrl(trimToNull(request.imageUrl()));
        phong.setTrangThai(request.trangThai());
        phong.setLoaiPhong(loaiPhong);
        return toRoomResponse(phongRepository.save(phong));
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(Long roomId, RoomRequest request) {
        validateRoomRequest(request);

        Phong phong = phongRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng"));

        String soPhong = request.soPhong().trim();
        if (!soPhong.equalsIgnoreCase(phong.getSoPhong()) && phongRepository.existsBySoPhong(soPhong)) {
            throw new IllegalArgumentException("Số phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.loaiPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        phong.setSoPhong(soPhong);
        phong.setImageUrl(trimToNull(request.imageUrl()));
        phong.setTrangThai(request.trangThai());
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
    public ServiceResponse createService(ServiceRequest request) {
        validateServiceRequest(request);

        DichVu dichVu = new DichVu();
        dichVu.setTen(request.ten().trim());
        dichVu.setImageUrl(trimToNull(request.imageUrl()));
        dichVu.setGia(request.gia());
        return toServiceResponse(dichVuRepository.save(dichVu));
    }

    @Override
    @Transactional
    public ServiceResponse updateService(Long serviceId, ServiceRequest request) {
        validateServiceRequest(request);

        DichVu dichVu = dichVuRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ"));
        dichVu.setTen(request.ten().trim());
        dichVu.setImageUrl(trimToNull(request.imageUrl()));
        dichVu.setGia(request.gia());
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
        khuyenMai.setTen(request.ten().trim());
        khuyenMai.setLoaiGiam(request.loaiGiam());
        khuyenMai.setGiaTri(request.giaTri());
        return toPromotionResponse(khuyenMaiRepository.save(khuyenMai));
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        validatePromotionRequest(request);

        KhuyenMai khuyenMai = khuyenMaiRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khuyến mãi"));
        khuyenMai.setTen(request.ten().trim());
        khuyenMai.setLoaiGiam(request.loaiGiam());
        khuyenMai.setGiaTri(request.giaTri());
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

        String sdt = request.sdt().trim();
        if (khachHangRepository.existsBySdt(sdt)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        String email = trimToNull(request.email());
        if (email != null && khachHangRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setTen(request.ten().trim());
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

        String sdt = request.sdt().trim();
        if (!sdt.equalsIgnoreCase(khachHang.getSdt()) && khachHangRepository.existsBySdt(sdt)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        String email = trimToNull(request.email());
        if (email != null && !email.equalsIgnoreCase(trimToNull(khachHang.getEmail()))
                && khachHangRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        khachHang.setTen(request.ten().trim());
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
        if (request == null || isBlank(request.soPhong()) || request.trangThai() == null || request.loaiPhongId() == null) {
            throw new IllegalArgumentException("Dữ liệu phòng không hợp lệ");
        }
    }

    private void validateServiceRequest(ServiceRequest request) {
        if (request == null || isBlank(request.ten()) || request.gia() == null || request.gia().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu dịch vụ không hợp lệ");
        }
    }

    private void validatePromotionRequest(PromotionRequest request) {
        if (request == null || isBlank(request.ten()) || request.loaiGiam() == null
                || request.giaTri() == null || request.giaTri().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu khuyến mãi không hợp lệ");
        }
    }

    private void validateCustomerRequest(CustomerRequest request) {
        if (request == null || isBlank(request.ten()) || isBlank(request.sdt())) {
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
}
