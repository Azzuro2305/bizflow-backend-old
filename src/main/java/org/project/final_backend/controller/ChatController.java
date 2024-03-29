package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
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
    @PostMapping("/send-message")
    public ResponseEntity<HttpResponse<ChatMessage>> sendMessage(@RequestBody ChatMessage chatMessage) {
        chatService.createMessage(chatMessage.getChatId(), chatMessage.getSenderId(), chatMessage.getRecipientId(), chatMessage.getContent());

        HttpResponse<ChatMessage> response = new HttpResponse<>(chatMessage, "Message sent successfully", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}