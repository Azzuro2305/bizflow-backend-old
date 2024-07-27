package org.project.final_backend.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserInfo {
    private UUID id;
    private String userName;
    private String email;
    private String role;
    private String subscription;
}
