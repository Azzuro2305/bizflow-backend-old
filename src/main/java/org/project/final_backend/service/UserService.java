package org.project.final_backend.service;

import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.ResetPasswordUserIdRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.domain.response.VerifyMailResponse;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.domain.subscribe.Subscribe;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Users findUsersById(UUID id);
    UserInfo validateUser(ValidateUserRequest request);
    NewUserResponse registerUser(NewUserRequest request);
    NewUserResponse updateUser(UUID id, UpdateUserRequest request);
    UserInfo retrieveUserInfo(UUID id);
    UserInfo retrieveUserInfoByUserName(String userName);
    void purchase(Subscribe subscribe);
    void resetPassword(ResetPasswordRequest request);
    void resetPasswordWithUserId(UUID id, ResetPasswordUserIdRequest request);
    void resetPasswordWithOTP(UUID id, ResetPasswordOTPRequest request);
    void deleteUser(UUID id);
    Page<Users> getAllUsers(Pageable pageable);
    VerifyMailResponse verifyMail(VerifyMailRequest request);
}
