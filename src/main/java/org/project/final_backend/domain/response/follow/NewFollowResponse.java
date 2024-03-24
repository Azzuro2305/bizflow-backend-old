package org.project.final_backend.domain.response.follow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFollowResponse {
    private String followerId;
    private String followingId;
    private boolean isFollowed;
}
