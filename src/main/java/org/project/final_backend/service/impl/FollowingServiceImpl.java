package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.following.NewFollowingRequest;
import org.project.final_backend.entity.Following;
import org.project.final_backend.entity.Users;
import org.project.final_backend.repo.FollowingRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.FollowingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FollowingServiceImpl implements FollowingService {
    private final FollowingRepo followingRepo;
    private final UserRepo userRepo;

    @Override
    public long countByUser_Id(UUID userId) {
        return followingRepo.countByUser_Id(userId);
    }

    @Override
    public void updateFollowingCount(UUID userId) {
        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        long followingCount = countByUser_Id(userId);
        user.setFollowing(followingCount);
        userRepo.save(user);
    }

    @Override
    public void addFollowing(NewFollowingRequest request) {
        UUID userId = request.getUserId();
        UUID followingId = request.getFollowingId();

        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Users following = userRepo.findById(followingId).orElseThrow(() -> new RuntimeException("Following not found"));

        if (userId.equals(followingId)) {
            throw new RuntimeException("A user cannot follow themselves");
        }

        Following existingFollowing = followingRepo.findByUserAndFollowing(user, following);
        if (existingFollowing != null && !existingFollowing.isDeleted()) {
            throw new RuntimeException("The user is already following");
        } else {
            Following newFollowing = Following.builder()
                    .user(user)
                    .following(following)
                    .followedAt(LocalDateTime.now())
                    .isDeleted(false)
                    .build();

            followingRepo.save(newFollowing);
        }
    }
}
