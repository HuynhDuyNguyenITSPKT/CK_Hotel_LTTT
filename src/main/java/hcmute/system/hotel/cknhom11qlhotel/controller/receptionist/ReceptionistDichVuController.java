package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.service.IDichVuService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistDichVuController {

    private final IDichVuService dichVuService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistDichVuController(IDichVuService dichVuService,
                                        ReceptionistAuthorizationSupport authorizationSupport) {
        this.dichVuService = dichVuService;
        this.authorizationSupport = authorizationSupport;
    }

    @PostMapping({"/dang-o/{datPhongId}/dich-vu", "/dat-phong/{datPhongId}/dich-vu"})
    public String themDichVu(@PathVariable Long datPhongId,
                             @RequestParam Long dichVuId,
                             @RequestParam Integer soLuong,
                             @RequestParam String phongApDung,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            String phongApDungChuan = phongApDung == null ? "" : phongApDung.trim();
            boolean apDungTatCaPhong = "ALL".equalsIgnoreCase(phongApDungChuan);
            Long phongId = null;
            if (!apDungTatCaPhong) {
                if (phongApDungChuan.isBlank()) {
                    throw new IllegalArgumentException("Vui lòng chọn phòng áp dụng dịch vụ");
                }
                try {
                    phongId = Long.parseLong(phongApDungChuan);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Phòng áp dụng dịch vụ không hợp lệ");
                }
            }

            dichVuService.themDichVuTrongThoiGianO(
                    datPhongId,
                    dichVuId,
                    soLuong,
                    phongId,
                    apDungTatCaPhong,
                    currentUser.getEmployeeId()
            );
            redirectAttributes.addFlashAttribute("success", "Đã thêm dịch vụ cho khách đang lưu trú");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=dang-o-them-dich-vu&selectedBookingId=" + datPhongId;
    }

    @PostMapping("/dich-vu-su-dung/{suDungDichVuId}/xoa")
    public String xoaDichVuDaThem(@PathVariable Long suDungDichVuId,
                                  @RequestParam(defaultValue = "dang-o-them-dich-vu") String redirectTab,
                                  @RequestParam(required = false) Long selectedBookingId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            dichVuService.xoaDichVuDaThem(suDungDichVuId, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Đã xóa dịch vụ và cập nhật lại tổng tiền");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        String redirect = "redirect:/receptionist/dashboard?tab=" + normalizeTab(redirectTab);
        if (selectedBookingId != null) {
            redirect += "&selectedBookingId=" + selectedBookingId;
        }
        return redirect;
    }

    private String normalizeTab(String tab) {
        return switch (tab) {
            case "tong-quan", "check-in", "check-out", "quan-ly-phong", "dat-phong", "dang-o-them-dich-vu", "hoa-don-thanh-toan" -> tab;
            default -> "tong-quan";
        };
    }
}
