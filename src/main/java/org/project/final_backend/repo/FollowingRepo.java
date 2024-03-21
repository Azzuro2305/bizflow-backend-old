package org.project.final_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowingRepo extends JpaRepository<FollowingRepo, UUID> {
}
