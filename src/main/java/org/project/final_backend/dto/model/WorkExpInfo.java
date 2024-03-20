package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class WorkExpInfo {
    private UUID id;
    private String companyName;
    private String position;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private UUID userId;
}
