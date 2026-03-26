package hcmute.system.hotel.cknhom11qlhotel.controller.api.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ApiMessage;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AdminApiSupport {

    public ResponseEntity<ApiMessage> forbiddenIfNotAdmin(HttpSession session) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiMessage("Bạn không có quyền truy cập"));
        }
        return null;
    }
}

