package org.project.final_backend.domain.follower;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewFollowerRequest {
    private UUID userId;
    private UUID followerId;
}
