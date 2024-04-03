package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class JobPostDto {
    private UUID id;
    private UUID userId;
    private String userName;
    private String profileImg;
    private long followers;
    private String title;
    private String description;
    private String location;
    private String type; // eg: full time (or) part-time
    private String category;
    private String qualification;
    private String exp;
    private String salary;
    private String companyName;
    private LocalDateTime uploadTime;
    private LocalDateTime updateTime;
    private boolean isDeleted;
}
