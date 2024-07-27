package org.project.final_backend.domain.request.password;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VerifyMailRequest {
    private String email;
}
