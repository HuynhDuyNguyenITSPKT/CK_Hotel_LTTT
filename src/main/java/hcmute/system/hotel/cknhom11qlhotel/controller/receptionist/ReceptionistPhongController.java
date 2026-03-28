package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IPhongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistPhongController {

    private final IPhongService phongService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistPhongController(IPhongService phongService,
                                       ReceptionistAuthorizationSupport authorizationSupport) {
        this.phongService = phongService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/rooms/{phongId}/status")
    public String capNhatTrangThaiPhong(@PathVariable Long phongId,
                                        @RequestParam RoomStatus trangThai,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            phongService.capNhatTrangThaiPhong(phongId, trangThai);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=quan-ly-phong";
    }
}
