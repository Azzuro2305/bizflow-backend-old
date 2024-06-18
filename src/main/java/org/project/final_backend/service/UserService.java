package org.project.final_backend.service;

import org.project.final_backend.domain.request.password.*;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.domain.subscribe.Subscribe;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Users findUsersById(UUID id);

    NewUserResponse updateUser(UUID id, UpdateUserRequest request);
    UserInfo retrieveUserInfo(UUID id);
    UserInfo retrieveUserInfoByUserName(String userName);
    void purchase(Subscribe subscribe);
    void resetPasswordWithUserId(UUID id, ResetPasswordUserIdRequest request);

    void deleteUser(UUID id);
    Page<Users> getAllUsers(Pageable pageable,String[] sort);


}
