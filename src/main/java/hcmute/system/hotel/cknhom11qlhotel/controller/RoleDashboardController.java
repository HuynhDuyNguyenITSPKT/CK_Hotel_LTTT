package hcmute.system.hotel.cknhom11qlhotel.controller;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String receptionistDashboard(HttpSession session, Model model) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.RECEPTIONIST) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", currentUser);
        return "dashboard/receptionist";
    }
}

