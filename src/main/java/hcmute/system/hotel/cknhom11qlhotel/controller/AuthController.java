package hcmute.system.hotel.cknhom11qlhotel.controller;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.service.IAuthService;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser != null) {
            return "redirect:" + defaultPath(currentUser.getRole());
        }

        if (!model.containsAttribute("error")) {
            model.addAttribute("error", "");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        Optional<LoginSession> loginSession = authService.authenticate(username, password);
        if (loginSession.isEmpty()) {
            model.addAttribute("error", "Sai tên đăng nhập, mật khẩu hoặc tài khoản đã bị khóa.");
            return "auth/login";
        }

        session.setAttribute(SessionKeys.CURRENT_USER, loginSession.get());
        return "redirect:" + defaultPath(loginSession.get().getRole());
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    private String defaultPath(EmployeeRole role) {
        return switch (role) {
            case ADMIN -> "/admin/dashboard";
            case MANAGER -> "/manager/dashboard";
            case RECEPTIONIST -> "/receptionist/dashboard";
        };
    }
}

