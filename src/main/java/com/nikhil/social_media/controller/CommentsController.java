package com.nikhil.social_media.controller;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.dto.responses.ResponseDto;
import com.nikhil.social_media.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{parentCommentId}/comment")
    public ResponseDto<EntityResponseDto> replyOnComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable String parentCommentId) {
        commentRequestDto.setParentCommentId(parentCommentId);
        return new ResponseDto<>(commentService.commentOnComment(commentRequestDto));
    }

    @PutMapping("/{commentId}/react")
    public ResponseDto<String> reactOnPost(@PathVariable String commentId, @RequestBody EntityReactRequestDto entityReactRequestDto) {
        entityReactRequestDto.setEntityId(commentId);
        commentService.react(entityReactRequestDto);
        return new ResponseDto<>("Reacted on comment successfully");
    }

    @GetMapping("/{commentId}")
    public ResponseDto<EntityResponseDto> getComment(@PathVariable String commentId) {
        return new ResponseDto<>(commentService.getCommentDetails(commentId));
    }

    @GetMapping("/{commentId}/replies")
    public ResponseDto<List<EntityResponseDto>> getCommentNextLevelReplies(@PathVariable String commentId,
                                                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int size,
                                                                           @RequestParam(value = "pageNo", defaultValue = "0", required = false) int page) {
        return new ResponseDto<>(commentService.getCommentNextLevelReplies(commentId, page, size));
    }

    @GetMapping("/{commentId}/react/{reactType}/users")
    public ResponseDto<Set<String>> getUsersWrtReactType(@PathVariable String commentId, @PathVariable String reactType) {
        return new ResponseDto<>(commentService.getUsersWrtReactType(commentId, reactType));
    }
}
