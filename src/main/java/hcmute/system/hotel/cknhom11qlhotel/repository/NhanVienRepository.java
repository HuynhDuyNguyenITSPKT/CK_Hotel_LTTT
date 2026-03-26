package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.persistence.EntityManager;

public interface NhanVienRepository {

    long countByRole(EntityManager em, EmployeeRole role);

    long countByTaiKhoanId(EntityManager em, Long taiKhoanId);

    void save(EntityManager em, NhanVien nhanVien);
}

