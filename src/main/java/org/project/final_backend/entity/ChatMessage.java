package org.project.final_backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;

    public ChatMessage(String chatId, String senderId, String recipientId, String content) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
    }
}