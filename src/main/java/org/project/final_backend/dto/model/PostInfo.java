package org.project.final_backend.dto.model;

import lombok.*;
import org.project.final_backend.entity.Users;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
