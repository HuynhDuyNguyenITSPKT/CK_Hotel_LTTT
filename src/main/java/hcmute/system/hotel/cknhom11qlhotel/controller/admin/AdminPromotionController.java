package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminPromotionController {

    private final IAdminManagementService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminPromotionController(IAdminManagementService adminManagementService,
                                    AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/promotions")
    public String createPromotion(PromotionRequest request,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createPromotion(request);
            redirectAttributes.addFlashAttribute("success", "Thêm khuyến mãi thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=promotions";
    }

    @PostMapping("/admin/promotions/{promotionId}/update")
    public String updatePromotion(@PathVariable Long promotionId,
                                  PromotionRequest request,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updatePromotion(promotionId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật khuyến mãi thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=promotions";
    }

    @PostMapping("/admin/promotions/{promotionId}/delete")
    public String deletePromotion(@PathVariable Long promotionId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.deletePromotion(promotionId);
            redirectAttributes.addFlashAttribute("success", "Xóa khuyến mãi thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=promotions";
    }
}

