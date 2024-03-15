package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.*;
import org.project.final_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("")
    public ResponseEntity<HttpResponse<NewUserResponse>> registerUser(@RequestBody NewUserRequest request){
        HttpResponse<NewUserResponse> response =
                new HttpResponse<>(userService.registerUser(request), "Successfully registered", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/validate")
    public ResponseEntity<UserInfo> validateUser(@RequestBody ValidateUserRequest request){
        return ResponseEntity.ok(userService.validateUser(request));
    }
}
