package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.domain.response.user.NewUserResponse;
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
    public ResponseEntity<HttpResponse<UserInfo>> retrieveUserInfo(@RequestParam UUID id){
        HttpResponse<UserInfo> response =
                new HttpResponse<>(userService.retrieveUserInfo(id), "User retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@PathVariable UUID id, @RequestBody ResetPasswordRequest request){
        userService.resetPassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reset-password-otp")
    public ResponseEntity<Void> resetPasswordWithOTP(@PathVariable UUID id, @RequestBody ResetPasswordOTPRequest request){
        userService.resetPasswordWithOTP(id, request);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<HttpResponse<UserInfo>> validateUser(@RequestBody ValidateUserRequest request){
            HttpResponse<UserInfo> response =
                    new HttpResponse<>(userService.validateUser(request), "User validated", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
