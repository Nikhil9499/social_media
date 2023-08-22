package com.nikhil.social_media.service;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.exceptions.custom.CommentNotFoundException;
import com.nikhil.social_media.exceptions.custom.EntityNotFoundException;
import com.nikhil.social_media.exceptions.custom.InvalidReactTypeException;
import com.nikhil.social_media.exceptions.custom.UserNotFoundException;
import com.nikhil.social_media.models.Comment;

import java.util.List;
import java.util.Set;

public interface CommentService {
    Comment createComment(String userId, String content, String postId) throws UserNotFoundException;

    boolean react(EntityReactRequestDto entityReactRequestDto) throws UserNotFoundException, EntityNotFoundException, InvalidReactTypeException;

    EntityResponseDto commentOnComment(CommentRequestDto commentRequestDto) throws UserNotFoundException, EntityNotFoundException;

    EntityResponseDto getCommentDetails(String commentId);

    Comment getComment(String commentId);

    List<EntityResponseDto> getCommentNextLevelReplies(String commentId, int pageNo, int pageSize) throws CommentNotFoundException;

    List<Comment> getAllCommentsWrtPostId(String postId, int pageNo, int pageSize) throws CommentNotFoundException;

    Set<String> getUsersWrtReactType(String commentId, String reactTypeString) throws CommentNotFoundException, InvalidReactTypeException;

}
