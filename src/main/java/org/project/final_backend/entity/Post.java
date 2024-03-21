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
    private String accountName;
    private Long followers;
    private LocalDateTime uploadTime;
    private LocalDateTime updateTime;
    private String profileImage;
    private String caption;
    private String uploadPhoto;
    private double react;
//    private String comment;
    private boolean isDeleted;
//    @OneToMany
//    @JoinColumn(name = "user_id")
//    private Comments comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
