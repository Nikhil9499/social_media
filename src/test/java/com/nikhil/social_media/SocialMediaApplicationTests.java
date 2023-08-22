package com.nikhil.social_media;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.CreatePostRequestDto;
import com.nikhil.social_media.dto.requests.CreateUserRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.models.Comment;
import com.nikhil.social_media.models.Post;
import com.nikhil.social_media.models.User;
import com.nikhil.social_media.service.CommentService;
import com.nikhil.social_media.service.PostService;
import com.nikhil.social_media.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocialMediaApplicationTests {

    @Test
    void contextLoads() {
    }

    @Mock
    private CommentService commentService;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Test
    public void runSampleFlow() {
//        Mockito.when(userService.createUser(Mockito.any())).thenReturn(User.builder().userName("Nick").userId("nick@gmail.com").emailId("nick@gmail.com").build());
//        User user = userService.createUser(CreateUserRequestDto.builder().build());
//        Post post = postService.create(CreatePostRequestDto.builder().userId(user.getUserId()).content("Hello").build());
//        postService.react(EntityReactRequestDto.builder().userId(user.getUserId()).entityId(post.getId()).reactType("like").build());
//        Comment parentComment = postService.commentOnPost(CommentRequestDto.builder()
//                .userId(user.getUserId()).postId(post.getId()).comment("first comment")
//                .build());
//
//        System.out.println(postService.getPost(post.getId()));
//
////        postService.react(user.getUserId(), post.getId(), "dislike");
//
//        Comment childComment = commentService.commentOnComment(CommentRequestDto.builder()
//                .userId(user.getUserId()).parentCommentId(parentComment.getId()).comment("first comment")
//                .build());
//        commentService.react(EntityReactRequestDto.builder().userId(user.getUserId()).entityId(parentComment.getId()).reactType("like").build());
//        commentService.react(EntityReactRequestDto.builder().userId(user.getUserId()).entityId(childComment.getId()).reactType("dislike").build());
//
//        System.out.println(commentService.getComment(parentComment.getId()));
//        System.out.println(commentService.getComment(childComment.getId()));
    }

}
