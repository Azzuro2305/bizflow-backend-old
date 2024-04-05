package org.project.final_backend.controller;

import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.ChatBoxInfo;
import org.project.final_backend.dto.request.IdRequest;
import org.project.final_backend.dto.request.NewChatBoxRequest;
import org.project.final_backend.dto.response.NewChatBoxResponse;
import org.project.final_backend.entity.ChatBox;
import org.project.final_backend.service.ChatBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/chat-box")
public class ChatBoxController {
    @Autowired
    private ChatBoxService chatBoxService;
    @Autowired
    private ModelMapper modelMapper;



    @GetMapping
    public ResponseEntity<HttpResponse<List<ChatBoxInfo>>> getChatBoxes(@RequestBody IdRequest request) {
        List<ChatBoxInfo> chatBoxInfos = chatBoxService.getChatBoxes(request);
        HttpResponse<List<ChatBoxInfo>> response = new HttpResponse<>(chatBoxInfos, "chat boxes retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpResponse<NewChatBoxResponse>> createChatBox(@RequestBody NewChatBoxRequest request) {
        HttpResponse<NewChatBoxResponse> response = new HttpResponse<>(chatBoxService.createChatBox(request), "chat box created", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
