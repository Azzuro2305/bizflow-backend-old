package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.post.NewPostRequest;
import org.project.final_backend.domain.request.post.UpdatePostRequest;
import org.project.final_backend.domain.response.post.NewPostResponse;
import org.project.final_backend.domain.response.post.UpdatePostResponse;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.PostRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private PostRepo postRepo;
    private UserRepo userRepo;
    private ModelMapper modelMapper;

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
    public void deletePost(UUID postId) {
        final Post post = postRepo.findPostById(postId).orElseThrow(()->new UserNotFoundException("Post not found "));
        postRepo.delete(post);
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepo.findAll(pageable);
    }
}
