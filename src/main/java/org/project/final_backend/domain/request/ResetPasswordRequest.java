package org.project.final_backend.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
