package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.domain.response.VerifyMailResponse;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.InvalidPasswordException;
import org.project.final_backend.exception.UserFoundException;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.FollowerRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final FollowerRepo followerRepo;

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
    public VerifyMailResponse verifyMail(VerifyMailRequest request) {
        if (userRepo.findUsersByMail(request.getMail()).isPresent()) {
            VerifyMailResponse response = new VerifyMailResponse();
            response.setMail(request.getMail());
        } else {
            throw new UserNotFoundException("User not found!");
        }
        return modelMapper.map(request, VerifyMailResponse.class);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        final Users user = userRepo.findUsersById(userRepo.findUsersByMail(request.getMail()).get().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found in database!"));
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
                    .bannerImg("https://as1.ftcdn.net/v2/jpg/05/36/23/60/1000_F_536236056_8tA6RPhBAjvvmTnoHckrfKuw4JzEZlJ8.jpg")
                    .profileImg("https://static.vecteezy.com/system/resources/previews/008/442/086/non_2x/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg")
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .createdDate(LocalDateTime.now())
                    .role("USER")
                    .build();
            return modelMapper.map(userRepo.save(user), NewUserResponse.class);
        }
    }

    @Override
    public NewUserResponse updateUser(UUID id, UpdateUserRequest request) {
        Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());
        user.setUserName(request.getUserName() != null ? request.getUserName() : user.getUserName());
        user.setMail(request.getMail() != null ? request.getMail() : user.getMail());
        user.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setAddress(request.getAddress() != null ? request.getAddress() : user.getAddress());
        user.setBannerImg(request.getBannerImg() != null ? request.getBannerImg() : user.getBannerImg());
        user.setProfileImg(request.getProfileImg() != null ? request.getProfileImg() : user.getProfileImg());
        user.setDob(request.getDob() != null ? request.getDob() : user.getDob());
        user.setGender(request.getGender() != null ? request.getGender() : user.getGender());
        user.setUpdatedDate(LocalDateTime.now());
        Users updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, NewUserResponse.class);
    }

    public void updateFollowersCount(UUID userId) {
        final Users user = userRepo.findUsersById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        long followersCount = followerRepo.countByUser_Id(userId);
        user.setFollowers(followersCount);
        userRepo.save(user);
    }
}
