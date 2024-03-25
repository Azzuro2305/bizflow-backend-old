package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.following.NewFollowingRequest;
import org.project.final_backend.service.FollowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/following")
public class FollowingController {
    private final FollowingService followingService;

    @PostMapping
    public ResponseEntity<Void> addFollowing(@RequestBody NewFollowingRequest request){
        followingService.addFollowing(request);
        followingService.updateFollowingCount(request.getUserId());
        return ResponseEntity.noContent().build();
    }
}
