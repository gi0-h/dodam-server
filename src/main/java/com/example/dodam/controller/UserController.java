package com.example.dodam.controller;

import com.example.dodam.config.auth.PrincipalDetails;
import com.example.dodam.domain.user.UpdateUserRequest;
import com.example.dodam.domain.user.User;
import com.example.dodam.domain.user.UserResponse;
import com.example.dodam.service.user.FileUploadService;
import com.example.dodam.service.user.UserService;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileUploadService fileUploadService;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(Authentication authentication) {
        return ResponseEntity.ok(UserResponse.of(getPrincipalUser(authentication)));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@ModelAttribute UpdateUserRequest request, Authentication authentication) throws IOException {
        User user = getPrincipalUser(authentication);
        String imagePath = fileUploadService.storeFile(request.getProfileImage());
        request.setImgPath(imagePath);
        fileUploadService.deleteFile(user.getImgPath());
        return ResponseEntity.ok(UserResponse.of(userService.update(user.getEmail(), request)));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.delete(getPrincipalUser(authentication).getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/images/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource downloadImage(@PathVariable String filename) throws
        MalformedURLException {
        return new UrlResource("file:" + fileUploadService.getFullPath(filename));
    }

    @DeleteMapping("/images")
    public ResponseEntity<Void> deleteImage(Authentication authentication) {
        User user = getPrincipalUser(authentication);
        userService.deleteImage(user.getId());
        fileUploadService.deleteFile(user.getImgPath());
        return ResponseEntity.ok().build();
    }

    private User getPrincipalUser(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }
}
