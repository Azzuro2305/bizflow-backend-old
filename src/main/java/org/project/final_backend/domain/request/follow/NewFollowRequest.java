package org.project.final_backend.domain.request.follow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFollowRequest {
    private String followerId;
    private String followingId;
}
