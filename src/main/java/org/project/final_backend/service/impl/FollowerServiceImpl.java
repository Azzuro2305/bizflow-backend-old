package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.entity.Follower;
import org.project.final_backend.entity.Users;
import org.project.final_backend.repo.FollowerRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.FollowerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepo followerRepo;
    private final UserRepo userRepo;

    @Override
    public long countByUser_Id(UUID userId) {
        return followerRepo.countByUser_Id(userId);
    }

@Override
public void addFollower(NewFollowerRequest request) {
    UUID userId = request.getUserId();
    UUID followerId = request.getFollowerId();

    Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Users follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));

    if (userId.equals(followerId)) {
        throw new RuntimeException("A user cannot follow themselves");
    }

    Follower existingFollower = followerRepo.findByUserAndFollower(user, follower);
    if (existingFollower != null && !existingFollower.isDeleted()) {
        throw new RuntimeException("The user is already following");

    } else {
        Follower newFollower = Follower.builder()
                .user(user)
                .follower(follower)
                .followedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        followerRepo.save(newFollower);
    }
}

    @Override
    public void updateFollowerCount(UUID userId) {
        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        long followerCount = followerRepo.countByUser_Id(userId);
        user.setFollowers(followerCount);
        userRepo.save(user);
    }
}
