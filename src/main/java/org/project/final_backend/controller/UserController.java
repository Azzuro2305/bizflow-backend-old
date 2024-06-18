package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.utility.CustomPaginationResponse;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.entity.Users;
import org.project.final_backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CustomPaginationResponse<Users>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "userName,desc") String[] sort) {

        Page<Users> usersPage = userService.getAllUsers(PageRequest.of(page-1, size), sort);

        CustomPaginationResponse.Meta meta = new CustomPaginationResponse.Meta(usersPage, page); // Pass the original page number
        CustomPaginationResponse<Users> response = new CustomPaginationResponse<>(
                usersPage.getContent(),
                true,
                meta,
                HttpStatus.OK,
                "Get All users Successfully Retrieved"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<UserInfo>> retrieveUserInfo(@PathVariable UUID id){
        UserInfo userInfo = userService.retrieveUserInfo(id);

        HttpResponse<UserInfo> response =
                new HttpResponse<>(userInfo,userInfo != null, "User retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PutMapping("/{id}/reset-password")
//    public ResponseEntity<Void> resetPasswordWithUserId(@PathVariable UUID id, @RequestBody ResetPasswordUserIdRequest request){
//        userService.resetPasswordWithUserId(id, request);
//        return ResponseEntity.noContent().build();
//    }

//    @GetMapping("/users")
//    public ResponseEntity<Page<Users>> getAllUsers(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "6") int size,
//            @RequestParam(defaultValue = "userName,asc") String[] sort) {
//        List<Sort.Order> orders = new LinkedHashSet<>(Arrays.stream(sort)
//                .map(s -> s.split(","))
//                .map(arr -> arr.length > 1 ? (arr[1].equals("desc") ? Sort.Order.desc(arr[0]) : Sort.Order.asc(arr[0])) : Sort.Order.asc("userName"))
//                .collect(Collectors.toList()))
//                .stream()
//                .collect(Collectors.toList());
//        Sort sorting = Sort.by(orders);
//
//        Page<Users> usersPage = userService.getAllUsers(PageRequest.of(page, size, sorting));
//
//        return new ResponseEntity<>(usersPage, HttpStatus.OK);
//    }

//    @PutMapping("/auth/reset-password-otp")
//    public ResponseEntity<Void> resetPasswordWithOTP(@RequestBody ResetPasswordOTPRequest request){
//        userService.resetPasswordWithOTP(request);
//        return ResponseEntity.noContent().build();
//    }

//
//    @PutMapping("/subscribe")
//    public ResponseEntity<HttpResponse<Void>> purchase(@RequestBody Subscribe subscribe){
//        userService.purchase(subscribe);
//        HttpResponse<Void> response = new HttpResponse<>(null, "Subscription successful", HttpStatus.OK);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/get-by-username")
//    public ResponseEntity<HttpResponse<UserInfo>> retrieveUserInfoByUserName(@RequestParam String userName){
//        HttpResponse<UserInfo> response =
//                new HttpResponse<>(userService.retrieveUserInfoByUserName(userName), "User retrieved", HttpStatus.OK);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//





    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse<String>> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        HttpResponse<String> response = new HttpResponse<>("User deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewUserResponse> updateUserInfo(@PathVariable UUID id, @RequestBody UpdateUserRequest request){
        NewUserResponse userResponse = userService.updateUser(id, request);
        return ResponseEntity.ok(userResponse);
    }

//    @PostMapping("")
//    public ResponseEntity<HttpResponse<NewUserResponse>> registerUser(@RequestBody NewUserRequest request){
//        HttpResponse<NewUserResponse> response =
//                new HttpResponse<>(userService.registerUser(request), "Successfully registered", HttpStatus.CREATED);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }


}
