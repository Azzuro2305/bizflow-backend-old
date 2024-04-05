package org.project.final_backend.domain.request.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewMessageRequest {
    private String senderId;
    private String recipientId;
    private String content;
}
