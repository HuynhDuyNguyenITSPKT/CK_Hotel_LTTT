package hcmute.system.hotel.cknhom11qlhotel.controller.api.admin;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.ApiMessage;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
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
@RequestMapping("/api/admin/rooms")
public class AdminRoomApiController {

    private final IAdminManagementService adminManagementService;
    private final AdminApiSupport adminApiSupport;

    public AdminRoomApiController(IAdminManagementService adminManagementService,
                                  AdminApiSupport adminApiSupport) {
        this.adminManagementService = adminManagementService;
        this.adminApiSupport = adminApiSupport;
    }

    @GetMapping
    public ResponseEntity<?> getRooms(HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.getRooms());
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomRequest request, HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(adminManagementService.createRoom(request));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomId,
                                        @Valid @RequestBody RoomRequest request,
                                        HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        return ResponseEntity.ok(adminManagementService.updateRoom(roomId, request));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId, HttpSession session) {
        ResponseEntity<ApiMessage> forbidden = adminApiSupport.forbiddenIfNotAdmin(session);
        if (forbidden != null) {
            return forbidden;
        }
        adminManagementService.deleteRoom(roomId);
        return ResponseEntity.ok(new ApiMessage("Xóa phòng thành công"));
    }
}

