package org.project.final_backend.service;

import org.project.final_backend.domain.request.otp.NewOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;

public interface AuthService {
    void generateOTP(NewOTPRequest request);

        boolean validateOTP(ResetPasswordOTPRequest request);
//    boolean validateOTP(NewPasswordRequest request);
}
