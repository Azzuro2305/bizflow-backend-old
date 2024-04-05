package org.project.final_backend.service;

import org.project.final_backend.domain.request.jobpost.NewJobPostRequest;
import org.project.final_backend.domain.request.jobpost.UpdateJobPostRequest;
import org.project.final_backend.domain.response.jobpost.NewJobPostResponse;
import org.project.final_backend.domain.response.jobpost.UpdateJobPostResponse;
import org.project.final_backend.dto.model.JobPostDto;
import org.project.final_backend.dto.model.JobPostInfo;
import org.project.final_backend.entity.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface JobPostService {
    List<JobPost> findJobPostsByUsersId(UUID userId);
    JobPostInfo retrieveJobPostInfo(UUID id);
    NewJobPostResponse createJobPost(NewJobPostRequest request);
    UpdateJobPostResponse updateJobPost(UpdateJobPostRequest request);
    void deleteJobPost(UUID userId, UUID id);
    Page<JobPostDto> getAllJobPosts(Pageable pageable, String[] sort);
}
