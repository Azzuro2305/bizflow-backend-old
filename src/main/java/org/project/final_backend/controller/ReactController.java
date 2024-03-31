package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.react.NewReactRequest;
import org.project.final_backend.domain.response.react.NewReactResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.service.ReactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/reacts")
public class ReactController {

    private ReactService reactService;

    @PostMapping
    public ResponseEntity<HttpResponse<NewReactResponse>> createReact(@RequestBody NewReactRequest request){
        HttpResponse<NewReactResponse> response = new HttpResponse<>(reactService.createReact(request),"Ok",HttpStatus.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpResponse<String>> deleteReact(@RequestParam UUID id){
        reactService.deleteReact(id);
        HttpResponse<String> response = new HttpResponse<>("React deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}