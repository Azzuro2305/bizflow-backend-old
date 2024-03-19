package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.NewPostRequest;
import org.project.final_backend.domain.response.NewPostResponse;
import org.project.final_backend.domain.response.NewUserResponse;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.PostRepo;
import org.project.final_backend.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepo postRepo;

    private ModelMapper modelMapper;


    @Override
    public Post findPostById(Integer id) {
        final Post post=postRepo.findPostById(id)
                .orElseThrow(()-> new UserNotFoundException("Post not found"));
        return post;
    }

    @Override
    public PostInfo retrievePostInfo(Integer id) {
        final Post post=postRepo.findPostById(id)
                .orElseThrow(()->new UserNotFoundException("Post not found "));

        return modelMapper.map(post, PostInfo.class);
    }


    @Override
    public NewPostResponse createPost(NewPostRequest request) {

     Post post= Post.builder()
             .caption(request.getCaption())
             .uploadPhoto(request.getUploadPhoto())
             .build();

        return modelMapper.map(postRepo.save(post),NewPostResponse.class);
    }

    @Override
    public NewPostResponse updatePost(Integer id, NewPostRequest request) {
//        Users user = userRepo.findUsersById(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found!"));
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setUserName(request.getUserName());
////        user.setMail(request.getMail());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setAddress(request.getAddress());
//        user.setDob(request.getDob());
//        user.setUpdatedDate(LocalDateTime.now());
//        return modelMapper.map(userRepo.save(user), NewUserResponse.class);

        Post post=postRepo.findPostById(id).orElseThrow(()->new UserNotFoundException("Post not found!"));
        post.setCaption(request.getCaption());
        post.setUploadPhoto(request.getUploadPhoto());

        return modelMapper.map(postRepo.save(post),NewPostResponse.class);
    }

    @Override
    public void deletePost(Integer id) {

        final Post post=postRepo.findPostById(id).orElseThrow(()->new UserNotFoundException("Post not found "));
        postRepo.delete(post);
    }
}
