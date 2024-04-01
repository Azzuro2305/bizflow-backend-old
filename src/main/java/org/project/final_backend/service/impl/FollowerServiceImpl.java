package org.project.final_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.project.final_backend.domain.follower.NewFollowerRequest;
import org.project.final_backend.domain.response.follower.NewFollowerResponse;
import org.project.final_backend.entity.Follower;
import org.project.final_backend.entity.Users;
import org.project.final_backend.repo.FollowerRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.FollowerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepo followerRepo;
    private final UserRepo userRepo;

    @Transactional
    @Override
    public NewFollowerResponse addFollower(NewFollowerRequest request) {
        UUID followingId = request.getFollowingId();
        UUID followerId = request.getFollowerId();

        Users following = userRepo.findById(followingId).orElseThrow(() -> new RuntimeException("User not found"));
        Users follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("User not found"));

        if (followingId.equals(followerId)) {
            throw new RuntimeException("A user cannot follow themselves");
        }

        Follower existingFollower = followerRepo.findByFollowingAndFollower(following, follower);
        if (existingFollower != null) {
            throw new RuntimeException("The user is already following");
        } else {
            Follower newFollower = Follower.builder()
                    .following(following)
                    .follower(follower)
                    .followedAt(LocalDateTime.now())
                    .build();

            followerRepo.save(newFollower);

            following.setFollowings(following.getFollowings() + 1);
            follower.setFollowers(follower.getFollowers() + 1);

//            following.setFollowings(followerRepo.countByFollower(following));
//            follower.setFollowers(followerRepo.countByFollowing(follower));
//            userRepo.save(following);
//            userRepo.save(follower);

            NewFollowerResponse response = new NewFollowerResponse();
            response.setFollowerId(followerId);
            response.setFollowingId(followingId);
            response.setFollowedAt(newFollower.getFollowedAt());

            return response;
        }
    }



    @Override
    public boolean hasFollowedBack(UUID followingId, UUID followerId) {
        Users following = userRepo.findById(followingId).orElseThrow(() -> new RuntimeException("User not found"));
        Users follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("User not found"));

        Follower followBack = followerRepo.findByFollowingAndFollower(following, follower);
        return followBack != null;
    }

    @Override
    public List<UUID> findFollowingsByUserId(UUID userId) {
        if (userRepo.existsById(userId))
        {
            return followerRepo.findByFollowing_Id(userId).stream()
                    .map(Follower::getFollower)
                    .map(Users::getId)
                    .collect(Collectors.toList());
        } else
        {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public List<UUID> findFollowersByUserId(UUID userId) {
        if (userRepo.existsById(userId))
        {
            return followerRepo.findByFollower_Id(userId).stream()
                    .map(Follower::getFollowing)
                    .map(Users::getId)
                    .collect(Collectors.toList());
        } else
        {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public long findFollowerCountByUserId(UUID userId) {
        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return followerRepo.countByFollowing(user);
    }

    @Override
    public long findFollowingCountByUserId(UUID userId) {
        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return followerRepo.countByFollower(user);
    }

    @Transactional
    @Override
    public void deleteFollower(UUID followingId, UUID followerId) {
        Users following = userRepo.findById(followingId).orElseThrow(() -> new RuntimeException("User not found"));
        Users follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("User not found"));

        Follower existingFollower = followerRepo.findByFollowingAndFollower(following, follower);
        if (existingFollower != null) {
            followerRepo.delete(existingFollower);
            following.setFollowings(following.getFollowings() - 1);
            follower.setFollowers(follower.getFollowers() - 1);
//            following.setFollowings(followerRepo.countByFollowing(following));
//            follower.setFollowers(followerRepo.countByFollower(follower));
            userRepo.save(following);
            userRepo.save(follower);
        } else {
            throw new RuntimeException("No such follower relationship exists");
        }
    }

//    @Override
//    public long countByUser_Id(UUID userId) {
//        return followerRepo.countByUser_Id(userId);
//    }
//
//    @Override
//    public List<UUID> findFollowersByUserId(UUID userId) {
//        return followerRepo.findByUser_Id(userId).stream()
//                .map(follower -> follower.getFollower().getId())
//                .toList();
//    }
//
//    @Override
//    public void addFollower(NewFollowerRequest request) {
//    UUID userId = request.getUserId();
//    UUID followerId = request.getFollowerId();
//
//    Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//    Users follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
//
//    if (userId.equals(followerId)) {
//        throw new RuntimeException("A user cannot follow themselves");
//    }
//
//    Follower existingFollower = followerRepo.findByUserAndFollower(user, follower);
//    if (existingFollower != null && !existingFollower.isDeleted()) {
//        throw new RuntimeException("The user is already following");
//
//    } else {
//        Follower newFollower = Follower.builder()
//                .user(user)
//                .follower(follower)
//                .followedAt(LocalDateTime.now())
//                .isDeleted(false)
//                .build();
//
//        followerRepo.save(newFollower);
//    }
//}

//    @Override
//    public void updateFollowerCount(UUID userId) {
//        Users user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        long followerCount = followerRepo.countByUser_Id(userId);
//        user.setFollowers(followerCount);
//        userRepo.save(user);
//    }
}
