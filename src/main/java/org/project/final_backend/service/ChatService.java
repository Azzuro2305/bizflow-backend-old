package org.project.final_backend.service;

public interface ChatService {
    void createMessage(String chatId, String senderId, String recipientId, String message);
}