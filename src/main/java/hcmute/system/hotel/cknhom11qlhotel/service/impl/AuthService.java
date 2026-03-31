package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.LoginSession;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.TaiKhoan;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.AccountStatus;
import hcmute.system.hotel.cknhom11qlhotel.repository.NhanVienRepository;
import hcmute.system.hotel.cknhom11qlhotel.service.IAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class AuthService implements IAuthService {

    private final NhanVienRepository nhanVienRepository;

    public AuthService(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public Optional<LoginSession> authenticate(String username, String password) {
        if (Stream.of(username, password).anyMatch(this::isBlank)) {
            return Optional.empty();
        }

        String normalizedUsername = username.trim();
        String normalizedPassword = password.trim();

        return nhanVienRepository.findByUsername(normalizedUsername)
                .filter(this::isAccountActive)
                .filter(nhanVien -> normalizedPassword.equals(nhanVien.getTaiKhoan().getPassword()))
                .map(this::toLoginSession);
    }

    private boolean isAccountActive(NhanVien nhanVien) {
        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        return taiKhoan != null && taiKhoan.getTrangThai() == AccountStatus.ACTIVE;
    }

    private LoginSession toLoginSession(NhanVien nhanVien) {
        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        return new LoginSession(
                nhanVien.getId(),
                taiKhoan.getUsername(),
                nhanVien.getTen(),
                nhanVien.getRole());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
