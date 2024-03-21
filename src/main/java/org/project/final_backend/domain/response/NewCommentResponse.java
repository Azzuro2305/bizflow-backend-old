package org.project.final_backend.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewCommentResponse {

    private UUID id;
    private String userName;
    private String userprofile;
    private String text;
}
