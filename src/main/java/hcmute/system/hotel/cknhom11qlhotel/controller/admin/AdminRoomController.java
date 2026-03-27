package hcmute.system.hotel.cknhom11qlhotel.controller.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminRoomController {

    private final IAdminManagementService adminManagementService;
    private final AdminAuthorizationSupport authorizationSupport;

    public AdminRoomController(IAdminManagementService adminManagementService,
                               AdminAuthorizationSupport authorizationSupport) {
        this.adminManagementService = adminManagementService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/admin/rooms")
    public String createRoom(RoomRequest request,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.createRoom(request);
            redirectAttributes.addFlashAttribute("success", "Thêm phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=rooms";
    }

    @PostMapping("/admin/rooms/{roomId}/update")
    public String updateRoom(@PathVariable Long roomId,
                             RoomRequest request,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.updateRoom(roomId, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=rooms";
    }

    @PostMapping("/admin/rooms/{roomId}/delete")
    public String deleteRoom(@PathVariable Long roomId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireAdmin(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            adminManagementService.deleteRoom(roomId);
            redirectAttributes.addFlashAttribute("success", "Xóa phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard?tab=rooms";
    }
}

