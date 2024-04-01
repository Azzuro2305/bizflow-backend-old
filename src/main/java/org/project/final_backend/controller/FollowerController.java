package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.domain.response.follower.NewFollowerResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.service.FollowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/follower")
public class FollowerController {
    private final FollowerService followerService;

    @PostMapping
    public ResponseEntity<NewFollowerResponse> addFollower(@RequestBody NewFollowerRequest request){
        NewFollowerResponse response = followerService.addFollower(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{followingId}/hasFollowedBack/{followerId}")
    public ResponseEntity<Boolean> hasFollowedBack(@PathVariable UUID followingId, @PathVariable UUID followerId){
        boolean hasFollowedBack = followerService.hasFollowedBack(followingId, followerId);
        return ResponseEntity.ok(hasFollowedBack);
    }

    @GetMapping("/{userId}/followings")
    public ResponseEntity<List<UUID>> findFollowingsByUserId(@PathVariable UUID userId){
        List<UUID> followings = followerService.findFollowingsByUserId(userId);
        return new ResponseEntity<>(followings, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UUID>> findFollowersByUserId(@PathVariable UUID userId){
        List<UUID> followers = followerService.findFollowersByUserId(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followerCount")
    public ResponseEntity<Long> findFollowerCountByUserId(@PathVariable UUID userId){
        long count = followerService.findFollowerCountByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followingCount")
    public ResponseEntity<Long> findFollowingCountByUserId(@PathVariable UUID userId){
        long count = followerService.findFollowingCountByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/{followingId}/hasUnfollowed/{followerId}")
    public ResponseEntity<HttpResponse<String>> deleteFollower(@PathVariable UUID followingId, @PathVariable UUID followerId){
        followerService.deleteFollower(followingId, followerId);
        HttpResponse<String> response = new HttpResponse<>("Follower deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


//    @PostMapping
//    public ResponseEntity<Void> addFollower(@RequestBody NewFollowerRequest request){
//
//        NewFollowingRequest newFollowingRequest = new NewFollowingRequest();
//        newFollowingRequest.setUserId(request.getUserId());
//        newFollowingRequest.setFollowingId(request.getFollowerId());
//        followingService.addFollowing(newFollowingRequest);
//
//        NewFollowerRequest newFollowerRequest = new NewFollowerRequest();
//        newFollowerRequest.setUserId(request.getFollowerId());
//        newFollowerRequest.setFollowerId(request.getUserId());
//        followerService.addFollower(newFollowerRequest);
//
//        followingService.updateFollowingCount(request.getUserId());
//        followerService.updateFollowerCount(request.getFollowerId());
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<?> findFollowers(@PathVariable UUID userId){
//        return ResponseEntity.ok(followerService.findFollowersByUserId(userId));
//    }
//}
