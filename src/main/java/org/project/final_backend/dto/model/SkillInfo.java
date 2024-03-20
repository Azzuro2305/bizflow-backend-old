package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SkillInfo {
    private UUID id;
    private String skillName;
    private UUID userId;
}
