package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.domain.request.NewUserRequest;
import org.project.final_backend.domain.request.UpdateUserRequest;
import org.project.final_backend.domain.request.ValidateUserRequest;
import org.project.final_backend.domain.response.NewUserResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("")
    public ResponseEntity<UserInfo> retrieveUserInfo(@RequestParam UUID id){
        return ResponseEntity.ok(userService.retrieveUserInfo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok("User successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewUserResponse> updateUserInfo(@PathVariable UUID id, @RequestBody UpdateUserRequest request){
        NewUserResponse userResponse = userService.updateUser(id, request);
        return ResponseEntity.ok(userResponse);
    }

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
