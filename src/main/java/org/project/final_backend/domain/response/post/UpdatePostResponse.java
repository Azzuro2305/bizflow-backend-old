package org.project.final_backend.domain.response.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdatePostResponse {
    private String caption;
    private String uploadPhoto;
    private LocalDateTime updateTime;
}
