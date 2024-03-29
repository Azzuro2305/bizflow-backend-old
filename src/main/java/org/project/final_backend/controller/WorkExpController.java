package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.workexp.NewWorkExpRequest;
import org.project.final_backend.domain.request.workexp.UpdateWorkExpRequest;
import org.project.final_backend.domain.response.workexp.NewWorkExpResponse;
import org.project.final_backend.domain.response.workexp.UpdateWorkExpResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.WorkExpInfo;
import org.project.final_backend.service.WorkExpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/work-exp")
public class WorkExpController {
    final WorkExpService workExpService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpResponse<NewWorkExpResponse>> addWorkExp(@PathVariable UUID id, @RequestBody NewWorkExpRequest request){
        HttpResponse<NewWorkExpResponse> response =
                new HttpResponse<>(workExpService.addWorkExp(id, request), "Work experience added", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<WorkExpInfo>> getWorkExp(@PathVariable UUID id){
        HttpResponse<WorkExpInfo> response =
                new HttpResponse<>(workExpService.getWorkExp(id), "Work experience retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UpdateWorkExpResponse>> updateWorkExp(@PathVariable UUID id, @RequestBody UpdateWorkExpRequest request){
        HttpResponse<UpdateWorkExpResponse> response =
                new HttpResponse<>(workExpService.updateWorkExp(id, request), "Work experience updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkExp(@PathVariable UUID id){
        workExpService.deleteWorkExp(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<HttpResponse<List<WorkExpInfo>>> getAllWorkExpByUserId(@PathVariable UUID userId){
        HttpResponse<List<WorkExpInfo>> response =
                new HttpResponse<>(workExpService.getAllWorkExpByUserId(userId), "Work experiences retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
