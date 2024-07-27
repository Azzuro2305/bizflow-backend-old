package org.project.final_backend.domain.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateUserRequest {
    private String email;
    private String password;
}
