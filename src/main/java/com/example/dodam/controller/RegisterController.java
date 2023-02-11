package com.example.dodam.controller;

import com.example.dodam.domain.sms.dto.SmsResponse;
import com.example.dodam.domain.sms.dto.VerificationRequest;
import com.example.dodam.domain.sms.dto.VerificationResponse;
import com.example.dodam.domain.user.RegisterRequest;
import com.example.dodam.domain.user.UserResponse;
import com.example.dodam.service.user.FileUploadService;
import com.example.dodam.service.user.RegisterService;
import com.example.dodam.service.user.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user/register")
public class RegisterController {

    private final RegisterService registerService;
    private final FileUploadService fileUploadService;
    private final SmsService smsService;

    public RegisterController(RegisterService registerService, FileUploadService fileUploadService,
        SmsService smsService) {
        this.registerService = registerService;
        this.fileUploadService = fileUploadService;
        this.smsService = smsService;
    }

    @PostMapping("/sms")
    public ResponseEntity<SmsResponse> getSms(@RequestParam String phone)
        throws URISyntaxException, JsonProcessingException {
        SmsResponse response = smsService.sendSms(phone);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerificationResponse> verifyNumber(@RequestBody VerificationRequest request) {
        log.debug("request={} {}", request.getCode(), request.getPhone());
        VerificationResponse response = smsService.verifyCode(request.getPhone(), request.getCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@ModelAttribute RegisterRequest request) throws IOException {
        String imagePath = fileUploadService.storeFile(request.getProfileImage());
        request.setImagePath(imagePath);
        try {
            smsService.checkPhone(request.getPhone());
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
}
