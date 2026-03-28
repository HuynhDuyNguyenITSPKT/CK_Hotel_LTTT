package hcmute.system.hotel.cknhom11qlhotel.service.impl;

import hcmute.system.hotel.cknhom11qlhotel.service.CloudinaryUploadService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AdminMediaSupport {

    private final CloudinaryUploadService cloudinaryUploadService;

    public AdminMediaSupport(CloudinaryUploadService cloudinaryUploadService) {
        this.cloudinaryUploadService = cloudinaryUploadService;
    }

    public String resolveImageUrl(String fallbackUrl, MultipartFile imageFile, String folder) {
        String uploadedImageUrl = cloudinaryUploadService.uploadImage(imageFile, folder);
        if (!isBlank(uploadedImageUrl)) {
            return uploadedImageUrl;
        }
        return trimToNull(fallbackUrl);
    }

    public String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.equalsIgnoreCase("null") || trimmed.equalsIgnoreCase("undefined")) {
            return null;
        }
        return trimmed.isEmpty() ? null : trimmed;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
