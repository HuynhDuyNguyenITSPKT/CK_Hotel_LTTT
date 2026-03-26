package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public class AdminUpdateAccountStatusRequest {

    @NotNull(message = "Trạng thái không được để trống")
    private AccountStatus status;

    public AdminUpdateAccountStatusRequest() {
    }

    public AdminUpdateAccountStatusRequest(AccountStatus status) {
        this.status = status;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
