package org.project.final_backend.service;

import org.project.final_backend.domain.request.otp.NewOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.dto.model.UserInfo;

public interface AuthService {
    boolean isOTPValid(ResetPasswordOTPRequest request);
    boolean registerUser(NewUserRequest request);
    boolean verifyMail(VerifyMailRequest request);
    boolean verifyOTPCode(ResetPasswordOTPRequest request);
    boolean resetPassword(ResetPasswordRequest request);
    boolean resetPasswordWithOTP(ResetPasswordOTPRequest request);

    void generateOTP(NewOTPRequest request);
    void validateOTPOrThrow(ResetPasswordOTPRequest request);

    UserInfo validateUser(ValidateUserRequest request);


}
