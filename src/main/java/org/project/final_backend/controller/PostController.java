package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.post.NewPostRequest;
import org.project.final_backend.domain.response.post.NewPostResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/feed")
public class PostController {

     private PostService postService;


    @PostMapping
    public ResponseEntity<HttpResponse<NewPostResponse>> createPost(@RequestBody NewPostRequest request){
        HttpResponse<NewPostResponse> response=new HttpResponse<>(postService.createPost(request),"Successfully upload",HttpStatus.CREATED);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostInfo> retrievePostInfo(@RequestParam UUID id){
        return ResponseEntity.ok(postService.retrievePostInfo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewPostResponse> updatePost(@PathVariable UUID id, @RequestBody NewPostRequest request){
        NewPostResponse response= postService.updatePost(id,request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewPostResponse> deletePost(@PathVariable UUID id){
        postService.deletePost(id);

        return ResponseEntity.noContent().build();
    }


}
