package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.password.*;
import org.project.final_backend.domain.request.user.UpdateUserRequest;
import org.project.final_backend.domain.response.user.NewUserResponse;
import org.project.final_backend.domain.subscribe.Subscribe;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.InvalidPasswordException;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Page<Users> getAllUsers(Pageable pageable, String[] sort) {
        List<Sort.Order> orders =  new LinkedHashSet<>(Arrays.stream(sort)
                .map(s -> s.split(","))
                .map(arr -> arr.length > 1 ? (arr[1].equals("asc") ? Sort.Order.desc(arr[0]) : Sort.Order.asc(arr[0])) : Sort.Order.asc("userName"))
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());

        Sort sorting = Sort.by(orders);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);

        Page<Users> users =  userRepo.findAll(sortedPageable);

        System.out.println(sortedPageable);
        System.out.println(users);

        return users;
    }

    @Override
    public Users findUsersById(UUID id) {
        final Users user = userRepo
                .findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return user;
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
    public void deleteUser(UUID id) {
        final Users user = userRepo
                .findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userRepo.delete(user);
    }



    @Override
    public NewUserResponse updateUser(UUID id, UpdateUserRequest request) {
        Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setUserName(request.getUserName() != null ? request.getUserName() : user.getUserName());
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


}
