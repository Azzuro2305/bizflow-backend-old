package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.domain.following.NewFollowingRequest;
import org.project.final_backend.service.FollowerService;
import org.project.final_backend.service.FollowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/follower")
public class FollowerController {
    private final FollowerService followerService;
    private final FollowingService followingService;



    @PostMapping
    public ResponseEntity<Void> addFollower(@RequestBody NewFollowerRequest request){

        NewFollowingRequest newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.setUserId(request.getUserId());
        newFollowingRequest.setFollowingId(request.getFollowerId());
        followingService.addFollowing(newFollowingRequest);

        NewFollowerRequest newFollowerRequest = new NewFollowerRequest();
        newFollowerRequest.setUserId(request.getFollowerId());
        newFollowerRequest.setFollowerId(request.getUserId());
        followerService.addFollower(newFollowerRequest);

        followingService.updateFollowingCount(request.getUserId());
        followerService.updateFollowerCount(request.getFollowerId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findFollowers(@PathVariable UUID userId){
        return ResponseEntity.ok(followerService.findFollowersByUserId(userId));
    }
}
