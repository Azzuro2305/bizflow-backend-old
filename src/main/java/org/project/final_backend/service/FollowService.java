package org.project.final_backend.service;

import java.util.UUID;

public interface FollowService {
    long countByUser_Id(UUID userId);

}
