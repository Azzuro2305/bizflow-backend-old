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
