package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String bannerImg;
    private String profileImg;
    private String bio;

    private long followers;
    private long followings;
    private long friends;
    private long posts;
    private long jobPosts;

    private String role;
    private String subscription;
    private LocalDate dob;
    private Date createdDate;
    private Date updatedDate;
    private String gender;
    private boolean isBanned;
    private boolean isDeleted;
}
