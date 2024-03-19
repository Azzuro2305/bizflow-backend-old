package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class PostInfo {

    private Integer id;
    private String accountName;
    private Long followers;
    private LocalDateTime uploadTime;
    private String profile;
    private String caption;
    private String uploadPhoto;
    private double like;
    private String comment;
}
