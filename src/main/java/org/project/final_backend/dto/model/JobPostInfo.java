package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class JobPostInfo {
    private UUID userId;
    private UUID id;
    private String userName;
    private long followers;

    private String profileImg;
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
    private boolean isDeleted;

}
