package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.NewOTPRequest;
import org.project.final_backend.domain.request.ResetPasswordOTPRequest;
import org.project.final_backend.domain.utility.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.project.final_backend.service.AuthService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/newOTP")
    public ResponseEntity<HttpResponse<Boolean>> newOTP(@RequestBody NewOTPRequest request) {
        authService.generateOTP(request);
        HttpResponse<Boolean> response =
                new HttpResponse<>(true, "OTP is sent", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }
}
