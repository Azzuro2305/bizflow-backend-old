package org.project.final_backend.domain.request.post;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePostRequest {
    private UUID postId;
    private String caption;
    private String uploadPhoto;
}
