package org.project.final_backend.service;

import org.project.final_backend.domain.request.chat.NewMessageRequest;

public interface ChatService {
    void sendMessage(NewMessageRequest request);


    boolean isChatBoxExist(String senderId, String recipientId);

}