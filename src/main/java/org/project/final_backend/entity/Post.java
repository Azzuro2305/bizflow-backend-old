package org.project.final_backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accountName;
    private Long followers;
    private LocalDateTime uploadTime;
    private LocalDateTime updateTime;
    private String profileImage;
    private String caption;
    private String uploadPhoto;
    private double react;
    private String comment;
    private boolean isDeleted;

}
