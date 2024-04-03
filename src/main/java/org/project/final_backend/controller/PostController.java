package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.post.NewPostRequest;
import org.project.final_backend.domain.request.post.UpdatePostRequest;
import org.project.final_backend.domain.response.post.NewPostResponse;
import org.project.final_backend.domain.response.post.UpdatePostResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.PostDto;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Post;
import org.project.final_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/feed")
public class PostController {
    private PostService postService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<HttpResponse<NewPostResponse>> createPost(@RequestBody NewPostRequest request) {
        HttpResponse<NewPostResponse> response = new HttpResponse<>(postService.createPost(request), "Successfully created", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<HttpResponse<PostInfo>> retrievePostInfo(@RequestParam UUID postId) {
        HttpResponse<PostInfo> response = new HttpResponse<>(postService.retrievePostInfo(postId), "Successfully retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpResponse<UpdatePostResponse>> updatePost(@RequestBody UpdatePostRequest request) {
        HttpResponse<UpdatePostResponse> response = new HttpResponse<>(postService.updatePost(request), "Successfully updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HttpResponse<String>> deletePost(@RequestParam UUID userId, UUID postId) {
        postService.deletePost(userId, postId);
        HttpResponse<String> response = new HttpResponse<>("Successfully deleted", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/posts")
    public Page<PostDto> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uploadTime,desc") String[] sort) {
        return postService.getAllPosts(PageRequest.of(page, size), sort);
    }

//    @GetMapping("/posts/{userId}")
//    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable UUID userId) {
//        return ResponseEntity.ok(postService.findPostsByUsersId(userId));
//    }

    @Bean
    public ModelMapper customModelMapper1() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Post.class, PostDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), PostDto::setUserId);
            mapper.map(Post::getId, PostDto::setId);
        });
        modelMapper.typeMap(Post.class, PostInfo.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), PostInfo::setUserId);
        });
        return modelMapper;
    }


    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<PostInfo>> getPostsByUserId(@PathVariable UUID userId) {
        List<Post> posts = postService.findPostsByUsersId(userId);
        List<PostInfo> postInfos = posts.stream()
                .map(post -> customModelMapper1().map(post, PostInfo.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(postInfos);
    }

//    @GetMapping("/{postId}")
//    public Post getPostById(@PathVariable UUID postId) {
//        return postService.findPostById(postId);
//    }

//    @GetMapping("/search")
//    public Page<Post> getALlPosts(
//            @RequestParam int pageNumber,
//            @RequestParam(defaultValue = "") String searchKey
//    ) {
//
//        Page<Post> result = postService.getAllPosts(pageNumber, searchKey);
//        return result;
//    }

//    @GetMapping("/search")
//    public Page<PostDto> getAllPosts(
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "") String searchKey
//    ) {
//        return postService.searchPosts(pageNumber, searchKey);
//    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPostsAndUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "") String searchKey
    ) {
        Map<String, Object> result = postService.searchPostsAndUsers(pageNumber, searchKey);
        return ResponseEntity.ok(result);
    }
}
