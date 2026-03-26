package hcmute.system.hotel.cknhom11qlhotel.model.dto.api;

import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record AdminUpdateAccountStatusRequest(
        @NotNull(message = "Trạng thái không được để trống")
        AccountStatus status
) {
}

