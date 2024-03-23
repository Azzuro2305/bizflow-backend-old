package org.project.final_backend.service;

import java.util.UUID;

public interface FollowingService {
    long countByUser_Id(UUID userId);
}
