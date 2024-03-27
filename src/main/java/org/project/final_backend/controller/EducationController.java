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
@RequestMapping("/education")
public class EducationController {
    final EducationService educationService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpResponse<NewEducationResponse>> addEducation(@PathVariable UUID id, @RequestBody NewEducationRequest request){
        HttpResponse<NewEducationResponse> response =
                new HttpResponse<>(educationService.addEducation(id, request), "Education added", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<EducationInfo>> getEducation(@PathVariable UUID id){
        HttpResponse<EducationInfo> response =
                new HttpResponse<>(educationService.getEducation(id), "Education retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UpdateEducationResponse>> updateEducation(@PathVariable UUID id, @RequestBody UpdateEducationRequest request){
        HttpResponse<UpdateEducationResponse> response =
                new HttpResponse<>(educationService.updateEducation(id, request), "Education updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable UUID id){
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<HttpResponse<List<EducationInfo>>> getAllEducationsByUserId(@PathVariable UUID userId){
        HttpResponse<List<EducationInfo>> response =
                new HttpResponse<>(educationService.getAllEducationsByUserId(userId), "Educations retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
