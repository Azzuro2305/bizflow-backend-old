package org.project.final_backend.repo;

import org.project.final_backend.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowerRepo extends JpaRepository<Follower, UUID> {

}
