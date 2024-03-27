package org.project.final_backend.repo;

import org.project.final_backend.dto.model.WorkExpInfo;
import org.project.final_backend.entity.WorkExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkExpRepo extends JpaRepository<WorkExp, UUID> {
    Optional<WorkExp> findWorkExpById(UUID id);
    List<WorkExp> findAllByUserId(UUID userId);
}
