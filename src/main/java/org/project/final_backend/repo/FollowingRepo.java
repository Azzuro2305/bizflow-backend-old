package org.project.final_backend.repo;

import org.project.final_backend.entity.Following;
import org.project.final_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowingRepo extends JpaRepository<Following, UUID> {
    long countByUser_Id(UUID userId);
    Following findByUserAndFollowing(Users user, Users following);
}
