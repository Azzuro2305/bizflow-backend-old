package org.project.final_backend.domain.response.follower;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NewFollowerResponse {
    private UUID followerId;
    private UUID followingId;
    private LocalDateTime followedAt;
}
