package org.project.final_backend.domain.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String userName;
//    private String lastName;
//    public String getUserName() {
//        return firstName + " " + lastName;
//    }
    private String email;
    private String password;
}
