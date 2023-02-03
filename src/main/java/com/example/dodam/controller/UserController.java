package com.example.dodam.controller;

import com.example.dodam.domain.user.UpdateUserRequest;
import com.example.dodam.domain.user.UserResponse;
import com.example.dodam.service.FileUploadService;
import com.example.dodam.service.UserService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileUploadService fileUploadService;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@RequestParam String email) {
        return ResponseEntity.ok(UserResponse.of(userService.getUser(email)));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@ModelAttribute UpdateUserRequest request) throws IOException {
        String imagePath = fileUploadService.storeFile(request.getProfileImage());
        request.setImgPath(imagePath);
        return ResponseEntity.ok(UserResponse.of(userService.update(request.getOriginalEmail(), request)));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        userService.delete(email);
        return ResponseEntity.ok().build();
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
