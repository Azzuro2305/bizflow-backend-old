package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.post.NewPostRequest;
import org.project.final_backend.domain.request.post.UpdatePostRequest;
import org.project.final_backend.domain.response.post.NewPostResponse;
import org.project.final_backend.domain.response.post.UpdatePostResponse;
import org.project.final_backend.dto.model.PostDto;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.PostRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public Post findPostById(UUID id) {
        final Post post=postRepo.findPostById(id)
                .orElseThrow(()-> new UserNotFoundException("Post not found"));
        return post;
    }

    @Override
    public PostInfo retrievePostInfo(UUID id) {
        final Post post=postRepo.findPostById(id)
                .orElseThrow(()->new UserNotFoundException("Post not found "));

        return modelMapper.map(post, PostInfo.class);
    }

    @Override
    public NewPostResponse createPost(NewPostRequest request) {
        Users user = userRepo.findUsersById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
         Post post= Post.builder()
                 .users(user)
                 .accountName(user.getUserName())
                 .followers(user.getFollowers())
                 .caption(request.getCaption())
                 .uploadTime(LocalDateTime.now())
                 .uploadPhoto(request.getUploadPhoto())
                 .profileImg(user.getProfileImg())
                 .build();
         postRepo.save(post);
         long postCount = postRepo.countByUsers_Id(request.getUserId());
         user.setPosts(postCount);
         userRepo.save(user);
         return modelMapper.map(postRepo.save(post),NewPostResponse.class);
    }

    @Override
    public UpdatePostResponse updatePost(UpdatePostRequest request) {
        Post post=postRepo.findPostById(request.getPostId()).orElseThrow(()->new UserNotFoundException("Post not found!"));
        post.setCaption(request.getCaption() != null ? request.getCaption() : post.getCaption());
        post.setUploadPhoto(request.getUploadPhoto() != null ? request.getUploadPhoto() : post.getUploadPhoto());
        post.setUpdateTime(LocalDateTime.now());
        return modelMapper.map(postRepo.save(post),UpdatePostResponse.class);
    }

    @Override
    public void deletePost(UUID userId, UUID postId) {
        final Post post = postRepo.findPostById(postId).orElseThrow(()->new UserNotFoundException("Post not found "));
        final Users user = userRepo.findUsersById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        postRepo.delete(post);
        user.setPosts(user.getPosts()-1);
        userRepo.save(user);

    }
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.typeMap(Post.class,PostDto.class).addMappings(mapper -> mapper.skip(PostDTO::setUser));
//        return modelMapper;
//    }

    @Override
    public List<Post> findPostsByUsersId(UUID userId) {
        return postRepo.findPostsByUsers_Id(userId);
    }

    @Bean
    public ModelMapper customModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Post.class, PostDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsers().getId(), PostDto::setUserId);
            mapper.map(Post::getId, PostDto::setId);
        });
        return modelMapper;
    }
    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepo.findAll(pageable);
        return posts.map(post -> customModelMapper().map(post, PostDto.class));
    }

    @Override
    public Map<String, Object> searchPostsAndUsers(int pageNumber, String searchKey) {
        Specification<Post> postSpec = (root, query, cb) -> {
            if (searchKey == null || searchKey.isEmpty()) {
                return cb.conjunction();
            } else {
                return cb.like(root.get("caption"), "%" + searchKey + "%"); // filter by caption
            }
        };

        Specification<Users> userSpec = (root, query, cb) -> {
            if (searchKey == null || searchKey.isEmpty()) {
                return cb.conjunction();
            } else {
                return cb.like(root.get("userName"), "%" + searchKey + "%"); // filter by userName
            }
        };

        Page<Post> posts = postRepo.findAll(postSpec, PageRequest.of(pageNumber, 10, Sort.by("uploadTime").descending()));
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        Page<Users> users = userRepo.findAll(userSpec, PageRequest.of(pageNumber, 10, Sort.by("userName").ascending()));
        List<UserInfo> userInfos = users.stream()
                .map(user -> modelMapper.map(user, UserInfo.class))
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("posts", postDtos);
        result.put("users", userInfos);

        return result;
    }

}
