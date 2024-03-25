package org.project.final_backend.service;

import org.project.final_backend.domain.follower.NewFollowerRequest;

import java.util.UUID;

public interface FollowerService {
    long countByUser_Id(UUID userId);
    void updateFollowerCount(UUID userId);
    void addFollower(NewFollowerRequest request);
}
