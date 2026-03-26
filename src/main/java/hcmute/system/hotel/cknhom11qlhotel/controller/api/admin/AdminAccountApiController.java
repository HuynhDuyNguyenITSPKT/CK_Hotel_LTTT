package hcmute.system.hotel.cknhom11qlhotel.controller.api.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.AdminUpdateAccountStatusRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ApiMessage;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/accounts")
public class AdminAccountApiController {

    private final IAdminManagementService adminManagementService;
    private final AdminApiSupport adminApiSupport;

    public AdminAccountApiController(IAdminManagementService adminManagementService,
                                     AdminApiSupport adminApiSupport) {
        this.adminManagementService = adminManagementService;
        this.adminApiSupport = adminApiSupport;
    }

    @GetMapping
    public ResponseEntity<?> getAccounts(HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getAccounts());
    }

    @PutMapping("/{accountId}/status")
    public ResponseEntity<?> updateAccountStatus(@PathVariable Long accountId,
                                                 @Valid @RequestBody AdminUpdateAccountStatusRequest request,
                                                 HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        adminManagementService.updateAccountStatus(accountId, request.status());
        return ResponseEntity.ok(new ApiMessage("Cập nhật trạng thái tài khoản thành công"));
    }
}

