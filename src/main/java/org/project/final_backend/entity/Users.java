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

//    public Users(String firstName, String lastName, String userName, String mail, String phoneNumber, String password, String address, String bannerImg, String profileImg, String bio, long followers, long followings, long friends, long posts, long jobPosts, String role, String subscription, LocalDate dob, Date createdDate, Date updated) {
//        UUID uuid = UUID.randomUUID();
//        this.id = uuid;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.userName = userName;
//        this.mail = mail;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.address = address;
//        this.bannerImg = bannerImg;
//        this.profileImg = profileImg;
//        this.bio = bio;
//        this.followers = followers;
//        this.followings = followings;
//        this.friends = friends;
//        this.posts = posts;
//        this.jobPosts = jobPosts;
//        this.role = role;
//        this.subscription = subscription;
//        this.dob = dob;
//        this.createdDate = new Date();
//        this.updatedDate = updated;
//    }

}
