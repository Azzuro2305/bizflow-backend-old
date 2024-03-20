package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String companyName;
    private String position;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
