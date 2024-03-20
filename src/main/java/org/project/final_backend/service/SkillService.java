package org.project.final_backend.service;

import org.project.final_backend.domain.request.skill.NewSkillRequest;
import org.project.final_backend.domain.request.skill.UpdateSkillRequest;
import org.project.final_backend.domain.response.skill.NewSkillResponse;
import org.project.final_backend.domain.response.skill.UpdateSkillResponse;
import org.project.final_backend.dto.model.SkillInfo;

import java.util.UUID;

public interface SkillService {
    NewSkillResponse addSkill(UUID id, NewSkillRequest request);
    SkillInfo getSkill(UUID id);
    UpdateSkillResponse updateSkill(UUID id, UpdateSkillRequest request);
    void deleteSkill(UUID id);
}
