package org.project.final_backend.domain.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String firstName;
    private String lastName;
//    private String userName;
    public String getUserName() {
        return firstName + " " + lastName;
    }
    private String mail;
    private String password;
}
