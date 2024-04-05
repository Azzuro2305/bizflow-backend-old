package org.project.final_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timeStamp;

    public ChatMessage(String senderId, String recipientId, String content) {
        UUID uuid = UUID.randomUUID();
        this.id = Math.abs(uuid.getMostSignificantBits());
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.timeStamp = new Date();
    }
}