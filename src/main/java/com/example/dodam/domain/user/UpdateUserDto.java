package com.example.dodam.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private String status;
    private String imgPath;
    private String role;
    private LocalDate birthDate;
    private LocalDateTime updateAt;
    private LocalDateTime startAt;

    public User toEntity() {
        return User.builder()
            .password(password)
            .email(email)
            .phone(phone)
            .nickname(nickname)
            .status(status)
            .imgPath(imgPath)
            .role(role)
            .birthDate(birthDate)
            .updateAt(updateAt)
            .startAt(startAt)
            .build();
    }
}
