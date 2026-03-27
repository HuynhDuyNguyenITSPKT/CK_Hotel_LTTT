package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;

import java.util.Optional;

public interface IAuthService {
	Optional<LoginSession> authenticate(String username, String password);
}
