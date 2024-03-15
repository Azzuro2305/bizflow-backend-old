package org.project.final_backend.service;

import org.project.final_backend.domain.NewUserRequest;
import org.project.final_backend.domain.NewUserResponse;

public interface UserService {
    NewUserResponse registerUser(NewUserRequest request);
}
