package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.project.final_backend.entity.Post;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PostDto {
//    private UUID postId;
    private UUID id;
    private UUID userId;
    private String accountName;
    private String uploadPhoto;
    private String profileImg;
    private String caption;
    private long followers;
    private long react;
    private long comment;
    private long repost;
    private LocalDateTime uploadTime;
    private LocalDateTime updateTime;
    private boolean isDeleted;
//    public PostDto(Post post) {
//        this.postId = post.getId();
//        this.userId = post.getUsers().getId();
//    }
}
