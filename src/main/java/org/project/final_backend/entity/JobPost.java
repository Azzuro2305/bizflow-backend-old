package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private String location;
    private String type; // eg: full time (or) part-time
    private String category;
    private String qualification;
    private String exp;
    private String salary;
    private String companyName;
    private LocalDateTime uploadTime;
    private LocalDateTime updateTime;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
