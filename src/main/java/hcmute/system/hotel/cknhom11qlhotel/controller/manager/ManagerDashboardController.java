package hcmute.system.hotel.cknhom11qlhotel.controller.manager;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IDanhMucQuanLyService;
import hcmute.system.hotel.cknhom11qlhotel.service.IBangDieuKhienQuanLyService;
import hcmute.system.hotel.cknhom11qlhotel.service.IBaoCaoQuanLyService;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {

    private final IBangDieuKhienQuanLyService managerDashboardService;
    private final IBaoCaoQuanLyService managerReportService;
    private final IDanhMucQuanLyService managerCatalogService;

    public ManagerDashboardController(IBangDieuKhienQuanLyService managerDashboardService,
                                      IBaoCaoQuanLyService managerReportService,
                                      IDanhMucQuanLyService managerCatalogService) {
        this.managerDashboardService = managerDashboardService;
        this.managerReportService = managerReportService;
        this.managerCatalogService = managerCatalogService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(defaultValue = "7") int days,
                            @RequestParam(defaultValue = "tong-quan") String tab,
                            @RequestParam(required = false) String q,
                            @RequestParam(required = false) RoomStatus roomStatus,
                            @RequestParam(required = false) Integer day,
                            @RequestParam(required = false) Integer month,
                            @RequestParam(required = false) Integer year,
                            HttpSession session,
                            Model model) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.MANAGER) {
            return "redirect:/login";
        }

        int soNgay = normalizeDays(days);
        String activeTab = normalizeTab(tab);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("q", q);
        model.addAttribute("roomStatusFilter", roomStatus);
        model.addAttribute("selectedTrendDays", soNgay);
        model.addAttribute("filterDay", day);
        model.addAttribute("filterMonth", month);
        model.addAttribute("filterYear", year);
        model.addAttribute("roomStatuses", RoomStatus.values());
        model.addAttribute("discountTypes", DiscountType.values());
        model.addAttribute("roomTypes", managerCatalogService.layLoaiPhong());

        switch (activeTab) {
            case "bao-cao" -> {
                model.addAttribute("invoiceReports", managerReportService.layBaoCaoHoaDon(day, month, year));
                model.addAttribute("paymentReports", managerReportService.layBaoCaoThanhToan(day, month, year));
            }
            case "quan-ly-phong" -> model.addAttribute("rooms", managerCatalogService.layDanhSachPhong(roomStatus, q));
            case "quan-ly-khuyen-mai" -> model.addAttribute("promotions", managerCatalogService.layDanhSachKhuyenMai(q));
            case "quan-ly-dich-vu" -> model.addAttribute("services", managerCatalogService.layDanhSachDichVu(q));
            default -> {
                model.addAttribute("managerStats", managerDashboardService.layThongKeTongQuan());
                model.addAttribute("managerTrend", managerDashboardService.layTrendPhongDatTheoNgay(soNgay));
                model.addAttribute("managerGuestWindow", managerDashboardService.layBieuDoKhachDatTheoKhoangNgay(5, 5));
            }
        }

        return "manager/dashboard";
    }

    @GetMapping("/reports/invoices/excel")
    public ResponseEntity<byte[]> exportInvoicesExcel(@RequestParam(required = false) Integer day,
                                                      @RequestParam(required = false) Integer month,
                                                      @RequestParam(required = false) Integer year,
                                                      HttpSession session) {
        if (!isManager(session)) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = managerReportService.xuatBaoCaoHoaDonExcel(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-hoa-don-manager.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @GetMapping("/reports/payments/excel")
    public ResponseEntity<byte[]> exportPaymentsExcel(@RequestParam(required = false) Integer day,
                                                      @RequestParam(required = false) Integer month,
                                                      @RequestParam(required = false) Integer year,
                                                      HttpSession session) {
        if (!isManager(session)) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = managerReportService.xuatBaoCaoThanhToanExcel(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-thanh-toan-manager.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @GetMapping("/reports/invoices/pdf")
    public ResponseEntity<byte[]> exportInvoicesPdf(@RequestParam(required = false) Integer day,
                                                    @RequestParam(required = false) Integer month,
                                                    @RequestParam(required = false) Integer year,
                                                    HttpSession session) {
        if (!isManager(session)) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = managerReportService.xuatBaoCaoHoaDonPdf(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-hoa-don-manager.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @GetMapping("/reports/payments/pdf")
    public ResponseEntity<byte[]> exportPaymentsPdf(@RequestParam(required = false) Integer day,
                                                    @RequestParam(required = false) Integer month,
                                                    @RequestParam(required = false) Integer year,
                                                    HttpSession session) {
        if (!isManager(session)) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = managerReportService.xuatBaoCaoThanhToanPdf(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-thanh-toan-manager.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @PostMapping("/rooms/{roomId}/update")
    public String updateRoom(@PathVariable Long roomId,
                             RoomRequest request,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isManager(session)) {
            return "redirect:/login";
        }

        try {
            managerCatalogService.capNhatPhong(roomId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/manager/dashboard?tab=quan-ly-phong";
    }

    @PostMapping("/promotions/{promotionId}/update")
    public String updatePromotion(@PathVariable Long promotionId,
                                  PromotionRequest request,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        if (!isManager(session)) {
            return "redirect:/login";
        }

        try {
            managerCatalogService.capNhatKhuyenMai(promotionId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật khuyến mãi thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/manager/dashboard?tab=quan-ly-khuyen-mai";
    }

    @PostMapping("/services/{serviceId}/update")
    public String updateService(@PathVariable Long serviceId,
                                ServiceRequest request,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isManager(session)) {
            return "redirect:/login";
        }

        try {
            managerCatalogService.capNhatDichVu(serviceId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật dịch vụ thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/manager/dashboard?tab=quan-ly-dich-vu";
    }

    private int normalizeDays(int days) {
        return switch (days) {
            case 14, 30 -> days;
            default -> 7;
        };
    }

    private String normalizeTab(String tab) {
        return switch (tab) {
            case "tong-quan", "bao-cao", "quan-ly-phong", "quan-ly-khuyen-mai", "quan-ly-dich-vu" -> tab;
            default -> "tong-quan";
        };
    }

    private boolean isManager(HttpSession session) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        return currentUser != null && currentUser.getRole() == EmployeeRole.MANAGER;
    }
}
