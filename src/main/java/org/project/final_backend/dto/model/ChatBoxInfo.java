package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatBoxInfo {
    private UUID id;
    private String profileImg;
    private String userName;
    private String lastMessage;
}
