package org.project.final_backend.service;

import org.project.final_backend.dto.model.ChatBoxInfo;
import org.project.final_backend.dto.request.IdRequest;
import org.project.final_backend.dto.request.NewChatBoxRequest;
import org.project.final_backend.dto.response.NewChatBoxResponse;
import org.project.final_backend.entity.ChatBox;

import java.util.List;
import java.util.UUID;

public interface ChatBoxService {
    NewChatBoxResponse createChatBox(NewChatBoxRequest request);
    List<ChatBoxInfo> getChatBoxes(IdRequest request);
    void createNewChatBox(NewChatBoxRequest request);
}
