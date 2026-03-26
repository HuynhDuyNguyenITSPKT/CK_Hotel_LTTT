package hcmute.system.hotel.cknhom11qlhotel.controller.api.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ApiMessage;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportApiController {

    private final IAdminManagementService adminManagementService;
    private final AdminApiSupport adminApiSupport;

    public AdminReportApiController(IAdminManagementService adminManagementService,
                                    AdminApiSupport adminApiSupport) {
        this.adminManagementService = adminManagementService;
        this.adminApiSupport = adminApiSupport;
    }

    @GetMapping("/revenue-chart")
    public ResponseEntity<?> getRevenueChart(HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getRevenueChart());
    }

    @GetMapping("/invoices")
    public ResponseEntity<?> getLatestInvoices(@RequestParam(required = false) Integer day,
                                               @RequestParam(required = false) Integer month,
                                               @RequestParam(required = false) Integer year,
                                               HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getInvoicesByFilters(day, month, year));
    }

    @GetMapping("/payments")
    public ResponseEntity<?> getLatestPayments(@RequestParam(required = false) Integer day,
                                               @RequestParam(required = false) Integer month,
                                               @RequestParam(required = false) Integer year,
                                               HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getPaymentsByFilters(day, month, year));
    }

    @GetMapping("/invoices/export-excel")
    public ResponseEntity<?> exportInvoicesExcel(@RequestParam(required = false) Integer day,
                                                 @RequestParam(required = false) Integer month,
                                                 @RequestParam(required = false) Integer year,
                                                 HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        byte[] content = adminManagementService.exportInvoicesExcel(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-hoa-don.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @GetMapping("/payments/export-excel")
    public ResponseEntity<?> exportPaymentsExcel(@RequestParam(required = false) Integer day,
                                                 @RequestParam(required = false) Integer month,
                                                 @RequestParam(required = false) Integer year,
                                                 HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        byte[] content = adminManagementService.exportPaymentsExcel(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-thanh-toan.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @GetMapping("/invoices/export-pdf")
    public ResponseEntity<?> exportInvoicesPdf(@RequestParam(required = false) Integer day,
                                               @RequestParam(required = false) Integer month,
                                               @RequestParam(required = false) Integer year,
                                               HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        byte[] content = adminManagementService.exportInvoicesPdf(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-hoa-don.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @GetMapping("/payments/export-pdf")
    public ResponseEntity<?> exportPaymentsPdf(@RequestParam(required = false) Integer day,
                                               @RequestParam(required = false) Integer month,
                                               @RequestParam(required = false) Integer year,
                                               HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        byte[] content = adminManagementService.exportPaymentsPdf(day, month, year);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bao-cao-thanh-toan.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }
}

