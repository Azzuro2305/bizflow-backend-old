package org.project.final_backend.domain.request.skill;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewSkillRequest {
    private String skillName;
}
