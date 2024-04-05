package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.project.final_backend.dto.model.ChatBoxInfo;
import org.project.final_backend.dto.request.IdRequest;
import org.project.final_backend.dto.request.NewChatBoxRequest;
import org.project.final_backend.dto.response.NewChatBoxResponse;
import org.project.final_backend.entity.ChatBox;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.ChatBoxRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.ChatBoxService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatBoxServiceImp implements ChatBoxService {
    private final UserRepo userRepo;
    private final ChatBoxRepo chatBoxRepo;
    private final ModelMapper modelMapper;

    @Override
    public NewChatBoxResponse createChatBox(NewChatBoxRequest request) {
        if (chatBoxRepo.findBySenderIdAndRecipientId(request.getSenderId(), request.getRecipientId()) != null || chatBoxRepo.findBySenderIdAndRecipientId(request.getRecipientId(), request.getSenderId()) != null) {
            throw new IllegalArgumentException("Chat box already exists!");
        } else {
            Users sender = userRepo
                    .findUsersById(request.getSenderId())
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
            Users recipient = userRepo
                    .findUsersById(request.getRecipientId())
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
            ChatBox chatBox = ChatBox.builder()
                    .sender(sender)
                    .recipient(recipient)
                    .build();

            ChatBoxInfo senderInfo = modelMapper.map(sender, ChatBoxInfo.class);
            ChatBoxInfo recipientInfo = modelMapper.map(recipient, ChatBoxInfo.class);

            NewChatBoxResponse response = modelMapper.map(chatBoxRepo.save(chatBox), NewChatBoxResponse.class);
            response.setSender(senderInfo);
            response.setRecipient(recipientInfo);
            return modelMapper.map(chatBoxRepo.save(chatBox), NewChatBoxResponse.class);
        }
    }

    @Bean
    public ModelMapper chatBoxCustomModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ChatBox, ChatBoxInfo>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setProfileImg(source.getSender().getProfileImg());
                map().setUserName(source.getSender().getUserName());
//                map().setLastMessage(source.getSender().getLastMessage());  // need to take this value from chat
            }
        });
        return modelMapper;
    }

    @Override
    public List<ChatBoxInfo> getChatBoxes(IdRequest request) {
        List<ChatBox> chatBoxes = chatBoxRepo.findBySenderId(request.getId());
        List<ChatBoxInfo> chatBoxInfos = chatBoxes.stream()
                .map(chatBox -> chatBoxCustomModelMapper().map(chatBox, ChatBoxInfo.class))
                .collect(Collectors.toList());
        return chatBoxInfos;
    }

    @Override
    public void createNewChatBox(NewChatBoxRequest request) {
        if (chatBoxRepo.findBySenderIdAndRecipientId(request.getSenderId(), request.getRecipientId()) != null || chatBoxRepo.findBySenderIdAndRecipientId(request.getRecipientId(), request.getSenderId()) != null) {
            throw new IllegalArgumentException("Chat box already exists!");
        } else {
            Users sender = userRepo
                    .findUsersById(request.getSenderId())
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
            Users recipient = userRepo
                    .findUsersById(request.getRecipientId())
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
            ChatBox chatBox = ChatBox.builder()
                    .sender(sender)
                    .recipient(recipient)
                    .build();
            chatBoxRepo.save(chatBox);
        }
    }
}
