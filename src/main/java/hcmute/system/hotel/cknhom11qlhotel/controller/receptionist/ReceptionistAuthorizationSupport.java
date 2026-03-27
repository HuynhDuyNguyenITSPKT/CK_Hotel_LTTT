package hcmute.system.hotel.cknhom11qlhotel.controller.receptionist;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import hcmute.system.hotel.cknhom11qlhotel.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class ReceptionistAuthorizationSupport {

    public LoginSession requireReceptionist(HttpSession session) {
        LoginSession currentUser = (LoginSession) session.getAttribute(SessionKeys.CURRENT_USER);
        if (currentUser == null || currentUser.getRole() != EmployeeRole.RECEPTIONIST) {
            return null;
        }
        return currentUser;
    }
}
