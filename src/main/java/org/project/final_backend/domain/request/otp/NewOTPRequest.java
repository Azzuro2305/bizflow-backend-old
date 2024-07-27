package org.project.final_backend.domain.request.otp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOTPRequest {
    private String email;
    private String otp;
}
