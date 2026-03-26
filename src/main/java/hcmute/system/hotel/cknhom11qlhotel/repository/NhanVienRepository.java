package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.NhanVien;
import hcmute.system.hotel.cknhom11qlhotel.model.enums.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    long countByRole(EmployeeRole role);

    long countByTaiKhoanId(Long taiKhoanId);

    @Query("select n from NhanVien n join fetch n.taiKhoan t where t.id = :taiKhoanId")
    Optional<NhanVien> findByTaiKhoanId(Long taiKhoanId);

    @Query("select n from NhanVien n join fetch n.taiKhoan t where lower(t.username) = lower(:username)")
    Optional<NhanVien> findByUsername(String username);

    @Query("select n from NhanVien n join fetch n.taiKhoan t where n.id = :id")
    Optional<NhanVien> findByIdWithTaiKhoan(Long id);

    @Query("select n from NhanVien n join fetch n.taiKhoan t order by n.id desc")
    List<NhanVien> findAllWithTaiKhoan();
}
