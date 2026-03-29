package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanKhachHangTraCuuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.service.IDatPhongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

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

    @GetMapping("/customers/by-phone")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> timKhachHangTheoSdt(@RequestParam String sdt,
                                                                   HttpSession session) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "Phiên đăng nhập đã hết hạn"
            ));
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("success", true);
        payload.put("exists", false);

        datPhongService.timKhachHangTheoSdt(sdt).ifPresent(khachHang -> {
            payload.put("exists", true);
            payload.put("customer", toCustomerPayload(khachHang));
        });

        return ResponseEntity.ok(payload);
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

    private Map<String, Object> toCustomerPayload(LeTanKhachHangTraCuuDto khachHang) {
        Map<String, Object> customer = new LinkedHashMap<>();
        customer.put("id", khachHang.getKhachHangId());
        customer.put("tenKhachHang", khachHang.getTenKhachHang());
        customer.put("sdt", khachHang.getSdt());
        customer.put("email", khachHang.getEmail());
        return customer;
    }
}
