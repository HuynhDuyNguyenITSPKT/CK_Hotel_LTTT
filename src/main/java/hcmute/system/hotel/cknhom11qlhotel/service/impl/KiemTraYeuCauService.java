package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.CreateEmployeeForm;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.CustomerRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.PromotionRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.RoomTypeRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.admin.ServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class KiemTraYeuCauService {

    public void validateCreateForm(CreateEmployeeForm form) {
        if (form == null
                || isBlank(form.getEmployeeName())
                || isBlank(form.getUsername())
                || isBlank(form.getEmail())
                || isBlank(form.getPassword())
                || form.getRole() == null) {
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin");
        }
    }

    public void validateRoomRequest(RoomRequest request) {
        if (request == null || isBlank(request.getSoPhong()) || request.getTrangThai() == null || request.getLoaiPhongId() == null) {
            throw new IllegalArgumentException("Dữ liệu phòng không hợp lệ");
        }
    }

    public void validateRoomTypeRequest(RoomTypeRequest request) {
        if (request == null || isBlank(request.getTenLoai()) || request.getGiaCoBan() == null || request.getGiaCoBan().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu loại phòng không hợp lệ");
        }
    }

    public void validateServiceRequest(ServiceRequest request) {
        if (request == null || isBlank(request.getTen()) || request.getGia() == null || request.getGia().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu dịch vụ không hợp lệ");
        }
    }

    public void validatePromotionRequest(PromotionRequest request) {
        if (request == null || isBlank(request.getTen()) || request.getLoaiGiam() == null
                || request.getGiaTri() == null || request.getGiaTri().signum() <= 0) {
            throw new IllegalArgumentException("Dữ liệu khuyến mãi không hợp lệ");
        }
    }

    public void validateCustomerRequest(CustomerRequest request) {
        if (request == null || isBlank(request.getTen()) || isBlank(request.getSdt())) {
            throw new IllegalArgumentException("Dữ liệu khách hàng không hợp lệ");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
