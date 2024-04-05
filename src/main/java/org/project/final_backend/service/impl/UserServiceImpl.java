package org.project.final_backend.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.ResetPasswordUserIdRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.domain.response.VerifyMailResponse;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.domain.subscribe.Subscribe;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.InvalidPasswordException;
import org.project.final_backend.exception.UserFoundException;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.FollowerRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    public UserInfo retrieveUserInfoByUserName(String userName) {
        final Users user = userRepo
                .findUsersByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return modelMapper.map(user, UserInfo.class);
    }

    @Override
    public void purchase(Subscribe subscribe) {
        final Users user = userRepo
                .findUsersById(subscribe.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setRole(subscribe.getSubscriptionType());
        userRepo.save(user);
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
            user.setUpdatedDate(new Date());
            userRepo.save(user);
        }
    }

    @Override
    public void resetPasswordWithUserId(UUID id, ResetPasswordUserIdRequest request) {
        final Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
            user.setUpdatedDate(new Date());
            userRepo.save(user);
        }
    }

    @Override
    public void resetPasswordWithOTP(ResetPasswordOTPRequest request) {
        final Users user = userRepo.findUsersByMail(request.getMail())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (!authService.validateOTP(request)) {
            throw new InvalidPasswordException("Invalid OTP!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
            user.setUpdatedDate(new Date());
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
//            UUID uuid = UUID.randomUUID();
//            long mostSigBits = Math.abs(uuid.getMostSignificantBits());
//            UUID new_uuid = new UUID(mostSigBits, 0);
            Users user = Users.builder()
//                    .id(new_uuid)
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .userName(request.getUserName())
                    .mail(request.getMail())
                    .bannerImg("https://images.pexels.com/photos/573130/pexels-photo-573130.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2")
                    .profileImg("https://static.vecteezy.com/system/resources/previews/008/442/086/non_2x/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg")
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .createdDate(new Date())
                    .role("FREE_USER")
                    .build();
            userRepo.save(user);
            Users newUser = userRepo.findUsersByMail(request.getMail()).get();

            Map<String, Object> userMap = new HashMap<>();
            BeanUtils.copyProperties(newUser, userMap);
            userMap.put("id", newUser.getId().toString());
            userMap.put("firstName", newUser.getFirstName());
            userMap.put("lastName", newUser.getLastName());
            userMap.put("userName", newUser.getUserName());
            userMap.put("mail", newUser.getMail());
            userMap.put("bannerImg", newUser.getBannerImg());
            userMap.put("profileImg", newUser.getProfileImg());
            userMap.put("password", newUser.getPassword());
            userMap.put("createdDate", newUser.getCreatedDate());
            userMap.put("role", newUser.getRole());

            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(newUser.getId().toString());
            ApiFuture<WriteResult> result = docRef.set(userMap);
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
        user.setBio(request.getBio() != null ? request.getBio() : user.getBio());
        user.setUpdatedDate(new Date());
        Users updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, NewUserResponse.class);
    }

//    public void updateFollowersCount(UUID userId) {
//        final Users user = userRepo.findUsersById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found!"));
//        long followersCount = followerRepo.countByUser_Id(userId);
//        user.setFollowers(followersCount);
//        userRepo.save(user);
//    }

    public Page<Users> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }
}
