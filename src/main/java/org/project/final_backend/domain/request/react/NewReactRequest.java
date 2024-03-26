package org.project.final_backend.domain.request.react;

import com.zaxxer.hikari.metrics.PoolStats;
import lombok.Getter;
import lombok.Setter;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;

import java.util.UUID;

@Getter
@Setter
public class NewReactRequest {

    private UUID postId;
    private UUID userId;

}
