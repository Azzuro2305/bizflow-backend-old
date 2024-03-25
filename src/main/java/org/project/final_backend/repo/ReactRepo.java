package org.project.final_backend.repo;

import org.project.final_backend.entity.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactRepo extends JpaRepository<React, UUID> {
    Optional<React> findReactById(UUID uuid);



}
