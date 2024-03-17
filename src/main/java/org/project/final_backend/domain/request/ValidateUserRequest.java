package org.project.final_backend.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateUserRequest {
    private String mail;
    private String password;
}
