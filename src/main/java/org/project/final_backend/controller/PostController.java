package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.post.NewPostRequest;
import org.project.final_backend.domain.request.post.UpdatePostRequest;
import org.project.final_backend.domain.response.post.NewPostResponse;
import org.project.final_backend.domain.response.post.UpdatePostResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Post;
import org.project.final_backend.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<HttpResponse<PostInfo>> retrievePostInfo(@RequestParam UUID postId){
        HttpResponse<PostInfo> response=new HttpResponse<>(postService.retrievePostInfo(postId),"Successfully retrieved",HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpResponse<UpdatePostResponse>> updatePost(@RequestBody UpdatePostRequest request){
        HttpResponse<UpdatePostResponse> response=new HttpResponse<>(postService.updatePost(request),"Successfully updated",HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts")
    public Page<Post> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uploadTime,desc") String[] sort) {
        List<Sort.Order> orders = new LinkedHashSet<>(Arrays.stream(sort)
                .map(s -> s.split(","))
                .map(arr -> arr.length > 1 ? (arr[1].equals("desc") ? Sort.Order.desc(arr[0]) : Sort.Order.asc(arr[0])) : Sort.Order.asc("uploadTime"))
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());
        Sort sorting = Sort.by(orders);
        return postService.getAllPosts(PageRequest.of(page, size, sorting));
    }
}
