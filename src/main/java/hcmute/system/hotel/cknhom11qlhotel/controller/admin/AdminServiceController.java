package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ServiceRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.IQuanTriTongHopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminServiceController {

    private final IQuanTriTongHopService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminServiceController(IQuanTriTongHopService adminManagementService,
                                  AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/services")
    public String createService(ServiceRequest request,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createService(request, imageFile);
            redirectAttributes.addFlashAttribute("success", "Thêm dịch vụ thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi upload ảnh dịch vụ. Vui lòng kiểm tra cấu hình Cloudinary.");
        }

        return "redirect:/admin/dashboard?tab=services";
    }

    @PostMapping("/admin/services/{serviceId}/update")
    public String updateService(@PathVariable Long serviceId,
                                ServiceRequest request,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateService(serviceId, request, imageFile);
            redirectAttributes.addFlashAttribute("success", "Cập nhật dịch vụ thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi upload ảnh dịch vụ. Vui lòng kiểm tra cấu hình Cloudinary.");
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

