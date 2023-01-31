package com.example.dodam.controller;

import com.example.dodam.domain.user.RegisterRequest;
import com.example.dodam.domain.user.UserResponse;
import com.example.dodam.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(UserResponse.of(registerService.register(request)));
    }

    @PostMapping("/email")
    public ResponseEntity<String> checkEmail(@RequestBody String email) {
        registerService.checkDuplicateEmail(email);
        return ResponseEntity.ok(email);
    }

    @PostMapping("/nickname")
    public ResponseEntity<String> checkNickname(@RequestBody String nickname) {
        registerService.checkDuplicateNickname(nickname);
        return ResponseEntity.ok(nickname);
    }
}
