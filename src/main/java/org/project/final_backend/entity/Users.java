package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
//    public UUID getId() {
//        return id;
//    }
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String phoneNumber;
    private String password;
    private String address;
    private String bannerImg;
    private String profileImg;
    private String bio;

    private long followers;
    private long following;
    private long friends;
    private long posts;

    private String role;
    private LocalDate dob;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String gender;
    private boolean isDeleted;


}
