package org.project.final_backend.repo;

import org.project.final_backend.dto.model.PostDto;
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
public interface PostRepo extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {
    Optional<Post> findPostById(UUID id);
    List<Post> findPostsByUsers_Id(UUID userId);
    Page<Post> findAll(Pageable pageable);
    long countByUsers_Id(UUID userId);

    Page<Post> findByAccountNameContainingIgnoreCaseOrCaptionContainingIgnoreCase(
            String key1,
            String key2,
            Pageable pageable
    );



}
