package com.example.nutritionapp.controller;

import com.example.nutritionapp.http.response.MinioObjectResponse;
import com.example.nutritionapp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public MinioObjectResponse addImage(@RequestBody MultipartFile multipartFile) {
        return imageService.addImage(multipartFile);
    }
}
