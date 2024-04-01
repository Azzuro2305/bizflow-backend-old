package org.project.final_backend.repo;

import org.project.final_backend.entity.Follower;
import org.project.final_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowerRepo extends JpaRepository<Follower, UUID> {
//    long countByUser_Id(UUID userId);
    List<Follower> findByFollowing_Id(UUID userId);
    List<Follower> findByFollower_Id(UUID userId);
    long countByFollower(Users user);
    long countByFollowing(Users user);
    Follower findByFollowingAndFollower(Users following, Users follower);
}
