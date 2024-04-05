package org.project.final_backend.service;

import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.domain.response.follower.NewFollowerResponse;

import java.util.List;
import java.util.UUID;

public interface FollowerService {
    NewFollowerResponse addFollower(NewFollowerRequest request);
    boolean hasFollowedBack(UUID followingId, UUID followerId);
    List<UUID> findFollowersByUserId(UUID userId);
    List<UUID> findFollowingsByUserId(UUID userId);
    long findFollowerCountByUserId(UUID userId);
    long findFollowingCountByUserId(UUID userId);
    void deleteFollower(UUID followingId, UUID followerId);
}
