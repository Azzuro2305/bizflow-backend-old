package org.project.final_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.final_backend.dto.model.ChatBoxInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewChatBoxResponse {
    private ChatBoxInfo sender;
    private ChatBoxInfo recipient;
}
