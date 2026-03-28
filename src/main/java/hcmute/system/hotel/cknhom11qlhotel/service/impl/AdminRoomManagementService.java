package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomResponse;
import hcmute.system.hotel.cknhom11qlhotel.model.dto.api.RoomTypeRequest;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.LoaiPhong;
import hcmute.system.hotel.cknhom11qlhotel.model.enity.Phong;
import hcmute.system.hotel.cknhom11qlhotel.repository.LoaiPhongRepository;
import hcmute.system.hotel.cknhom11qlhotel.repository.PhongRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@Service
public class AdminRoomManagementService {

    private final PhongRepository phongRepository;
    private final LoaiPhongRepository loaiPhongRepository;
    private final AdminMediaSupport adminMediaSupport;
    private final AdminRequestValidator adminRequestValidator;

    public AdminRoomManagementService(PhongRepository phongRepository,
                                      LoaiPhongRepository loaiPhongRepository,
                                      AdminMediaSupport adminMediaSupport,
                                      AdminRequestValidator adminRequestValidator) {
        this.phongRepository = phongRepository;
        this.loaiPhongRepository = loaiPhongRepository;
        this.adminMediaSupport = adminMediaSupport;
        this.adminRequestValidator = adminRequestValidator;
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getRooms() {
        return phongRepository.findAllByOrderByIdDesc().stream().map(this::toRoomResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LoaiPhong> getRoomTypes() {
        return loaiPhongRepository.findAll().stream()
                .sorted(Comparator.comparing(LoaiPhong::getId))
                .toList();
    }

    @Transactional
    public LoaiPhong createRoomType(RoomTypeRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateRoomTypeRequest(request);

        String tenLoai = request.getTenLoai().trim();
        if (loaiPhongRepository.existsByTenLoaiIgnoreCase(tenLoai)) {
            throw new IllegalArgumentException("Tên loại phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = new LoaiPhong();
        loaiPhong.setTenLoai(tenLoai);
        loaiPhong.setMoTa(adminMediaSupport.trimToNull(request.getMoTa()));
        loaiPhong.setImageUrl(adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/room-types"));
        loaiPhong.setGiaCoBan(request.getGiaCoBan());
        return loaiPhongRepository.save(loaiPhong);
    }

    @Transactional
    public LoaiPhong updateRoomType(Long roomTypeId, RoomTypeRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateRoomTypeRequest(request);

        LoaiPhong loaiPhong = loaiPhongRepository.findById(roomTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        String tenLoai = request.getTenLoai().trim();
        if (!tenLoai.equalsIgnoreCase(loaiPhong.getTenLoai()) && loaiPhongRepository.existsByTenLoaiIgnoreCase(tenLoai)) {
            throw new IllegalArgumentException("Tên loại phòng đã tồn tại");
        }

        String imageUrl = adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/room-types");

        loaiPhong.setTenLoai(tenLoai);
        loaiPhong.setMoTa(adminMediaSupport.trimToNull(request.getMoTa()));
        loaiPhong.setImageUrl(imageUrl != null ? imageUrl : loaiPhong.getImageUrl());
        loaiPhong.setGiaCoBan(request.getGiaCoBan());
        return loaiPhong;
    }

    @Transactional
    public void deleteRoomType(Long roomTypeId) {
        if (!loaiPhongRepository.existsById(roomTypeId)) {
            throw new IllegalArgumentException("Không tìm thấy loại phòng");
        }
        if (phongRepository.existsByLoaiPhong_Id(roomTypeId)) {
            throw new IllegalArgumentException("Không thể xóa loại phòng đang được sử dụng");
        }
        loaiPhongRepository.deleteById(roomTypeId);
    }

    @Transactional
    public RoomResponse createRoom(RoomRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateRoomRequest(request);

        String soPhong = request.getSoPhong().trim();
        if (phongRepository.existsBySoPhong(soPhong)) {
            throw new IllegalArgumentException("Số phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getLoaiPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        Phong phong = new Phong();
        phong.setSoPhong(soPhong);
        phong.setImageUrl(adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/rooms"));
        phong.setTrangThai(request.getTrangThai());
        phong.setLoaiPhong(loaiPhong);
        return toRoomResponse(phongRepository.save(phong));
    }

    @Transactional
    public RoomResponse updateRoom(Long roomId, RoomRequest request, MultipartFile imageFile) {
        adminRequestValidator.validateRoomRequest(request);

        Phong phong = phongRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng"));

        String soPhong = request.getSoPhong().trim();
        if (!soPhong.equalsIgnoreCase(phong.getSoPhong()) && phongRepository.existsBySoPhong(soPhong)) {
            throw new IllegalArgumentException("Số phòng đã tồn tại");
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getLoaiPhongId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));

        String imageUrl = adminMediaSupport.resolveImageUrl(request.getImageUrl(), imageFile, "hotel/rooms");

        phong.setSoPhong(soPhong);
        phong.setImageUrl(imageUrl != null ? imageUrl : phong.getImageUrl());
        phong.setTrangThai(request.getTrangThai());
        phong.setLoaiPhong(loaiPhong);
        return toRoomResponse(phong);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        if (!phongRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Không tìm thấy phòng");
        }
        phongRepository.deleteById(roomId);
    }

    private RoomResponse toRoomResponse(Phong phong) {
        return new RoomResponse(
                phong.getId(),
                phong.getSoPhong(),
                phong.getTrangThai(),
                phong.getImageUrl(),
                phong.getLoaiPhong().getId(),
                phong.getLoaiPhong().getTenLoai(),
                phong.getLoaiPhong().getMoTa());
    }
}
