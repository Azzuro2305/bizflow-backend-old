package org.project.final_backend.repo;

import org.project.final_backend.dto.model.PostDto;
import org.project.final_backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {
    Optional<Post> findPostById(UUID id);
    Optional<Post> findPostByUsersId(UUID id);
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAccountNameContainingIgnoreCaseOrCaptionContainingIgnoreCase(
            String key1,
            String key2,
            Pageable pageable
    );



}
