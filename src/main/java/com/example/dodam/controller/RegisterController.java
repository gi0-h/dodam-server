package com.example.dodam.controller;

import com.example.dodam.domain.user.RegisterRequest;
import com.example.dodam.domain.user.UserResponse;
import com.example.dodam.service.FileUploadService;
import com.example.dodam.service.RegisterService;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/register")
public class RegisterController {

    private final RegisterService registerService;
    private final FileUploadService fileUploadService;

    public RegisterController(RegisterService registerService, FileUploadService fileUploadService) {
        this.registerService = registerService;
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@ModelAttribute RegisterRequest request) throws IOException {
        String imagePath = fileUploadService.storeFile(request.getProfileImage());
        request.setImagePath(imagePath);
        try {
            return ResponseEntity.ok(UserResponse.of(registerService.register(request)));
        } catch (Exception e) {
            fileUploadService.deleteFile(imagePath);
            throw e;
        }
    }

    @PostMapping("/email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        registerService.checkDuplicateEmail(email);
        return ResponseEntity.ok(email);
    }

    @PostMapping("/nickname")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        registerService.checkDuplicateNickname(nickname);
        return ResponseEntity.ok(nickname);
    }

    @GetMapping(value = "/images/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource downloadImage(@PathVariable String filename) throws
        MalformedURLException {
        return new UrlResource("file:" + fileUploadService.getFullPath(filename));
    }

    @DeleteMapping("/images/{filename}")
    public ResponseEntity<Void> deleteImage(@PathVariable String filename) {
        fileUploadService.deleteFile(filename);
        return ResponseEntity.ok().build();
    }
}
