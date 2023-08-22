package com.nikhil.social_media.controller;


import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.CreatePostRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.dto.responses.ResponseDto;
import com.nikhil.social_media.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseDto<EntityResponseDto> createPost(@RequestBody CreatePostRequestDto createPostRequestDto) {
        return new ResponseDto<>(postService.create(createPostRequestDto));
    }

    @PostMapping("/{postId}/comment")
    public ResponseDto<EntityResponseDto> commentOnPost(@RequestBody CommentRequestDto commentRequestDto, @PathVariable String postId) {
        commentRequestDto.setPostId(postId);
        return new ResponseDto<>(postService.commentOnPost(commentRequestDto));
    }

    @PutMapping("/{postId}/react")
    public ResponseDto<String> reactOnPost(@PathVariable String postId, @RequestBody EntityReactRequestDto entityReactRequestDto) {
        entityReactRequestDto.setEntityId(postId);
        postService.react(entityReactRequestDto);
        return new ResponseDto<>("Reacted on post successfully");
    }

    @GetMapping("/{postId}")
    public ResponseDto<EntityResponseDto> getPost(@PathVariable String postId) {
        return new ResponseDto<>(postService.getPostDetails(postId));
    }

    @GetMapping("/{postId}/comments")
    public ResponseDto<List<EntityResponseDto>> getFirstNLevelComments(@PathVariable String postId,
                                                                       @RequestParam(value = "pageSize", defaultValue = "10", required = false) int size,
                                                                       @RequestParam(value = "pageNo", defaultValue = "0", required = false) int page,
                                                                       @RequestParam(value = "N", defaultValue = "100", required = false) int N) {
        return new ResponseDto<>(postService.getFirstNLevelComments(postId, page, size, N));
    }

    @GetMapping("/{postId}/react/{reactType}/users")
    public ResponseDto<Set<String>> getUsersWrtReactType(@PathVariable String postId, @PathVariable String reactType) {
        return new ResponseDto<>(postService.getUsersWrtReactType(postId, reactType));
    }
}
