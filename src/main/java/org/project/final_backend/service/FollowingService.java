package org.project.final_backend.service;

import org.project.final_backend.domain.following.NewFollowingRequest;

import java.util.UUID;

public interface FollowingService {
    long countByUser_Id(UUID userId);
    void updateFollowingCount(UUID userId);
    void addFollowing(NewFollowingRequest request);
}
