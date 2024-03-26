package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.workexp.NewWorkExpRequest;
import org.project.final_backend.domain.request.workexp.UpdateWorkExpRequest;
import org.project.final_backend.domain.response.workexp.NewWorkExpResponse;
import org.project.final_backend.domain.response.workexp.UpdateWorkExpResponse;
import org.project.final_backend.dto.model.WorkExpInfo;
import org.project.final_backend.entity.WorkExp;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.WorkExpRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.WorkExpService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkExpServiceImpl implements WorkExpService {
    private final WorkExpRepo workExpRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public NewWorkExpResponse addWorkExp(UUID id, NewWorkExpRequest request) {
        Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        WorkExp workExp = WorkExp.builder()
                .companyName(request.getCompanyName())
                .position(request.getPosition())
                .type(request.getType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        WorkExp savedWorkExp = workExpRepo.save(workExp);
        return modelMapper.map(savedWorkExp, NewWorkExpResponse.class);
    }

    @Override
    public WorkExpInfo getWorkExp(UUID id) {
        final WorkExp workExp = workExpRepo.findWorkExpById(id)
                .orElseThrow(() -> new UserNotFoundException("Work experience not found"));
        return modelMapper.map(workExp, WorkExpInfo.class);
    }

    @Override
    public UpdateWorkExpResponse updateWorkExp(UUID id, UpdateWorkExpRequest request) {
        WorkExp workExp = workExpRepo.findWorkExpById(id)
                .orElseThrow(() -> new UserNotFoundException("Work experience not found"));
        workExp.setCompanyName(request.getCompanyName() != null ? request.getCompanyName() : workExp.getCompanyName());
        workExp.setPosition(request.getPosition() != null ? request.getPosition() : workExp.getPosition());
        workExp.setStartDate(request.getStartDate() != null ? request.getStartDate() : workExp.getStartDate());
        workExp.setEndDate(request.getEndDate() != null ? request.getEndDate() : workExp.getEndDate());
        workExp.setUpdatedDate(LocalDateTime.now());
        WorkExp updatedWorkExp = workExpRepo.save(workExp);
        return modelMapper.map(updatedWorkExp, UpdateWorkExpResponse.class);
    }

    @Override
    public List<WorkExpInfo> getAllWorkExp(UUID userId) {
        List<WorkExpInfo> workExps = workExpRepo.findAllByUserId(userId);
        return workExps.stream()
                .map(workExp -> modelMapper.map(workExp, WorkExpInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorkExp(UUID id) {
        WorkExp workExp = workExpRepo.findWorkExpById(id)
                .orElseThrow(() -> new UserNotFoundException("Work experience not found"));
        workExpRepo.delete(workExp);
    }
}