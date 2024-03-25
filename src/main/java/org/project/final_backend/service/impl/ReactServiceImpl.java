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

        React react = React.builder()
                .postId(post)
                .userId(user)
                .build();
        return modelMapper.map(reactRepo.save(react), NewReactResponse.class);
    }

//    @Override
//    public NewCommentResponse createReact(UUID user_id, UUID post_id, NewReactRequest request) {
////        reactRepo.findReactById(user_id,post_id.equals(false))
////                .orElseThrow( ()-> new RuntimeException("You've already reacted"));
//           React react = React.builder()
//                    .postId(request.getPostId())
//                            .userId(request.getUserId())
//                   .build();
//
//        return ;
//    }


}

//    @Override
//    public void createReact(UUID user_id, UUID post_id) {
////        reactRepo.findReactByPostIdAndUserId(user_id,post_id.equals(false))
////                .orElseThrow( ()-> new RuntimeException("You've already reacted"));
////
//        boolean hasReacted=reactRepo.findReactByPostIdAndUserId(user_id,post_id) !=null;
////
//
//        if (!hasReacted){
//
//
//            React react = React.builder()
//                    .userId(Users.builder().id(user_id).build())
//                    .postId(Post.builder().id(post_id).build())
//                    .isLiked(true)
//                    .build();
//
//            react.setLiked(true);
//            reactRepo.save(react);
//          }
//        else {
//            throw new RuntimeException("You've already reacted");
//        }



//        return modelMapper.map(commentRepo.save(comment), NewCommentResponse.class);



