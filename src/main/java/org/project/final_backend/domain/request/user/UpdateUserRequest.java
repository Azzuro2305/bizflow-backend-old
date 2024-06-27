package org.project.final_backend.domain.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserRequest {
    private String userName;
    private String mail;
    private String phoneNumber;
    private String address;
    private String bannerImg;
    private String profileImg;
    private LocalDate dob;
    private String gender;
    private String bio;
}
