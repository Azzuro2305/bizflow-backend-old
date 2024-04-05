package org.project.final_backend.service;

import org.project.final_backend.domain.request.NewCommentRequest;
import org.project.final_backend.domain.response.NewCommentResponse;
import org.project.final_backend.dto.model.CommentInfo;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.entity.Comment;

import java.util.UUID;

public interface CommentService {
    Comment findCommentById(UUID Id);
    NewCommentResponse createComment(UUID user_id, UUID post_id, NewCommentRequest request);
    NewCommentResponse updateComment(UUID id,NewCommentRequest request);
    CommentInfo retrieveCommentInfo(UUID id);
    void deleteComment(UUID id);
}
