package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.NewUserRequest;
import org.project.final_backend.domain.NewUserResponse;
import org.project.final_backend.entity.Users;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public NewUserResponse registerUser(NewUserRequest request) {
        Users user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .mail(request.getMail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .role(1)
                .build();
        return modelMapper.map(userRepo.save(user), NewUserResponse.class);
    }
}
