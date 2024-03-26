package org.project.final_backend.domain.response.react;

import lombok.Getter;
import lombok.Setter;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;

import java.util.UUID;

@Getter
@Setter
public class NewReactResponse {

    //    private UUID id;
    private UUID postId;
    private UUID userId;

}