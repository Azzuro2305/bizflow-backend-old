package org.project.final_backend.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    public String getUserName() {
        return firstName + " " + lastName;
    }
    private String mail;  // read-only
    private String phoneNumber;
//    private String password;
    private String address;
//    private Integer role;
    private LocalDate dob;
    private LocalDateTime updatedDate; // read-only
    private String gender;
//    private boolean isDeleted;
}
