package org.project.final_backend.repo;

import org.project.final_backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    Optional<Post> findPostById(Integer id);
}
