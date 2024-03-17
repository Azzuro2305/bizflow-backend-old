package org.project.final_backend.service;

import org.project.final_backend.domain.request.NewOTPRequest;
import org.project.final_backend.domain.request.ResetPasswordOTPRequest;

public interface AuthService {
    void generateOTP(NewOTPRequest request);

    boolean validateOTP(ResetPasswordOTPRequest request);
}
