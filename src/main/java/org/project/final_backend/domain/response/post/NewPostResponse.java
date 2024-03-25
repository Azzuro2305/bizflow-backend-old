package org.project.final_backend.domain.response.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NewPostResponse {
    private UUID id;
    private String accountName;
    private long followers;
    private LocalDateTime uploadTime;
    private String profileImg;
    private String caption;
    private String uploadPhoto;
    private long react;
    private long comment;
    private boolean isDeleted;
}
