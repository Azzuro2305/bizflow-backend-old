package org.project.final_backend.domain.request.password;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResetPasswordUserIdRequest {
    private UUID userId;
    private String oldPassword;
    private String newPassword;
}
