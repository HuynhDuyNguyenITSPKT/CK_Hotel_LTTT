package hcmute.system.hotel.cknhom11qlhotel.controller;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RoleDashboardController {

    @GetMapping("/")
    public String root(HttpSession session) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null) {
            return "redirect:/login";
        }

        return switch (currentUser.getRole()) {
            case ADMIN -> "redirect:/admin/dashboard";
            case MANAGER -> "redirect:/manager/dashboard";
            case RECEPTIONIST -> "redirect:/receptionist/dashboard";
        };
    }

    @GetMapping("/manager/dashboard")
    public String managerDashboard(HttpSession session, Model model) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.MANAGER) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", currentUser);
        return "dashboard/manager";
    }

    @GetMapping("/receptionist/dashboard")
    public String receptionistDashboard(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "5") int size,
                                        HttpSession session,
                                        Model model) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.RECEPTIONIST) {
            return "redirect:/login";
        }

        int safeSize = Math.max(3, Math.min(size, 10));
        List<ReceptionRequest> allRequests = List.of(
                new ReceptionRequest("Trần Thị B", "Check-out", "Chờ xử lý", "amber"),
                new ReceptionRequest("Phạm Văn C", "Gia hạn phòng", "Đã tiếp nhận", "cyan"),
                new ReceptionRequest("Nguyễn Văn D", "Đổi phòng", "Chờ xử lý", "amber"),
                new ReceptionRequest("Lê Thị E", "Yêu cầu dọn phòng", "Đã tiếp nhận", "cyan"),
                new ReceptionRequest("Võ Minh F", "Hỗ trợ hành lý", "Chờ xử lý", "amber"),
                new ReceptionRequest("Bùi Anh G", "Check-in sớm", "Đã tiếp nhận", "cyan")
        );

        int totalItems = allRequests.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / safeSize));
        int safePage = Math.max(1, Math.min(page, totalPages));
        long skip = (long) (safePage - 1) * safeSize;
        List<ReceptionRequest> pageItems = allRequests.stream()
                .skip(skip)
                .limit(safeSize)
                .toList();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("receptionistRequests", pageItems);
        model.addAttribute("receptionistPage", safePage);
        model.addAttribute("receptionistTotalPages", totalPages);
        model.addAttribute("receptionistTotalItems", totalItems);
        model.addAttribute("receptionistHasPrev", safePage > 1);
        model.addAttribute("receptionistHasNext", safePage < totalPages);
        model.addAttribute("receptionistSize", safeSize);
        return "dashboard/receptionist";
    }

    private record ReceptionRequest(String customerName, String requestType, String status, String tone) {
    }
}

