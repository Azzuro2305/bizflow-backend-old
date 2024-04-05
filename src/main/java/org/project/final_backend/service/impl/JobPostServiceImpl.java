package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.jobpost.NewJobPostRequest;
import org.project.final_backend.domain.request.jobpost.UpdateJobPostRequest;
import org.project.final_backend.domain.response.jobpost.NewJobPostResponse;
import org.project.final_backend.domain.response.jobpost.UpdateJobPostResponse;
import org.project.final_backend.dto.model.JobPostDto;
import org.project.final_backend.dto.model.JobPostInfo;
import org.project.final_backend.entity.JobPost;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.JobPostRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.JobPostService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private final UserRepo userRepo;
    private final JobPostRepo jobPostRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<JobPost> findJobPostsByUsersId(UUID userId) {
        return jobPostRepo.findJobPostsByUsers_Id(userId);
    }

    @Override
    public JobPostInfo retrieveJobPostInfo(UUID id) {
        JobPost jobPost = jobPostRepo.findJobPostById(id)
                .orElseThrow(() -> new UserNotFoundException("Job post not found"));
        return jobPostCustomModelMapper3().map(jobPost, JobPostInfo.class);
    }


    @Override
    public NewJobPostResponse createJobPost(NewJobPostRequest request) {
        Users user = userRepo.findUsersById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        JobPost jobPost = JobPost.builder()
                .users(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .type(request.getType())
                .category(request.getCategory())
                .qualification(request.getQualification())
                .exp(request.getExp())
                .salary(request.getSalary())
                .companyName(request.getCompanyName())
                .uploadTime(LocalDateTime.now())
                .build();
        jobPostRepo.save(jobPost);
        long jobPostCount = jobPostRepo.countByUsers_Id(request.getUserId());
        user.setJobPosts(jobPostCount);
        userRepo.save(user);
        return modelMapper.map(jobPost, NewJobPostResponse.class);
    }

    @Override
    public UpdateJobPostResponse updateJobPost(UpdateJobPostRequest request) {
        JobPost jobPost = jobPostRepo.findJobPostById(request.getJobPostId())
                .orElseThrow(() -> new UserNotFoundException("Job post not found"));
        jobPost.setTitle(request.getTitle() != null ? request.getTitle() : jobPost.getTitle());
        jobPost.setDescription(request.getDescription() != null ? request.getDescription() : jobPost.getDescription());
        jobPost.setLocation(request.getLocation() != null ? request.getLocation() : jobPost.getLocation());
        jobPost.setType(request.getType() != null ? request.getType() : jobPost.getType());
        jobPost.setCategory(request.getCategory() != null ? request.getCategory() : jobPost.getCategory());
        jobPost.setQualification(request.getQualification() != null ? request.getQualification() : jobPost.getQualification());
        jobPost.setExp(request.getExp() != null ? request.getExp() : jobPost.getExp());
        jobPost.setSalary(request.getSalary() != null ? request.getSalary() : jobPost.getSalary());
        jobPost.setCompanyName(request.getCompanyName() != null ? request.getCompanyName() : jobPost.getCompanyName());
        jobPost.setUpdateTime(LocalDateTime.now());
        jobPost.setDeleted(request.isDeleted());
        return modelMapper.map(jobPostRepo.save(jobPost), UpdateJobPostResponse.class);
    }

    @Override
    public void deleteJobPost(UUID userId, UUID id) {
        JobPost jobPost = jobPostRepo.findJobPostById(id)
                .orElseThrow(() -> new UserNotFoundException("Job post not found"));
        jobPostRepo.delete(jobPost);
        Users user = userRepo.findUsersById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        long jobPostCount = jobPostRepo.countByUsers_Id(userId);
        user.setJobPosts(jobPostCount);
        userRepo.save(user);
    }

    @Bean
    public ModelMapper jobPostCustomModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(JobPost.class, JobPostDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), JobPostDto::setUserId);
            mapper.map(src -> src.getUsers().getProfileImg(), JobPostDto::setProfileImg);
            mapper.map(src -> src.getUsers().getUserName(), JobPostDto::setUserName);
            mapper.map(JobPost::getId, JobPostDto::setId);
        });
        // Add more mappings if you have other DTOs
        return modelMapper;
    }

    @Bean
    public ModelMapper jobPostCustomModelMapper3() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(JobPost.class, JobPostDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), JobPostDto::setUserId);
            mapper.map(src -> src.getUsers().getProfileImg(), JobPostDto::setProfileImg);
            mapper.map(src -> src.getUsers().getUserName(), JobPostDto::setUserName);
            mapper.map(JobPost::getId, JobPostDto::setId);
        });
        modelMapper.typeMap(JobPost.class, JobPostInfo.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), JobPostInfo::setUserId);
            mapper.map(src -> src.getUsers().getProfileImg(), JobPostInfo::setProfileImg);
            mapper.map(src -> src.getUsers().getUserName(), JobPostInfo::setUserName);
            mapper.map(src -> src.getUsers().getFollowers(), JobPostInfo::setFollowers);
            mapper.map(JobPost::getId, JobPostInfo::setId);
            // Add mappings for other properties as needed
        });
        // Add more mappings if you have other DTOs
        return modelMapper;
    }

    @Override
    public Page<JobPostDto> getAllJobPosts(Pageable pageable, String[] sort) {
        List<Sort.Order> orders = new LinkedHashSet<>(Arrays.stream(sort)
                .map(s -> s.split(","))
                .map(arr -> arr.length > 1 ? (arr[1].equals("asc") ? Sort.Order.desc(arr[0]) : Sort.Order.asc(arr[0])) : Sort.Order.asc("uploadTime"))
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());
        Sort sorting = Sort.by(orders);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
        Page<JobPost> jobPosts = jobPostRepo.findAll(sortedPageable);
        return jobPosts.map(jobPost -> jobPostCustomModelMapper().map(jobPost, JobPostDto.class));
    }
}
