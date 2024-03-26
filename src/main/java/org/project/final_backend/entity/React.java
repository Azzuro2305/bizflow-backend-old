package org.project.final_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
    name = "react",
    uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "user_id"})
)
public class React {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users userId;
    //    private String type;
//    private boolean isDeleted;
//    private boolean isLiked;
}
