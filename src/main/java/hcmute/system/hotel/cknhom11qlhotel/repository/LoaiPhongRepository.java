package hcmute.system.hotel.cknhom11qlhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.system.hotel.cknhom11qlhotel.model.entity.LoaiPhong;

public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {

	boolean existsByTenLoaiIgnoreCase(String tenLoai);
}

