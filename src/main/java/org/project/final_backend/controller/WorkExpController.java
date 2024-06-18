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
@RequestMapping("/api/work-experiences")
public class WorkExpController {
    final WorkExpService workExpService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpResponse<NewWorkExpResponse>> addWorkExp(@PathVariable UUID id, @RequestBody NewWorkExpRequest request){
        NewWorkExpResponse workExp = workExpService.addWorkExp(id, request);
        HttpResponse<NewWorkExpResponse> response =
                new HttpResponse<>(workExp, workExp != null, "Work experience added", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<HttpResponse<WorkExpInfo>> getWorkExp(@PathVariable UUID id){
        WorkExpInfo workExp = workExpService.getWorkExp(id);
        HttpResponse<WorkExpInfo> response =
                new HttpResponse<>(workExp, workExp!= null, "Work experience retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UpdateWorkExpResponse>> updateWorkExp(@PathVariable UUID id, @RequestBody UpdateWorkExpRequest request){
        UpdateWorkExpResponse workExp = workExpService.updateWorkExp(id, request);

        HttpResponse<UpdateWorkExpResponse> response =
                new HttpResponse<>(workExp,workExp != null, "Work experience updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/work-experiences/{id}")
    public ResponseEntity<HttpResponse<String>> deleteWorkExp(@PathVariable UUID id){
        workExpService.deleteWorkExp(id);
        HttpResponse<String> response = new HttpResponse<>("Work experience deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<HttpResponse<List<WorkExpInfo>>> getAllWorkExpByUserId(@PathVariable UUID userId){
        List<WorkExpInfo> workExp = workExpService.getAllWorkExpByUserId(userId);
        HttpResponse<List<WorkExpInfo>> response =
                new HttpResponse<>(workExp, workExp != null, "Work experiences retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
