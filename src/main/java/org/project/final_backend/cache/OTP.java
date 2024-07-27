package org.project.final_backend.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OTP {
    private String email;
    private String otp;
    private LocalDateTime expiredTIme;
}