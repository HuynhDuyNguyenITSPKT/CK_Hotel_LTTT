package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.service.ICheckInOutService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistCheckInOutController {

    private final ICheckInOutService checkInOutService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistCheckInOutController(ICheckInOutService checkInOutService,
                                            ReceptionistAuthorizationSupport authorizationSupport) {
        this.checkInOutService = checkInOutService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/check-in/{datPhongId}")
    public String checkIn(@PathVariable Long datPhongId,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            checkInOutService.thucHienCheckIn(datPhongId, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Check-in thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=check-in";
    }

    @PostMapping("/check-out/{datPhongId}")
    public String checkOut(@PathVariable Long datPhongId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            checkInOutService.thucHienCheckOut(datPhongId, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Check-out thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=check-out";
    }
}
