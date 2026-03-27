package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.LeTanTaoDatPhongFormDto;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist.TrangDuLieuDto;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.PaymentMethod;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.RoomStatus;
import hcmute.system.hotel.cknhom11qlhotel.service.IReceptionistDashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistDashboardController {

    private final IReceptionistDashboardService receptionistDashboardService;
    private final ReceptionistAuthorizationSupport authorizationSupport;

    public ReceptionistDashboardController(IReceptionistDashboardService receptionistDashboardService,
                                           ReceptionistAuthorizationSupport authorizationSupport) {
        this.receptionistDashboardService = receptionistDashboardService;
        this.authorizationSupport = authorizationSupport;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(defaultValue = "tong-quan") String tab,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "8") int size,
                            HttpSession session,
                            Model model) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        String activeTab = normalizeTab(tab);
        int safeSize = Math.max(3, Math.min(size, 20));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("size", safeSize);
        model.addAttribute("thongKeNhanh", receptionistDashboardService.layThongKeNhanh());
        model.addAttribute("roomStatuses", RoomStatus.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("phongChoDat", receptionistDashboardService.layPhongChoDatPhong());
        model.addAttribute("danhSachDichVu", receptionistDashboardService.layDanhSachDichVu());

        if (!model.containsAttribute("bookingForm")) {
            model.addAttribute("bookingForm", new LeTanTaoDatPhongFormDto());
        }

        switch (activeTab) {
            case "check-in" -> {
                TrangDuLieuDto<?> duLieu = receptionistDashboardService.layTrangCheckIn(page, safeSize);
                model.addAttribute("danhSachCheckIn", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "check-out" -> {
                TrangDuLieuDto<?> duLieu = receptionistDashboardService.layTrangCheckOut(page, safeSize);
                model.addAttribute("danhSachCheckOut", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "quan-ly-phong" -> {
                TrangDuLieuDto<?> duLieu = receptionistDashboardService.layTrangQuanLyPhong(page, safeSize);
                model.addAttribute("danhSachPhong", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "dat-phong" -> {
                TrangDuLieuDto<?> duLieu = receptionistDashboardService.layTrangDatPhong(page, safeSize);
                model.addAttribute("danhSachDatPhong", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            case "hoa-don-thanh-toan" -> {
                TrangDuLieuDto<?> duLieu = receptionistDashboardService.layTrangHoaDonThanhToan(page, safeSize);
                model.addAttribute("danhSachHoaDonThanhToan", duLieu.getDanhSach());
                themThongTinPhanTrang(model, duLieu);
            }
            default -> {
                model.addAttribute("danhSachCheckInNhanh", receptionistDashboardService.layCheckInSapToi(5));
                model.addAttribute("danhSachCheckOutNhanh", receptionistDashboardService.layCheckOutSapToi(5));
                model.addAttribute("danhSachDatPhongNhanh", receptionistDashboardService.layDatPhongGanNhat(6));
            }
        }

        return "receptionist/dashboard";
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
            receptionistDashboardService.thucHienCheckIn(datPhongId, currentUser.getEmployeeId());
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
            receptionistDashboardService.thucHienCheckOut(datPhongId, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Check-out thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=check-out";
    }

    @PostMapping("/thanh-toan/{datPhongId}")
    public String thanhToan(@PathVariable Long datPhongId,
                            @RequestParam BigDecimal soTienThanhToan,
                            @RequestParam PaymentMethod phuongThuc,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            receptionistDashboardService.thucHienThanhToan(datPhongId, soTienThanhToan, phuongThuc, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Ghi nhận thanh toán thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=check-out";
    }

    @PostMapping("/dat-phong/{datPhongId}/dich-vu")
    public String themDichVu(@PathVariable Long datPhongId,
                             @RequestParam Long dichVuId,
                             @RequestParam Integer soLuong,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        LoginSession currentUser = authorizationSupport.requireReceptionist(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            receptionistDashboardService.themDichVuTrongThoiGianO(datPhongId, dichVuId, soLuong, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Đã thêm dịch vụ cho khách đang lưu trú");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=check-out";
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
            receptionistDashboardService.capNhatTrangThaiPhong(phongId, trangThai);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/receptionist/dashboard?tab=quan-ly-phong";
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
            receptionistDashboardService.taoDatPhong(bookingForm, currentUser.getEmployeeId());
            redirectAttributes.addFlashAttribute("success", "Tạo đặt phòng thành công");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("bookingForm", bookingForm);
        }

        return "redirect:/receptionist/dashboard?tab=dat-phong";
    }

    private void themThongTinPhanTrang(Model model, TrangDuLieuDto<?> duLieu) {
        model.addAttribute("page", duLieu.getTrangHienTai());
        model.addAttribute("totalPages", duLieu.getTongTrang());
        model.addAttribute("totalItems", duLieu.getTongBanGhi());
        model.addAttribute("hasPrev", duLieu.isCoTrangTruoc());
        model.addAttribute("hasNext", duLieu.isCoTrangSau());
    }

    private String normalizeTab(String tab) {
        return switch (tab) {
            case "tong-quan", "check-in", "check-out", "quan-ly-phong", "dat-phong", "hoa-don-thanh-toan" -> tab;
            default -> "tong-quan";
        };
    }
}
