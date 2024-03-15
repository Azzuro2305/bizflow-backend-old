package org.project.final_backend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String password;
}
