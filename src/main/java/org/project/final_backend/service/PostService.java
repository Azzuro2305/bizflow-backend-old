package org.project.final_backend.service;

import org.project.final_backend.domain.request.NewPostRequest;
import org.project.final_backend.domain.response.NewPostResponse;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Post;

import java.util.UUID;

public interface PostService {


    Post findPostById(Integer id);

    PostInfo retrievePostInfo(Integer id);
    NewPostResponse createPost(NewPostRequest request);

    NewPostResponse updatePost(Integer id, NewPostRequest request);

    void deletePost(Integer id);

}
