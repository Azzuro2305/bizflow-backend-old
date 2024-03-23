package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.project.final_backend.repo.FollowingRepo;
import org.project.final_backend.service.FollowingService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FollowingServiceImpl implements FollowingService {
    private final FollowingRepo followingRepo;

    @Override
    public long countByUser_Id(UUID userId) {
        return followingRepo.countByUser_Id(userId);
    }
}
