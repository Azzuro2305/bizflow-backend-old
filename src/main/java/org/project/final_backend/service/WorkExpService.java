package org.project.final_backend.service;

import org.project.final_backend.domain.request.workexp.NewWorkExpRequest;
import org.project.final_backend.domain.request.workexp.UpdateWorkExpRequest;
import org.project.final_backend.domain.response.workexp.NewWorkExpResponse;
import org.project.final_backend.domain.response.workexp.UpdateWorkExpResponse;
import org.project.final_backend.dto.model.WorkExpInfo;
import org.project.final_backend.entity.WorkExp;

import java.util.List;
import java.util.UUID;

public interface WorkExpService {
    NewWorkExpResponse addWorkExp(UUID id, NewWorkExpRequest request);
    WorkExpInfo getWorkExp(UUID id);
    UpdateWorkExpResponse updateWorkExp(UUID id, UpdateWorkExpRequest request);
    List<WorkExpInfo> getAllWorkExpByUserId(UUID userId);
    void deleteWorkExp(UUID id);
}
