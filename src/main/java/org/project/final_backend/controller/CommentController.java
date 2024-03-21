package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.NewCommentRequest;
import org.project.final_backend.domain.response.NewCommentResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.CommentInfo;
import org.project.final_backend.dto.model.PostInfo;
import org.project.final_backend.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity<HttpResponse<NewCommentResponse>> createComment(@RequestParam UUID user_id, @RequestParam UUID post_id, @RequestBody NewCommentRequest request){
        HttpResponse<NewCommentResponse> response = new HttpResponse<>(commentService.createComment(user_id, post_id, request),"comment created",HttpStatus.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewCommentResponse> updateComment(@PathVariable UUID id, @RequestBody NewCommentRequest request){
        NewCommentResponse response= commentService.updateComment(id,request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommentInfo> retrieveCommentInfo(@RequestParam UUID id){
        return ResponseEntity.ok(commentService.retrieveCommentInfo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewCommentResponse> deleteComment(@PathVariable UUID id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
