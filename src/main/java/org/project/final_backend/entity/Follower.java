package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Users follower;
    @ManyToOne
    @JoinColumn(name = "following_id")
    private Users following;

    private LocalDateTime followedAt;
}

// This entity is to describe the relationship between users and their followers.
// No.    User    User's followers
// 1    @user1    @user2
// 1    @user1    @user3