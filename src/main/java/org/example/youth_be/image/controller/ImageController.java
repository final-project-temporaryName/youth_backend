package org.example.youth_be.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.youth_be.image.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/profile")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile multipartFile) {
        return ResponseEntity.ok(imageService.uploadImage(multipartFile));
    }
}
