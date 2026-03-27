package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {

	boolean existsByTenLoaiIgnoreCase(String tenLoai);
}

