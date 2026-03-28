package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.service.IDatPhongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistDatPhongController {

    private final IDatPhongService datPhongService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistDatPhongController(IDatPhongService datPhongService,
                                          ReceptionistAuthorizationSupport authorizationSupport) {
        this.datPhongService = datPhongService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/bookings")
    public String taoDatPhong(@ModelAttribute("bookingForm") LeTanTaoDatPhongFormDto bookingForm,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            datPhongService.taoDatPhong(bookingForm, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Tạo đặt phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("bookingForm", bookingForm);
        }

        return "redirect:/receptionist/dashboard?tab=dat-phong";
    }

    @PostMapping("/dat-phong/{datPhongId}/huy")
    public String huyDatPhong(@PathVariable Long datPhongId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            datPhongService.huyDatPhong(datPhongId, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Đã chuyển đặt phòng sang trạng thái hủy");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=dat-phong";
    }
}
