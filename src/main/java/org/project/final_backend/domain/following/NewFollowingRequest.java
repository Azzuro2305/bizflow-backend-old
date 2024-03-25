package org.project.final_backend.domain.following;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewFollowingRequest {
    private UUID userId;
    private UUID followingId;
}
