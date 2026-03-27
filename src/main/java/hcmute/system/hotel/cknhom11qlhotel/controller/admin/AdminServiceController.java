package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminServiceController {

    private final IAdminManagementService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminServiceController(IAdminManagementService adminManagementService,
                                  AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/services")
    public String createService(ServiceRequest request,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createService(request);
            redirectAttributes.addFlashAttribute("success", "Thêm dịch vụ thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=services";
    }

    @PostMapping("/admin/services/{serviceId}/update")
    public String updateService(@PathVariable Long serviceId,
                                ServiceRequest request,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateService(serviceId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật dịch vụ thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=services";
    }

    @PostMapping("/admin/services/{serviceId}/delete")
    public String deleteService(@PathVariable Long serviceId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.deleteService(serviceId);
            redirectAttributes.addFlashAttribute("success", "Xóa dịch vụ thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=services";
    }
}

