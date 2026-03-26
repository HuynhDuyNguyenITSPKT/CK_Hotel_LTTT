package hcmute.system.hotel.cknhom11qlhotel.controller;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final IAdminManagementService adminManagementService;

    public AdminController(IAdminManagementService adminManagementService) {
        this.adminManagementService = adminManagementService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        LoginSession currentUser = requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("accounts", adminManagementService.getAccounts());
        model.addAttribute("employees", adminManagementService.getEmployees());
        model.addAttribute("createEmployeeForm", new CreateEmployeeForm());
        model.addAttribute("roles", EmployeeRole.values());
        model.addAttribute("statuses", AccountStatus.values());
        return "dashboard/admin";
    }

    @PostMapping("/admin/employees")
    public String createEmployee(CreateEmployeeForm form, HttpSession session, Model model) {
        LoginSession currentUser = requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createEmployeeWithAccount(form);
            return "redirect:/admin/dashboard?success=created";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("accounts", adminManagementService.getAccounts());
            model.addAttribute("employees", adminManagementService.getEmployees());
            model.addAttribute("createEmployeeForm", form);
            model.addAttribute("roles", EmployeeRole.values());
            model.addAttribute("statuses", AccountStatus.values());
            model.addAttribute("error", ex.getMessage());
            return "dashboard/admin";
        }
    }

    @PostMapping("/admin/accounts/{accountId}/status")
    public String updateStatus(@PathVariable Long accountId,
                               @RequestParam AccountStatus status,
                               HttpSession session) {
        LoginSession currentUser = requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        adminManagementService.updateAccountStatus(accountId, status);
        return "redirect:/admin/dashboard?success=status";
    }

    @PostMapping("/admin/employees/{employeeId}/role")
    public String updateRole(@PathVariable Long employeeId,
                             @RequestParam EmployeeRole role,
                             HttpSession session) {
        LoginSession currentUser = requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        adminManagementService.updateEmployeeRole(employeeId, role);
        return "redirect:/admin/dashboard?success=role";
    }

    private LoginSession requireAdmin(HttpSession session) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.ADMIN) {
            return null;
        }
        return currentUser;
    }
}

