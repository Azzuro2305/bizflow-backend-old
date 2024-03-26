package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.education.NewEducationRequest;
import org.project.final_backend.domain.request.education.UpdateEducationRequest;
import org.project.final_backend.domain.response.education.NewEducationResponse;
import org.project.final_backend.domain.response.education.UpdateEducationResponse;
import org.project.final_backend.dto.model.EducationInfo;
import org.project.final_backend.entity.Education;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.EducationRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.EducationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EducationServiceImpl implements EducationService {
    private final EducationRepo educationRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public NewEducationResponse addEducation(UUID id, NewEducationRequest request) {
        Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Education education = Education.builder()
                .schoolName(request.getSchoolName())
                .degree(request.getDegree())
                .fieldOfStudy(request.getFieldOfStudy())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        Education savedEducation = educationRepo.save(education);
        return modelMapper.map(savedEducation, NewEducationResponse.class);
    }

    @Override
    public EducationInfo getEducation(UUID id) {
        final Education education = educationRepo.findEducationById(id)
                .orElseThrow(() -> new UserNotFoundException("Education not found"));
        return modelMapper.map(education, EducationInfo.class);
    }

    @Override
    public UpdateEducationResponse updateEducation(UUID id, UpdateEducationRequest request) {
        Education education = educationRepo.findEducationById(id)
                .orElseThrow(() -> new UserNotFoundException("Education not found"));
        education.setSchoolName(request.getSchoolName() != null ? request.getSchoolName() : education.getSchoolName());
        education.setDegree(request.getDegree() != null ? request.getDegree() : education.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy() != null ? request.getFieldOfStudy() : education.getFieldOfStudy());
        education.setStartDate(request.getStartDate() != null ? request.getStartDate() : education.getStartDate());
        education.setEndDate(request.getEndDate() != null ? request.getEndDate() : education.getEndDate());
        education.setUpdatedDate(LocalDateTime.now());
        Education updatedEducation = educationRepo.save(education);
        return modelMapper.map(updatedEducation, UpdateEducationResponse.class);
    }

    @Override
    public void deleteEducation(UUID id) {
        Education education = educationRepo.findEducationById(id)
                .orElseThrow(() -> new UserNotFoundException("Education not found"));
        educationRepo.delete(education);
    }

    @Override
    public List<EducationInfo> getAllEducationsByUserId(UUID userId) {
        List<Education> educations = educationRepo.findAllByUserId(userId);
        return educations.stream()
                .map(education -> modelMapper.map(education, EducationInfo.class))
                .collect(Collectors.toList());
    }
}
