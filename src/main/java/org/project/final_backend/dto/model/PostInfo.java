package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class PostInfo {
    private UUID userId;
    private UUID id;
    private String accountName;
    private long followers;
    private LocalDateTime uploadTime;
    private String profileImg;
    private String caption;
    private String uploadPhoto;
    private long like;
    private Long comment;
}
