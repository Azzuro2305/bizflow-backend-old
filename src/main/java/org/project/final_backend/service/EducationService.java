package org.project.final_backend.service;

import org.project.final_backend.domain.request.education.NewEducationRequest;
import org.project.final_backend.domain.request.education.UpdateEducationRequest;
import org.project.final_backend.domain.response.education.NewEducationResponse;
import org.project.final_backend.domain.response.education.UpdateEducationResponse;
import org.project.final_backend.dto.model.EducationInfo;

import java.util.UUID;

public interface EducationService {
    NewEducationResponse addEducation(UUID id, NewEducationRequest request);
    EducationInfo getEducation(UUID id);
    UpdateEducationResponse updateEducation(UUID id, UpdateEducationRequest request);
    void deleteEducation(UUID id);
}
