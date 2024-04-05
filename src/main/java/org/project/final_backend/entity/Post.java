package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String uploadPhoto;
    private String accountName;
    private String profileImg;
    @Lob
    private String caption;
    private long followers;
    private long react;
    private long comment;
    private long repost;
    private LocalDateTime uploadTime;
    private LocalDateTime updateTime;
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
