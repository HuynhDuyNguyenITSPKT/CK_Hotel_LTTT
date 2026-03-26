package hcmute.system.hotel.cknhom11qlhotel.controller.api.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ApiMessage;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/customers")
public class AdminCustomerApiController {

    private final IAdminManagementService adminManagementService;
    private final AdminApiSupport adminApiSupport;

    public AdminCustomerApiController(IAdminManagementService adminManagementService,
                                      AdminApiSupport adminApiSupport) {
        this.adminManagementService = adminManagementService;
        this.adminApiSupport = adminApiSupport;
    }

    @GetMapping
    public ResponseEntity<?> getCustomers(HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getCustomers());
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest request, HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(adminManagementService.createCustomer(request));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId,
                                            @Valid @RequestBody CustomerRequest request,
                                            HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.updateCustomer(customerId, request));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId, HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        adminManagementService.deleteCustomer(customerId);
        return ResponseEntity.ok(new ApiMessage("Xóa khách hàng thành công"));
    }
}

