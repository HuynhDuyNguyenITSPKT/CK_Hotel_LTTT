package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IQuanTriTongHopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminAccountController {

    private final IQuanTriTongHopService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminAccountController(IQuanTriTongHopService adminManagementService,
                                  AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/accounts/{accountId}/status")
    public String updateStatus(@PathVariable Long accountId,
                               @RequestParam AccountStatus status,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateAccountStatus(accountId, status);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái tài khoản thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=accounts";
    }
}

