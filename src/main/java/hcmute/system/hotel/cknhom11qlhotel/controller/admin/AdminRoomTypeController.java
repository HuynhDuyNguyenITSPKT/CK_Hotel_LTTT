package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomTypeRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.IQuanTriTongHopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminRoomTypeController {

    private final IQuanTriTongHopService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminRoomTypeController(IQuanTriTongHopService adminManagementService,
                                   AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/room-types")
    public String createRoomType(RoomTypeRequest request,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createRoomType(request, imageFile);
            redirectAttributes.addFlashAttribute("success", "Thêm loại phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi upload ảnh loại phòng. Vui lòng kiểm tra cấu hình Cloudinary.");
        }

        return "redirect:/admin/dashboard?tab=room-types";
    }

    @PostMapping("/admin/room-types/{roomTypeId}/update")
    public String updateRoomType(@PathVariable Long roomTypeId,
                                 RoomTypeRequest request,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateRoomType(roomTypeId, request, imageFile);
            redirectAttributes.addFlashAttribute("success", "Cập nhật loại phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi upload ảnh loại phòng. Vui lòng kiểm tra cấu hình Cloudinary.");
        }

        return "redirect:/admin/dashboard?tab=room-types";
    }

    @PostMapping("/admin/room-types/{roomTypeId}/delete")
    public String deleteRoomType(@PathVariable Long roomTypeId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.deleteRoomType(roomTypeId);
            redirectAttributes.addFlashAttribute("success", "Xóa loại phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=room-types";
    }
}
