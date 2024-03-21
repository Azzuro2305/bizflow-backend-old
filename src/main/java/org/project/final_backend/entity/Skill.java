package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String skillName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
