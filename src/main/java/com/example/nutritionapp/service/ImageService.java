package com.example.nutritionapp.service;

import com.example.nutritionapp.http.response.MinioObjectResponse;
import com.example.nutritionapp.utils.IPAddressUtil;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final MinioService minioService;
    @Value("${spring.minio.bucket}")
    private String bucketName;
    @Value("${spring.minio.port}")
    private String minioPort;

    public String getObjectUrl(String objectName) {
        try {
            return IPAddressUtil.getIPAddress() + ":" + minioPort + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MinioObjectResponse addImage(MultipartFile file) {
        Path path = Path.of(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());

            String objectUrl = getObjectUrl(file.getOriginalFilename());


            return new MinioObjectResponse(objectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
