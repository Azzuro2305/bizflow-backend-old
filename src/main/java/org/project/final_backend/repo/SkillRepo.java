package org.project.final_backend.repo;

import org.project.final_backend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillRepo extends JpaRepository<Skill, UUID> {
    Optional<Skill> findSkillById(UUID id);
}
