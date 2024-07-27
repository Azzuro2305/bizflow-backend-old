package org.project.final_backend.domain.response.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NewUserResponse {
    private UUID id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String role;
    private LocalDate dob;
    private LocalDateTime createdDate;
    private String gender;
    private String bio;
    private boolean isDeleted;
}
