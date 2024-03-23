package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.project.final_backend.entity.Users;
import org.project.final_backend.repo.FollowerRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.FollowService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowerRepo followerRepo;
    private final UserRepo userRepo;

    @Override
    public long countByUser_Id(UUID userId) {
        return followerRepo.countByUser_Id(userId);
    }

    public void updateFollowerCount(UUID userId) {
        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        long followerCount = followerRepo.countByUser_Id(userId);
        user.setFollowers(followerCount);
        userRepo.save(user);
    }
}
