package hcmute.system.hotel.cknhom11qlhotel.service;

import hcmute.system.hotel.cknhom11qlhotel.config.TransactionManager;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.impl.IAuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

	private final TransactionManager transactionManager;
	private final NhanVienRepository nhanVienRepository;

	public AuthService(TransactionManager transactionManager, NhanVienRepository nhanVienRepository) {
		this.transactionManager = transactionManager;
		this.nhanVienRepository = nhanVienRepository;
	}

	@Override
	public Optional<LoginSession> authenticate(String username, String password) {
		if (username == null || password == null || username.isBlank() || password.isBlank()) {
			return Optional.empty();
		}

		String normalized = username.trim();
		try {
			return transactionManager.runInTransaction(em -> {
				Optional<NhanVien> nhanVienOpt = nhanVienRepository.findByUsername(em, normalized);

				if (nhanVienOpt.isEmpty()) {
					return Optional.empty();
				}

				NhanVien nhanVien = nhanVienOpt.get();
				TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
				if (taiKhoan.getTrangThai() != AccountStatus.ACTIVE) {
					return Optional.empty();
				}

				if (!password.equals(taiKhoan.getPassword())) {
					return Optional.empty();
				}

				return Optional.of(new LoginSession(
						nhanVien.getId(),
						taiKhoan.getUsername(),
						nhanVien.getTen(),
						nhanVien.getRole()));
			});
		} catch (Exception ex) {
			throw new IllegalStateException("Khong the dang nhap", ex);
		}
	}
}

