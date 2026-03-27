package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminCustomerController {

    private final IAdminManagementService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminCustomerController(IAdminManagementService adminManagementService,
                                   AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/customers")
    public String createCustomer(CustomerRequest request,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createCustomer(request);
            redirectAttributes.addFlashAttribute("success", "Thêm khách hàng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=customers";
    }

    @PostMapping("/admin/customers/{customerId}/update")
    public String updateCustomer(@PathVariable Long customerId,
                                 CustomerRequest request,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateCustomer(customerId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật khách hàng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=customers";
    }

    @PostMapping("/admin/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable Long customerId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.deleteCustomer(customerId);
            redirectAttributes.addFlashAttribute("success", "Xóa khách hàng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=customers";
    }
}

