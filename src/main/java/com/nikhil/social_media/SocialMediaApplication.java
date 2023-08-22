package com.nikhil.social_media;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.CreatePostRequestDto;
import com.nikhil.social_media.dto.requests.CreateUserRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.models.Comment;
import com.nikhil.social_media.models.Post;
import com.nikhil.social_media.models.User;
import com.nikhil.social_media.service.CommentService;
import com.nikhil.social_media.service.EntityReactsService;
import com.nikhil.social_media.service.PostService;
import com.nikhil.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SocialMediaApplication {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final EntityReactsService entityReactsService;

    @Autowired
    public SocialMediaApplication(UserService userService, PostService postService, CommentService commentService, EntityReactsService entityReactsService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.entityReactsService = entityReactsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApplication.class, args);
    }

    @Bean
    public void sampleTestCase() throws Exception {
        System.out.println("\nGoing to create a user");
        User user = userService.createUser(CreateUserRequestDto.builder().userName("Nick").email("nick@gmail.com").build());

        System.out.println("User created is " + user);

        List<User> userList = userService.bulkCreateUser();

        String userId = user.getUserId();
        System.out.println("\nGoing to create a post by userId " + userId);
        EntityResponseDto post = postService.create(CreatePostRequestDto.builder().userId(user.getUserId()).content("Hello").build());
        System.out.println("Post created is " + postService.getPostDetails(post.getId()));

        System.out.println("\nGoing to like on created post by userId " + userId);
        postService.react(EntityReactRequestDto.builder().userId(user.getUserId()).entityId(post.getId()).reactType("like").build());
        System.out.println("Post after like is " + postService.getPostDetails(post.getId()));

        System.out.println("\nGoing to put 5 comments on created post by 5 diff users");

        EntityResponseDto parentComment = commentOnPost(userList, post.getId());

        System.out.println("Post after comments & reacts is " + postService.getPostDetails(post.getId()));
        System.out.println("\nN first level comments are " + postService.getFirstNLevelComments(post.getId(), 0,10,5));

        System.out.println("\nUsers who liked the created post are: " + postService.getUsersWrtReactType(post.getId(), "like"));
        System.out.println("\nUsers who disliked the created post are: " + postService.getUsersWrtReactType(post.getId(), "dislike"));

        System.out.println("\nGoing to reply on comment with id " + parentComment.getId());
        EntityResponseDto childComment = replyOnComment(userList, parentComment.getId());
        System.out.println("\nComment after replies & reacts is " + commentService.getCommentDetails(parentComment.getId()));

        System.out.println("\nNext level comment replies are " + commentService.getCommentNextLevelReplies(parentComment.getId(), 0,10));
        System.out.println("\nOne of the child comment is " + commentService.getCommentDetails(childComment.getId()));
        System.out.println("\nUsers who liked the comment in which users are replying are: " + commentService.getUsersWrtReactType(parentComment.getId(), "like"));
        System.out.println("\nUsers who disliked the comment in which users are replying are: " + commentService.getUsersWrtReactType(parentComment.getId(), "dislike"));

        System.out.println();
    }

    private EntityResponseDto commentOnPost(List<User> userList, String postId) throws Exception {
        EntityResponseDto parentComment = null;
        for (int i = 0; i < userList.size(); i++) {
            parentComment = postService.commentOnPost(CommentRequestDto.builder()
                    .userId(userList.get(i).getUserId()).postId(postId).comment("Hey from " + userList.get(i).getUserId())
                    .build());
            if (i%2 == 0) {
                postService.react(EntityReactRequestDto.builder().userId(userList.get(i).getUserId()).entityId(postId).reactType("like").build());
            } else {
                postService.react(EntityReactRequestDto.builder().userId(userList.get(i).getUserId()).entityId(postId).reactType("dislike").build());
            }
        }
        return parentComment;
    }

    private EntityResponseDto replyOnComment(List<User> userList, String commentId) throws Exception {
        EntityResponseDto childComment = null;
        for (int i = 0; i < userList.size(); i++) {
            childComment = commentService.commentOnComment(CommentRequestDto.builder()
                    .userId(userList.get(i).getUserId()).parentCommentId(commentId).comment("Reply from " + userList.get(i).getUserId())
                    .build());
            if (i%2 == 0) {
                commentService.react(EntityReactRequestDto.builder().userId(userList.get(i).getUserId()).entityId(commentId).reactType("like").build());
            } else {
                commentService.react(EntityReactRequestDto.builder().userId(userList.get(i).getUserId()).entityId(commentId).reactType("dislike").build());
            }
        }
        return childComment;
    }

}
