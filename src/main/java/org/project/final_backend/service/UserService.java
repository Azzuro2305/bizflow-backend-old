package org.project.final_backend.service;

import org.project.final_backend.domain.request.NewUserRequest;
import org.project.final_backend.domain.request.UpdateUserRequest;
import org.project.final_backend.domain.response.NewUserResponse;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.domain.request.ValidateUserRequest;
import org.project.final_backend.entity.Users;

import java.util.UUID;

public interface UserService {
    Users findUsersById(UUID id);
    UserInfo validateUser(ValidateUserRequest request);
    NewUserResponse registerUser(NewUserRequest request);
    NewUserResponse updateUser(UUID id, UpdateUserRequest request);
    UserInfo retrieveUserInfo(UUID id);
    void deleteUser(UUID id);
}
