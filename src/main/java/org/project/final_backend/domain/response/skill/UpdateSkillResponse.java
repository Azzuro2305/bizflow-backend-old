package org.project.final_backend.domain.response.skill;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateSkillResponse {
    private String skillName;
    private LocalDateTime updatedDate;
}
