package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.jobpost.NewJobPostRequest;
import org.project.final_backend.domain.request.jobpost.UpdateJobPostRequest;
import org.project.final_backend.domain.response.jobpost.NewJobPostResponse;
import org.project.final_backend.domain.response.jobpost.UpdateJobPostResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.JobPostDto;
import org.project.final_backend.dto.model.JobPostInfo;
import org.project.final_backend.entity.JobPost;
import org.project.final_backend.service.JobPostService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/job")
public class JobPostController {
    private JobPostService jobPostService;
    private final ModelMapper modelMapper;
    @PostMapping
    public ResponseEntity<HttpResponse<NewJobPostResponse>> createJobPost(@RequestBody NewJobPostRequest request) {
        HttpResponse<NewJobPostResponse> response = new HttpResponse<>(jobPostService.createJobPost(request), "Successfully created", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/jobposts")
    public Page<JobPostDto> getAllJobPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uploadTime,desc") String[] sort) {
        return jobPostService.getAllJobPosts(PageRequest.of(page, size), sort);
    }

    @PutMapping
    public ResponseEntity<HttpResponse<UpdateJobPostResponse>> updateJobPost(@RequestBody UpdateJobPostRequest request) {
        HttpResponse<UpdateJobPostResponse> response = new HttpResponse<>(jobPostService.updateJobPost(request), "Successfully updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<JobPostInfo>> getJobPostById(@PathVariable UUID id) {
        JobPostInfo jobPostInfo = jobPostService.retrieveJobPostInfo(id);
        HttpResponse<JobPostInfo> response = new HttpResponse<>(jobPostInfo, "Successfully retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Bean
    public ModelMapper jobPostCustomModelMapper1() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(JobPost.class, JobPostDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), JobPostDto::setUserId);
            mapper.map(src -> src.getUsers().getUserName(), JobPostDto::setUserName);
            mapper.map(src -> src.getUsers().getProfileImg(), JobPostDto::setProfileImg);
            mapper.map(JobPost::getId, JobPostDto::setId);
        });
        modelMapper.typeMap(JobPost.class, JobPostInfo.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), JobPostInfo::setUserId);
            mapper.map(src -> src.getUsers().getUserName(), JobPostInfo::setUserName);
            mapper.map(src -> src.getUsers().getProfileImg(), JobPostInfo::setProfileImg);
        });
        return modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<JobPostInfo>> getJobPostsByUserId(@RequestParam UUID userId) {
        List<JobPost> jobPosts = jobPostService.findJobPostsByUsersId(userId);
        List<JobPostInfo> jobPostInfos = jobPosts.stream()
                .map(jobPost -> jobPostCustomModelMapper1().map(jobPost, JobPostInfo.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(jobPostInfos);
    }


    @DeleteMapping()
    public ResponseEntity<HttpResponse<String>> deleteJobPost(@RequestParam UUID userId, UUID jobPostId) {
        jobPostService.deleteJobPost(userId, jobPostId);
        HttpResponse<String> response = new HttpResponse<>("Successfully deleted", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
