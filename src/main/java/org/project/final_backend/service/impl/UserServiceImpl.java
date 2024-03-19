package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.*;
import org.project.final_backend.domain.response.NewUserResponse;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.InvalidPasswordException;
import org.project.final_backend.exception.UserFoundException;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    AuthServiceImpl authService;

    @Override
    public Users findUsersById(UUID id) {
        final Users user = userRepo
                .findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return user;
    }

    @Override
    public UserInfo validateUser(ValidateUserRequest request) {
        final Users user = userRepo
                .findUsersByMail(request.getMail())
                .orElseThrow(() -> new UserNotFoundException("User have not registered yet!"));
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        }
        return modelMapper.map(user, UserInfo.class);
    }

    @Override
    public UserInfo retrieveUserInfo(UUID id) {
        final Users user = userRepo
                .findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return modelMapper.map(user, UserInfo.class);
    }

    @Override
    public void resetPassword(UUID id, ResetPasswordRequest request) {
        final Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
            user.setUpdatedDate(LocalDateTime.now());
            userRepo.save(user);
        }
    }

    @Override
    public void resetPasswordWithOTP(UUID id, ResetPasswordOTPRequest request) {
        final Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (!authService.validateOTP(request)) {
            throw new InvalidPasswordException("Invalid OTP!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
            user.setUpdatedDate(LocalDateTime.now());
            userRepo.save(user);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        final Users user = userRepo
                .findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userRepo.delete(user);
    }

    @Override
    public NewUserResponse registerUser(NewUserRequest request) {
        if (userRepo.findUsersByMail(request.getMail()).isPresent()) {
            throw new UserFoundException("User already exists!");
        } else {
            Users user = Users.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .userName(request.getUserName())
                    .mail(request.getMail())
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .createdDate(LocalDateTime.now())
//                    .role(1)
                    .build();
            return modelMapper.map(userRepo.save(user), NewUserResponse.class);
        }
    }

    @Override
    public NewUserResponse updateUser(UUID id, UpdateUserRequest request) {
        Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUserName());
//        user.setMail(request.getMail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());
        user.setUpdatedDate(LocalDateTime.now());
        return modelMapper.map(userRepo.save(user), NewUserResponse.class);
    }
}
