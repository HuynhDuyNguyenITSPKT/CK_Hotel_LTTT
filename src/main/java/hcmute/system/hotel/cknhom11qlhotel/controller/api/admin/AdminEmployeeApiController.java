package hcmute.system.hotel.cknhom11qlhotel.controller.api.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.AdminCreateEmployeeRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.AdminUpdateEmployeeRoleRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ApiMessage;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAdminManagementService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/employees")
public class AdminEmployeeApiController {

    private final IAdminManagementService adminManagementService;
    private final AdminApiSupport adminApiSupport;

    public AdminEmployeeApiController(IAdminManagementService adminManagementService,
                                      AdminApiSupport adminApiSupport) {
        this.adminManagementService = adminManagementService;
        this.adminApiSupport = adminApiSupport;
    }

    @GetMapping
    public ResponseEntity<?> getEmployees(HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getEmployees());
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody AdminCreateEmployeeRequest request,
                                            HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        CreateEmployeeForm form = new CreateEmployeeForm();
        form.setEmployeeName(request.employeeName());
        form.setRole(request.role());
        form.setUsername(request.username());
        form.setEmail(request.email());
        form.setPassword(request.password());

        adminManagementService.createEmployeeWithAccount(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiMessage("Tạo nhân viên thành công"));
    }

    @PutMapping("/{employeeId}/role")
    public ResponseEntity<?> updateEmployeeRole(@PathVariable Long employeeId,
                                                @Valid @RequestBody AdminUpdateEmployeeRoleRequest request,
                                                HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }

        adminManagementService.updateEmployeeRole(employeeId, request.role());
        return ResponseEntity.ok(new ApiMessage("Cập nhật vai trò nhân viên thành công"));
    }
}

