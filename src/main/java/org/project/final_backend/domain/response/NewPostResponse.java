package org.project.final_backend.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NewPostResponse {
    private Integer id;
    private String accountName;
    private Long followers;
    private LocalDateTime uploadTime;
    private String profileImage;
    private String caption;
    private String uploadPhoto;
    private Long react;
    private String comment;
    private boolean isDeleted;
}
