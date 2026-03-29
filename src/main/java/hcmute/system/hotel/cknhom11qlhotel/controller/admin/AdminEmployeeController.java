package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.service.IQuanTriTongHopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminEmployeeController {

    private final IQuanTriTongHopService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminEmployeeController(IQuanTriTongHopService adminManagementService,
                                   AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/employees")
    public String createEmployee(CreateEmployeeForm form,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createEmployeeWithAccount(form);
            redirectAttributes.addFlashAttribute("success", "Tạo nhân viên và tài khoản thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=employees";
    }

    @PostMapping("/admin/employees/{employeeId}/role")
    public String updateRole(@PathVariable Long employeeId,
                             @RequestParam EmployeeRole role,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateEmployeeRole(employeeId, role);
            redirectAttributes.addFlashAttribute("success", "Cập nhật vai trò nhân viên thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=employees";
    }
}

