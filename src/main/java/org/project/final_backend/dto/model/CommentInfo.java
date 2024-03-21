package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentInfo {
    private UUID id;
    private String userName;
    private String userProfile;
    private String text;
    private boolean isDeleted;
}
