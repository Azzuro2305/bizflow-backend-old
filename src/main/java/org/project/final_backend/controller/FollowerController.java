package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.service.FollowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/follower")
public class FollowerController {
    private final FollowerService followerService;

    @PostMapping
    public ResponseEntity<Void> addFollower(@RequestBody NewFollowerRequest request){
        followerService.addFollower(request);
        followerService.updateFollowerCount(UUID.fromString(request.getUserId().toString()));
        return ResponseEntity.noContent().build();
    }
}
