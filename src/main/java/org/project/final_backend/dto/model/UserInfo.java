package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfo {
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String phoneNumber;
//    private String password;
    private String address;
//    private Integer role;
    private LocalDate dob;
//    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String gender;
    private boolean isDeleted;
}
