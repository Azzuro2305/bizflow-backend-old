package org.project.final_backend.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NewUserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String phoneNumber;
    private String password;
    private String address;
    private Integer role;
    private LocalDate dob;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean gender;
    private boolean isDeleted;
}
