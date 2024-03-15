package org.project.final_backend.service;

import org.project.final_backend.domain.NewUserRequest;
import org.project.final_backend.domain.NewUserResponse;
import org.project.final_backend.domain.UserInfo;
import org.project.final_backend.domain.ValidateUserRequest;

public interface UserService {
    UserInfo validateUser(ValidateUserRequest request);
    NewUserResponse registerUser(NewUserRequest request);
}
