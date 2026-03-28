package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.service.IThanhToanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistThanhToanController {

    private final IThanhToanService thanhToanService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistThanhToanController(IThanhToanService thanhToanService,
                                           ReceptionistAuthorizationSupport authorizationSupport) {
        this.thanhToanService = thanhToanService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping("/thanh-toan/{datPhongId}")
    public String thanhToan(@PathVariable Long datPhongId,
                            @RequestParam BigDecimal soTienThanhToan,
                            @RequestParam PaymentMethod phuongThuc,
                            @RequestParam(defaultValue = "check-out") String redirectTab,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            thanhToanService.thucHienThanhToan(datPhongId, soTienThanhToan, phuongThuc, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Ghi nhận thanh toán thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=" + normalizeTab(redirectTab);
    }

    @PostMapping("/dat-phong/{datPhongId}/khuyen-mai")
    public String apDungKhuyenMai(@PathVariable Long datPhongId,
                                  @RequestParam(required = false) String maKhuyenMai,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            thanhToanService.apDungMaKhuyenMai(datPhongId, maKhuyenMai, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Cập nhật mã khuyến mãi thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=hoa-don-thanh-toan";
    }

    @PostMapping("/dat-phong/{datPhongId}/khuyen-mai-thanh-toan")
    public String apDungKhuyenMaiVaThanhToan(@PathVariable Long datPhongId,
                                             @RequestParam(required = false) String maKhuyenMai,
                                             @RequestParam BigDecimal soTienThanhToan,
                                             @RequestParam PaymentMethod phuongThuc,
                                             HttpSession session,
                                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            thanhToanService.apDungKhuyenMaiVaThanhToan(
                    datPhongId,
                    maKhuyenMai,
                    soTienThanhToan,
                    phuongThuc,
                    currentUser.getEmployeeId()
            );
            redirectAttributes.addFlashAttribute("success", "Áp dụng khuyến mãi và ghi nhận thanh toán thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=hoa-don-thanh-toan";
    }

    private String normalizeTab(String tab) {
        return switch (tab) {
            case "tong-quan", "check-in", "check-out", "quan-ly-phong", "dat-phong", "dang-o-them-dich-vu", "hoa-don-thanh-toan" -> tab;
            default -> "tong-quan";
        };
    }
}
