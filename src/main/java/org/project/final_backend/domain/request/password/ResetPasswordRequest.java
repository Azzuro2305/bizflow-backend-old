package org.project.final_backend.domain.request.password;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
