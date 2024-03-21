package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "user_id")
    private Users user;
//    private UUID userId;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Users follower;
//    private UUID followerId;
    private boolean isDeleted;
}
