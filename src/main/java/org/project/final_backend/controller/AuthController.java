package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.otp.NewOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.domain.response.VerifyMailResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/newOTP")
    public ResponseEntity<HttpResponse<Boolean>> newOTP(@RequestBody NewOTPRequest request) {
        authService.generateOTP(request);

        HttpResponse<Boolean> response =
                new HttpResponse<>(true, "OTP is sent", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse<Boolean>> registerUser(@RequestBody NewUserRequest request) {

        HttpResponse<Boolean> response = new HttpResponse<>(authService.registerUser(request), true, "Successfully registered", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse<UserInfo>> validateUser(@RequestBody ValidateUserRequest request){
        UserInfo userInfo = authService.validateUser(request);

        HttpResponse<UserInfo> response = new HttpResponse<>(userInfo,userInfo != null,"User Validate", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify-mail")
    public ResponseEntity<HttpResponse<Boolean>> verifyMail(@RequestBody VerifyMailRequest request){

        HttpResponse<Boolean> response =
                new HttpResponse<>(authService.verifyMail(request),true, "Mail verified", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password-otp")
    public ResponseEntity<HttpResponse<Boolean>> verifyOTPCode(@RequestBody ResetPasswordOTPRequest request){
        HttpResponse<Boolean> response =
                new HttpResponse<>(authService.verifyOTPCode(request), "OTP verified", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<HttpResponse<Boolean>> resetPassword(@RequestBody ResetPasswordRequest request){

        HttpResponse<Boolean> response = new HttpResponse<>(authService.resetPassword(request), true,"Password Update Successfully", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password-otp")
    public ResponseEntity<HttpResponse<Boolean>> resetPasswordWithOTP(@RequestBody ResetPasswordOTPRequest request){

        HttpResponse<Boolean> response = new HttpResponse<>(authService.resetPasswordWithOTP(request), true,"Password Reset Successfully", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
