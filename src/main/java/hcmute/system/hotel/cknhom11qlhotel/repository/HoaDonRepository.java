package hcmute.system.hotel.cknhom11qlhotel.repository;

import hcmute.system.hotel.cknhom11qlhotel.model.enity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("select coalesce(sum(h.tongTien), 0) from HoaDon h")
    BigDecimal sumTongTien();
}

