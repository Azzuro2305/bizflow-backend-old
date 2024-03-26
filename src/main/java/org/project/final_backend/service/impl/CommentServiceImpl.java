package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.NewCommentRequest;
import org.project.final_backend.domain.response.NewCommentResponse;
import org.project.final_backend.dto.model.CommentInfo;
import org.project.final_backend.entity.Comment;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserFoundException;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.CommentRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.CommentService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepo commentRepo;
    private UserRepo userRepo;

    private ModelMapper modelMapper;


    @Override
    public Comment findCommentById(UUID Id) {
        final Comment comment = commentRepo.findCommentById(Id)
                .orElseThrow(()-> new UserFoundException("Comment not found"));

        return comment;
    }

    @Override
    public NewCommentResponse createComment(UUID user_id, UUID post_id, NewCommentRequest request) {
        Users user = userRepo.findUsersById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
      Comment comment = Comment.builder()
                .text(request.getText())
                .userName(user.getUserName())
                .userprofile(user.getProfileImg())
                .post(Post.builder().id(post_id).build())
                .build();

        return modelMapper.map(commentRepo.save(comment), NewCommentResponse.class);
    }

    @Override
    public NewCommentResponse updateComment(UUID id, NewCommentRequest request) {
        Comment comment =commentRepo.findCommentById(id).orElseThrow(()->new UserNotFoundException("Comment not found!"));
        comment.setText(request.getText());

        return modelMapper.map(commentRepo.save(comment), NewCommentResponse.class);
    }

    @Override
    public CommentInfo retrieveCommentInfo(UUID id) {
        final Comment comment =  commentRepo.findCommentById(id)
                .orElseThrow(()-> new UserFoundException("comment not find"));

        return modelMapper.map(comment, CommentInfo.class);
    }

    @Override
    public void deleteComment(UUID id) {
        final Comment comment = commentRepo.findCommentById(id).orElseThrow(()-> new UserNotFoundException("comment not found"));
        commentRepo.delete(comment);
    }
}
