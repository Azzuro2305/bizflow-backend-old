package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.domain.response.VerifyMailResponse;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.entity.Users;
import org.project.final_backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PostMapping("/verify-mail")
    public ResponseEntity<HttpResponse<VerifyMailResponse>> verifyMail(@RequestBody VerifyMailRequest request){
        HttpResponse<VerifyMailResponse> response =
                new HttpResponse<>(userService.verifyMail(request), "Mail verified", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request){
        userService.resetPassword(request);
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

    @GetMapping("/get-all-users")
    public ResponseEntity<Page<Users>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "userName,asc") String[] sort) {
        List<Sort.Order> orders = new LinkedHashSet<>(Arrays.stream(sort)
                .map(s -> s.split(","))
                .map(arr -> arr.length > 1 ? (arr[1].equals("desc") ? Sort.Order.desc(arr[0]) : Sort.Order.asc(arr[0])) : Sort.Order.asc("userName"))
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());
        Sort sorting = Sort.by(orders);
        Page<Users> usersPage = userService.getAllUsers(PageRequest.of(page, size, sorting));
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }
}
