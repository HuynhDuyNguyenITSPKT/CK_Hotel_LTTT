package hcmute.system.hotel.cknhom11qlhotel.controller;

import hcmute.system.hotel.cknhom11qlhotel.controller.admin.AdminAuthorizationSupport;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminAccountView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.AdminEmployeeView;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.DiscountType;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final IAdminManagementService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminController(IAdminManagementService adminManagementService,
                           AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(@RequestParam(required = false) Integer day,
                            @RequestParam(required = false) Integer month,
                            @RequestParam(required = false) Integer year,
                            @RequestParam(defaultValue = "dashboard") String tab,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            HttpSession session,
                            Model model) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        int safeSize = Math.max(5, Math.min(size, 30));
        int safePage = Math.max(page, 1);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("activeTab", tab);
        model.addAttribute("size", safeSize);

        model.addAttribute("stats", adminManagementService.getDashboardStats());
        model.addAttribute("revenueChart", adminManagementService.getRevenueChart());
        model.addAttribute("invoiceReports", adminManagementService.getInvoicesByFilters(day, month, year));
        model.addAttribute("paymentReports", adminManagementService.getPaymentsByFilters(day, month, year));
        model.addAttribute("filterDay", day);
        model.addAttribute("filterMonth", month);
        model.addAttribute("filterYear", year);

        model.addAttribute("statuses", AccountStatus.values());
        model.addAttribute("roles", EmployeeRole.values());
        model.addAttribute("roomStatuses", RoomStatus.values());
        model.addAttribute("discountTypes", DiscountType.values());

        loadTabData(model, tab, safePage, safeSize);
        return "dashboard/admin";
    }

    @GetMapping("/admin/reports/invoices/excel")
    public ResponseEntity<byte[]> exportInvoicesExcel(@RequestParam(required = false) Integer day,
                                                      @RequestParam(required = false) Integer month,
                                                      @RequestParam(required = false) Integer year,
                                                      HttpSession session) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = adminManagementService.exportInvoicesExcel(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-hoa-don.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @GetMapping("/admin/reports/payments/excel")
    public ResponseEntity<byte[]> exportPaymentsExcel(@RequestParam(required = false) Integer day,
                                                      @RequestParam(required = false) Integer month,
                                                      @RequestParam(required = false) Integer year,
                                                      HttpSession session) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = adminManagementService.exportPaymentsExcel(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-thanh-toan.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @GetMapping("/admin/reports/invoices/pdf")
    public ResponseEntity<byte[]> exportInvoicesPdf(@RequestParam(required = false) Integer day,
                                                    @RequestParam(required = false) Integer month,
                                                    @RequestParam(required = false) Integer year,
                                                    HttpSession session) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = adminManagementService.exportInvoicesPdf(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-hoa-don.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @GetMapping("/admin/reports/payments/pdf")
    public ResponseEntity<byte[]> exportPaymentsPdf(@RequestParam(required = false) Integer day,
                                                    @RequestParam(required = false) Integer month,
                                                    @RequestParam(required = false) Integer year,
                                                    HttpSession session) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        byte[] content = adminManagementService.exportPaymentsPdf(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-thanh-toan.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    private void loadTabData(Model model, String tab, int page, int size) {
        switch (tab) {
            case "accounts" -> addAccountsPage(model, page, size);
            case "employees" -> addEmployeesPage(model, page, size);
            case "rooms" -> addRoomsPage(model, page, size);
            case "services" -> addServicesPage(model, page, size);
            case "promotions" -> addPromotionsPage(model, page, size);
            case "customers" -> addCustomersPage(model, page, size);
            default -> model.addAttribute("size", DEFAULT_PAGE_SIZE);
        }
    }

    private void addAccountsPage(Model model, int page, int size) {
        List<AdminAccountView> all = adminManagementService.getAccounts();
        PaginationResult<AdminAccountView> result = paginate(all, page, size);
        model.addAttribute("accounts", result.items());
        addPaginationModel(model, result);
    }

    private void addEmployeesPage(Model model, int page, int size) {
        List<AdminEmployeeView> all = adminManagementService.getEmployees();
        PaginationResult<AdminEmployeeView> result = paginate(all, page, size);
        model.addAttribute("employees", result.items());
        addPaginationModel(model, result);
    }

    private void addRoomsPage(Model model, int page, int size) {
        var all = adminManagementService.getRooms();
        PaginationResult<?> result = paginate(all, page, size);
        model.addAttribute("rooms", result.items());
        model.addAttribute("roomTypes", adminManagementService.getRoomTypes());
        addPaginationModel(model, result);
    }

    private void addServicesPage(Model model, int page, int size) {
        var all = adminManagementService.getServices();
        PaginationResult<?> result = paginate(all, page, size);
        model.addAttribute("services", result.items());
        addPaginationModel(model, result);
    }

    private void addPromotionsPage(Model model, int page, int size) {
        var all = adminManagementService.getPromotions();
        PaginationResult<?> result = paginate(all, page, size);
        model.addAttribute("promotions", result.items());
        addPaginationModel(model, result);
    }

    private void addCustomersPage(Model model, int page, int size) {
        var all = adminManagementService.getCustomers();
        PaginationResult<?> result = paginate(all, page, size);
        model.addAttribute("customers", result.items());
        addPaginationModel(model, result);
    }

    private <T> PaginationResult<T> paginate(List<T> source, int page, int size) {
        int totalItems = source == null ? 0 : source.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / size));
        int safePage = Math.max(1, Math.min(page, totalPages));
        int fromIndex = Math.min((safePage - 1) * size, totalItems);
        int toIndex = Math.min(fromIndex + size, totalItems);
        List<T> items = totalItems == 0 ? List.of() : source.subList(fromIndex, toIndex);
        return new PaginationResult<>(items, safePage, totalPages, totalItems);
    }

    private void addPaginationModel(Model model, PaginationResult<?> result) {
        model.addAttribute("page", result.page());
        model.addAttribute("totalPages", result.totalPages());
        model.addAttribute("totalItems", result.totalItems());
        model.addAttribute("hasPrev", result.page() > 1);
        model.addAttribute("hasNext", result.page() < result.totalPages());
    }

    private static class PaginationResult<T> {
        private final List<T> items;
        private final int page;
        private final int totalPages;
        private final int totalItems;

        private PaginationResult(List<T> items, int page, int totalPages, int totalItems) {
            this.items = items;
            this.page = page;
            this.totalPages = totalPages;
            this.totalItems = totalItems;
        }

        public List<T> items() {
            return items;
        }

        public int page() {
            return page;
        }

        public int totalPages() {
            return totalPages;
        }

        public int totalItems() {
            return totalItems;
        }
    }
}

