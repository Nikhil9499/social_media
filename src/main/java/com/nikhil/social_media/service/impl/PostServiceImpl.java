package com.nikhil.social_media.service.impl;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.CreatePostRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.exceptions.custom.EntityNotFoundException;
import com.nikhil.social_media.exceptions.custom.InvalidReactTypeException;
import com.nikhil.social_media.exceptions.custom.PostNotFoundException;
import com.nikhil.social_media.exceptions.custom.UserNotFoundException;
import com.nikhil.social_media.models.Comment;
import com.nikhil.social_media.models.Post;
import com.nikhil.social_media.models.enums.ReactType;
import com.nikhil.social_media.service.CommentService;
import com.nikhil.social_media.service.EntityReactsService;
import com.nikhil.social_media.service.PostService;
import com.nikhil.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final Map<String, Post> postMap;
    private final UserService userService;
    private final EntityReactsService entityReactsService;
    private final CommentService commentService;

    @Autowired
    public PostServiceImpl(UserService userService, EntityReactsService entityReactsService, CommentService commentService) {
        this.commentService = commentService;
        this.postMap = new HashMap<>();
        this.userService = userService;
        this.entityReactsService = entityReactsService;
    }

    @Override
    public EntityResponseDto create(CreatePostRequestDto createPostRequestDto) throws UserNotFoundException {
        String userId = createPostRequestDto.getUserId();
        String content = createPostRequestDto.getContent();

        userService.validateUser(userId);

        Post post = Post.builder()
                .id(UUID.randomUUID().toString())
                .description(content)
                .addedBy(userId)
                .commentsCount(0)
                .reactTypeVsUserIdsMap(new HashMap<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        postMap.put(post.getId(), post);
        return EntityResponseDto.getEntityResponseDtoFromPost(post);
    }

    @Override
    public boolean react(EntityReactRequestDto entityReactRequestDto) throws UserNotFoundException, EntityNotFoundException, InvalidReactTypeException {
        String userId = entityReactRequestDto.getUserId();
        String reactTypeString = entityReactRequestDto.getReactType();
        String postId = entityReactRequestDto.getEntityId();

        userService.validateUser(userId);
        validatePost(postId);
        ReactType.validateReactType(reactTypeString);
        ReactType reactType = ReactType.valueOf(reactTypeString.toUpperCase());

        entityReactsService.react(userId, postId, reactType);

        Map<ReactType, Set<String>> reactTypeVsUserIdsMap = getPost(postId).getReactTypeVsUserIdsMap();
        for (ReactType type : reactTypeVsUserIdsMap.keySet()) {
            if (type.equals(reactType)) {
                continue;
            }
            Set<String> userSet = reactTypeVsUserIdsMap.get(type);
            if (!ObjectUtils.isEmpty(userSet)) {
                userSet.remove(userId);
            }
        }

        Set<String> userIdsMapOrDefault = reactTypeVsUserIdsMap.getOrDefault(reactType, new HashSet<>());
        userIdsMapOrDefault.add(userId);
        reactTypeVsUserIdsMap.put(reactType, userIdsMapOrDefault);
        return true;
    }

    @Override
    public EntityResponseDto commentOnPost(CommentRequestDto commentRequestDto) throws UserNotFoundException, EntityNotFoundException {
        String userId = commentRequestDto.getUserId();
        String postId = commentRequestDto.getPostId();
        String content = commentRequestDto.getComment();

        userService.validateUser(userId);
        validatePost(postId);

        Post post = getPost(postId);

        Comment comment = commentService.createComment(userId, content, postId);
        comment.setParentId(postId);
        post.setCommentsCount(post.getCommentsCount() + 1);
        return EntityResponseDto.getEntityResponseDtoFromComment(comment);
    }

    private void validatePost(String postId) {
        if (!postMap.containsKey(postId)) {
            throw new PostNotFoundException(String.format("Post with postId %s is not present.", postId));
        }
    }

    public EntityResponseDto getPostDetails(String postId) {
        Post post = postMap.get(postId);
        return EntityResponseDto.getEntityResponseDtoFromPost(post);
    }

    public Post getPost(String postId) throws PostNotFoundException {
        validatePost(postId);
        return postMap.get(postId);
    }

    @Override
    public List<EntityResponseDto> getFirstNLevelComments(String postId, int pageNo, int pageSize, int N) throws PostNotFoundException {
        validatePost(postId);
        List<Comment> allCommentsWrtPostId = commentService.getAllCommentsWrtPostId(postId, pageNo, pageSize);
        if (allCommentsWrtPostId.size() == 0) {
            return new ArrayList<>();
        }
        int start = 0;
        int end = start + Math.min(N, allCommentsWrtPostId.size());
        return allCommentsWrtPostId.subList(start, end).stream()
                .map(EntityResponseDto::getEntityResponseDtoFromComment)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getUsersWrtReactType(String postId, String reactType) throws PostNotFoundException {
        validatePost(postId);
        ReactType.validateReactType(reactType);
        ReactType reactTypeEnum = ReactType.valueOf(reactType.toUpperCase());

        return getPost(postId).getReactTypeVsUserIdsMap().get(reactTypeEnum) == null ? Set.of() : getPost(postId).getReactTypeVsUserIdsMap().get(reactTypeEnum);
    }
}
