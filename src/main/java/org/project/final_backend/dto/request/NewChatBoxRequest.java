package org.project.final_backend.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewChatBoxRequest {
    private UUID senderId;
    private UUID recipientId;
}
