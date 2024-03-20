package org.project.final_backend.service;

import org.project.final_backend.domain.request.post.NewPostRequest;
import org.project.final_backend.domain.response.post.NewPostResponse;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Post;

import java.util.UUID;

public interface PostService {


    Post findPostById(UUID id);

    PostInfo retrievePostInfo(UUID id);
    NewPostResponse createPost(NewPostRequest request);

    NewPostResponse updatePost(UUID id, NewPostRequest request);

    void deletePost(UUID id);

}
