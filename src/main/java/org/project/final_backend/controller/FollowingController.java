package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.domain.following.NewFollowingRequest;
import org.project.final_backend.service.FollowerService;
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
    private final FollowerService followerService;

    @PostMapping
    public ResponseEntity<Void> addFollowing(@RequestBody NewFollowingRequest request){

        NewFollowerRequest newFollowerRequest = new NewFollowerRequest();
        newFollowerRequest.setUserId(request.getUserId());
        newFollowerRequest.setFollowerId(request.getFollowingId());
        followerService.addFollower(newFollowerRequest);

        NewFollowingRequest newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.setUserId(request.getFollowingId());
        newFollowingRequest.setFollowingId(request.getUserId());
        followingService.addFollowing(newFollowingRequest);

        followingService.updateFollowingCount(request.getUserId());
        followerService.updateFollowerCount(request.getFollowingId());


//        NewFollowerRequest newFollowerRequest = new NewFollowerRequest();
//        followingService.addFollowing(request);
//        followingService.updateFollowingCount(request.getUserId());
        return ResponseEntity.noContent().build();
    }
}
