package com.nikhil.social_media.service;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.CreatePostRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.exceptions.custom.EntityNotFoundException;
import com.nikhil.social_media.exceptions.custom.InvalidReactTypeException;
import com.nikhil.social_media.exceptions.custom.PostNotFoundException;
import com.nikhil.social_media.exceptions.custom.UserNotFoundException;
import com.nikhil.social_media.models.Post;

import java.util.List;
import java.util.Set;

public interface PostService {
    EntityResponseDto create(CreatePostRequestDto createPostRequestDto) throws UserNotFoundException;

    boolean react(EntityReactRequestDto entityReactRequestDto) throws UserNotFoundException, EntityNotFoundException, InvalidReactTypeException;

    EntityResponseDto commentOnPost(CommentRequestDto commentRequestDto) throws UserNotFoundException, EntityNotFoundException;

    EntityResponseDto getPostDetails(String postId);

    Post getPost(String postId) throws PostNotFoundException;

    List<EntityResponseDto> getFirstNLevelComments(String postId, int pageNo, int pageSize, int N) throws PostNotFoundException;

    Set<String> getUsersWrtReactType(String postId, String reactType) throws PostNotFoundException;

}
