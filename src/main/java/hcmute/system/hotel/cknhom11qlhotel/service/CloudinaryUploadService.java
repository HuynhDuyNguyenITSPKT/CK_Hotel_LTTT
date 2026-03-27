package hcmute.system.hotel.cknhom11qlhotel.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryUploadService {

	private final Cloudinary cloudinary;

	@Value("${cloudinary.cloud-name:}")
	private String cloudName;

	@Value("${cloudinary.api-key:}")
	private String apiKey;

	@Value("${cloudinary.api-secret:}")
	private String apiSecret;

	public CloudinaryUploadService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	public String uploadImage(MultipartFile imageFile, String folder) {
		if (imageFile == null || imageFile.isEmpty()) {
			return null;
		}

		if (isBlank(cloudName) || isBlank(apiKey) || isBlank(apiSecret)) {
			throw new IllegalArgumentException("Cloudinary chưa được cấu hình. Vui lòng thiết lập CLOUDINARY_CLOUD_NAME, CLOUDINARY_API_KEY, CLOUDINARY_API_SECRET");
		}

		String contentType = imageFile.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new IllegalArgumentException("File tải lên không phải định dạng ảnh hợp lệ");
		}

		try {
			Map<String, Object> options = new HashMap<>();
			options.put("resource_type", "image");
			if (folder != null && !folder.isBlank()) {
				options.put("folder", folder.trim());
			}

			Map<?, ?> uploaded = cloudinary.uploader().upload(imageFile.getBytes(), options);
			Object secureUrl = uploaded.get("secure_url");
			if (secureUrl == null) {
				throw new IllegalArgumentException("Upload ảnh thất bại, chưa nhận được URL từ Cloudinary");
			}
			return secureUrl.toString();
		} catch (IOException ex) {
			throw new IllegalArgumentException("Không thể upload ảnh lên Cloudinary", ex);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Upload ảnh thất bại. Kiểm tra lại cấu hình Cloudinary hoặc file ảnh", ex);
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
