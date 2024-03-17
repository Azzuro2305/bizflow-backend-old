package org.project.final_backend.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordOTPRequest {
    private String mail;
    private String otp;
    private String newPassword;
}
