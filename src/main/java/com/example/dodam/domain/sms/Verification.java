package com.example.dodam.domain.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@RedisHash(value = "verification")
@AllArgsConstructor
@NoArgsConstructor
public class Verification {

    @Id
    private String id;

    private String code;

    @TimeToLive
    private Long expiration;
    private VerificationStatus status;
}
