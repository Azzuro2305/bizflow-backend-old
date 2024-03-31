package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.chat.NewMessageRequest;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.entity.ChatMessage;
import org.project.final_backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload NewMessageRequest request) {
//        chatService.sendMessage(request);
//        return new ChatMessage(request.getChatId(), request.getSenderId(), request.getRecipientId(), request.getContent());
//    }

    @PostMapping("/send-message")
    public ResponseEntity<HttpResponse> sendMessage(@RequestBody NewMessageRequest request) {
        chatService.sendMessage(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}