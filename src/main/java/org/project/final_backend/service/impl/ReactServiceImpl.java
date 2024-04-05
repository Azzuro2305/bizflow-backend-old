package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.react.NewReactRequest;
import org.project.final_backend.domain.response.react.NewReactResponse;
import org.project.final_backend.entity.Post;
import org.project.final_backend.entity.React;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserFoundException;
import org.project.final_backend.repo.PostRepo;
import org.project.final_backend.repo.ReactRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.ReactService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReactServiceImpl implements ReactService {
    private ReactRepo reactRepo;
    private UserRepo userRepo;
    private PostRepo postRepo;
    private ModelMapper modelMapper;

    @Override
    public React findReactById(UUID id) {
        final React react = reactRepo.findReactById(id)
                .orElseThrow(()-> new UserFoundException("react not found"));
        return react;
    }

    @Override
    public NewReactResponse createReact(NewReactRequest request) {
        UUID userId = request.getUserId();
        UUID postId = request.getPostId();

        Users user = userRepo.findUsersById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepo.findPostById(postId).orElseThrow(() -> new RuntimeException("User not found"));

        if (reactRepo.findReactByPostAndUser(postId, userId).isPresent()) {
            throw new RuntimeException("You've already reacted");
        } else {
            React react = React.builder()
                    .postId(post)
                    .userId(user)
                    .build();
            post.setReact(post.getReact() + 1);
            postRepo.save(post);

            return modelMapper.map(reactRepo.save(react), NewReactResponse.class);
        }
    }

    @Override
    public void deleteReact(UUID id) {
        React react = reactRepo.findReactById(id).orElseThrow(()-> new UserFoundException("react not found"));
        Post post = react.getPostId();
        post.setReact(post.getReact() - 1);
        reactRepo.delete(react);
    }
}
