package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.education.NewEducationRequest;
import org.project.final_backend.domain.request.education.UpdateEducationRequest;
import org.project.final_backend.domain.response.education.NewEducationResponse;
import org.project.final_backend.domain.response.education.UpdateEducationResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.EducationInfo;
import org.project.final_backend.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/educations")
public class EducationController {
    final EducationService educationService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpResponse<NewEducationResponse>> addEducation(@PathVariable UUID id, @RequestBody NewEducationRequest request){
        NewEducationResponse newEducationResponse = educationService.addEducation(id, request);
        HttpResponse<NewEducationResponse> response =
                new HttpResponse<>(newEducationResponse,newEducationResponse != null, "Education added", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<HttpResponse<EducationInfo>> getEducation(@PathVariable UUID id){
        EducationInfo education = educationService.getEducation(id);
        HttpResponse<EducationInfo> response =
                new HttpResponse<>(education,education != null, "Education retrieved", HttpStatus.OK );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UpdateEducationResponse>> updateEducation(@PathVariable UUID id, @RequestBody UpdateEducationRequest request){
        UpdateEducationResponse educationResponse = educationService.updateEducation(id, request);

        HttpResponse<UpdateEducationResponse> response =
                new HttpResponse<>(educationResponse, educationResponse != null,"Education updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse<String>> deleteEducation(@PathVariable UUID id){
        educationService.deleteEducation(id);
        HttpResponse<String> response = new HttpResponse<>("Education deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<HttpResponse<List<EducationInfo>>> getAllEducationsByUserId(@PathVariable UUID userId){
        List<EducationInfo> education = educationService.getAllEducationsByUserId(userId);
        HttpResponse<List<EducationInfo>> response =
                new HttpResponse<>(education,education != null, "Educations retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
