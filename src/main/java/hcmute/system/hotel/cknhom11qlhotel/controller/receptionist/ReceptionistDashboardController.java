package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.TrangDuLieuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.BookingStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IDashboardQueryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistDashboardController {

    private final IDashboardQueryService dashboardQueryService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistDashboardController(IDashboardQueryService dashboardQueryService,
                                           ReceptionistAuthorizationSupport authorizationSupport) {
        this.dashboardQueryService = dashboardQueryService;
        this.authorizationSupport = authorizationSupport;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(defaultValue = "tong-quan") String tab,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "8") int size,
                            @RequestParam(required = false) Long selectedBookingId,
                            @RequestParam(required = false) String checkoutPaymentStatus,
                            @RequestParam(defaultValue = "moi-nhat") String checkoutSort,
                            @RequestParam(required = false) String bookingStatus,
                            @RequestParam(defaultValue = "moi-nhat") String bookingSort,
                            @RequestParam(required = false) String invoicePaymentStatus,
                            @RequestParam(defaultValue = "moi-nhat") String invoiceSort,
                            @RequestParam(defaultValue = "moi-nhat") String inStaySort,
                            HttpSession session,
                            Model model) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        String activeTab = normalizeTab(tab);
        int safeSize = Math.max(3, Math.min(size, 20));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("size", safeSize);
        model.addAttribute("thongKeNhanh", dashboardQueryService.layThongKeNhanh());
        model.addAttribute("roomStatuses", RoomStatus.values());
        model.addAttribute("bookingStatuses", BookingStatus.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("phongChoDat", dashboardQueryService.layPhongChoDatPhong());
        model.addAttribute("danhSachDichVu", dashboardQueryService.layDanhSachDichVu());
        model.addAttribute("danhSachKhuyenMai", dashboardQueryService.layDanhSachKhuyenMai());

        model.addAttribute("checkoutPaymentStatus", checkoutPaymentStatus);
        model.addAttribute("checkoutSort", checkoutSort);
        model.addAttribute("bookingStatus", bookingStatus);
        model.addAttribute("bookingSort", bookingSort);
        model.addAttribute("invoicePaymentStatus", invoicePaymentStatus);
        model.addAttribute("invoiceSort", invoiceSort);
        model.addAttribute("inStaySort", inStaySort);
        model.addAttribute("selectedBookingId", selectedBookingId);

        if (!model.containsAttribute("bookingForm")) {
            model.addAttribute("bookingForm", new LeTanTaoDatPhongFormDto());
        }

        switch (activeTab) {
            case "check-in" -> {
                TrangDuLieuDto<?> duLieu = dashboardQueryService.layTrangCheckIn(page, safeSize);
                model.addAttribute("danhSachCheckIn", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "check-out" -> {
                TrangDuLieuDto<?> duLieu = dashboardQueryService.layTrangCheckOut(page, safeSize, checkoutPaymentStatus, checkoutSort);
                model.addAttribute("danhSachCheckOut", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "quan-ly-phong" -> {
                TrangDuLieuDto<?> duLieu = dashboardQueryService.layTrangQuanLyPhong(page, safeSize);
                model.addAttribute("danhSachPhong", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "dat-phong" -> {
                TrangDuLieuDto<?> duLieu = dashboardQueryService.layTrangDatPhong(page, safeSize, bookingStatus, bookingSort);
                model.addAttribute("danhSachDatPhong", duLieu.getDanhSach());
                model.addAttribute("danhSachChiTietDatPhong", dashboardQueryService.layDanhSachChiTietDatPhong());
                themThongTinPhanTrang(model, duLieu);
            }
            case "dang-o-them-dich-vu" -> {
                TrangDuLieuDto<?> duLieu = dashboardQueryService.layTrangDangOThemDichVu(page, safeSize, inStaySort);
                model.addAttribute("danhSachDangOThemDichVu", duLieu.getDanhSach());
                model.addAttribute("danhSachSuDungDichVu", dashboardQueryService.layDanhSachSuDungDichVu());
                themThongTinPhanTrang(model, duLieu);
            }
            case "hoa-don-thanh-toan" -> {
                TrangDuLieuDto<?> duLieu = dashboardQueryService.layTrangHoaDonThanhToan(page, safeSize, invoicePaymentStatus, invoiceSort);
                model.addAttribute("danhSachHoaDonThanhToan", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            default -> {
                model.addAttribute("danhSachCheckInNhanh", dashboardQueryService.layCheckInSapToi(5));
                model.addAttribute("danhSachCheckOutNhanh", dashboardQueryService.layCheckOutSapToi(5));
                model.addAttribute("danhSachDatPhongNhanh", dashboardQueryService.layDatPhongGanNhat(6));
            }
        }

        return "receptionist/dashboard";
    }

    private void themThongTinPhanTrang(Model model, TrangDuLieuDto<?> duLieu) {
        model.addAttribute("page", duLieu.getTrangHienTai());
        model.addAttribute("totalPages", duLieu.getTongTrang());
        model.addAttribute("totalItems", duLieu.getTongBanGhi());
        model.addAttribute("hasPrev", duLieu.isCoTrangTruoc());
        model.addAttribute("hasNext", duLieu.isCoTrangSau());
    }

    private String normalizeTab(String tab) {
        return switch (tab) {
            case "tong-quan", "check-in", "check-out", "quan-ly-phong", "dat-phong", "dang-o-them-dich-vu", "hoa-don-thanh-toan" -> tab;
            default -> "tong-quan";
        };
    }

}
