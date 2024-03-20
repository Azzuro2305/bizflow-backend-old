package org.project.final_backend.dto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.final_backend.entity.Education;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Skill;
import org.project.final_backend.entity.WorkExp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserInfo {
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String phoneNumber;
    private String address;
    private String bannerImg;
    private String profileImg;
    private String bio;
    private long followers;
    private long following;
    private String role;
    private LocalDate dob;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String gender;
}
