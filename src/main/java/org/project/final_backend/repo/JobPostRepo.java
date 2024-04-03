package org.project.final_backend.repo;

import org.project.final_backend.entity.JobPost;
import org.project.final_backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobPostRepo extends JpaRepository<JobPost, UUID>, JpaSpecificationExecutor<JobPost> {
    Optional<JobPost> findJobPostById(UUID id);
    List<JobPost> findJobPostsByUsers_Id(UUID userId);
    Page<JobPost> findAll(Pageable pageable);
    long countByUsers_Id(UUID userId);
}
