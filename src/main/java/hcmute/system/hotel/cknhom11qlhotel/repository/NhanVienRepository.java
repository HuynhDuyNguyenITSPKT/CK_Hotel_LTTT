package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository {

    long countByRole(EntityManager em, EmployeeRole role);

    long countByTaiKhoanId(EntityManager em, Long taiKhoanId);

    Optional<NhanVien> findByTaiKhoanId(EntityManager em, Long taiKhoanId);

    Optional<NhanVien> findByUsername(EntityManager em, String username);

    Optional<NhanVien> findById(EntityManager em, Long id);

    List<NhanVien> findAllWithTaiKhoan(EntityManager em);

    void save(EntityManager em, NhanVien nhanVien);
}

