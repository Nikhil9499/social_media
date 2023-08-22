package com.nikhil.social_media.service.impl;

import com.nikhil.social_media.dto.requests.CommentRequestDto;
import com.nikhil.social_media.dto.requests.EntityReactRequestDto;
import com.nikhil.social_media.dto.responses.EntityResponseDto;
import com.nikhil.social_media.exceptions.custom.CommentNotFoundException;
import com.nikhil.social_media.exceptions.custom.EntityNotFoundException;
import com.nikhil.social_media.exceptions.custom.InvalidReactTypeException;
import com.nikhil.social_media.exceptions.custom.UserNotFoundException;
import com.nikhil.social_media.models.Comment;
import com.nikhil.social_media.models.Entity;
import com.nikhil.social_media.models.enums.ReactType;
import com.nikhil.social_media.service.CommentService;
import com.nikhil.social_media.service.EntityReactsService;
import com.nikhil.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final Map<String, Comment> commentMap;
    private final Map<String, Set<Comment>> commentCommentsMap;
    private final Map<String, Set<Comment>> postCommentMap;
    private final UserService userService;
    private final EntityReactsService entityReactsService;

    @Autowired
    public CommentServiceImpl(UserService userService, EntityReactsService entityReactsService) {
        this.postCommentMap = new HashMap<>();
        this.commentCommentsMap = new HashMap<>();
        this.commentMap = new HashMap<>();
        this.userService = userService;
        this.entityReactsService = entityReactsService;
    }

    @Override
    public Comment createComment(String userId, String content, String postId) throws UserNotFoundException {
        userService.validateUser(userId);
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .description(content)
                .addedBy(userId)
                .commentsCount(0)
                .postId(postId)
                .reactTypeVsUserIdsMap(new HashMap<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        commentMap.put(comment.getId(), comment);

        Set<Comment> postComments = postCommentMap.getOrDefault(postId, new TreeSet<>(Comparator.comparing(Entity::getCreatedAt, Collections.reverseOrder())));
        postComments.add(comment);
        postCommentMap.put(postId, postComments);

        return comment;
    }

    @Override
    public boolean react(EntityReactRequestDto entityReactRequestDto) throws UserNotFoundException, EntityNotFoundException {
        String userId = entityReactRequestDto.getUserId();
        String reactTypeString = entityReactRequestDto.getReactType();
        String commentId = entityReactRequestDto.getEntityId();

        userService.validateUser(userId);
        ReactType.validateReactType(reactTypeString);
        validateComment(commentId);

        ReactType reactType = ReactType.valueOf(reactTypeString.toUpperCase());

        entityReactsService.react(userId, commentId, reactType);

        // If same user reacts again
        Map<ReactType, Set<String>> reactTypeVsUserIdsMap = getComment(commentId).getReactTypeVsUserIdsMap();
        for (ReactType type : reactTypeVsUserIdsMap.keySet()) {
            if (type.equals(reactType)) {
                continue;
            }
            Set<String> userSet = reactTypeVsUserIdsMap.get(type);
            if (!CollectionUtils.isEmpty(userSet)) {
                userSet.remove(userId);
            }
        }

        Set<String> userIdsMapOrDefault = reactTypeVsUserIdsMap.getOrDefault(reactType, new HashSet<>());
        userIdsMapOrDefault.add(userId);
        reactTypeVsUserIdsMap.put(reactType, userIdsMapOrDefault);
        return true;
    }

    @Override
    public EntityResponseDto commentOnComment(CommentRequestDto commentRequestDto) throws UserNotFoundException, EntityNotFoundException {
        String userId = commentRequestDto.getUserId();
        String parentCommentId = commentRequestDto.getParentCommentId();
        String content = commentRequestDto.getComment();

        userService.validateUser(userId);
        validateComment(parentCommentId);
        Comment parentComment = getComment(parentCommentId);

        Comment childComment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .description(content)
                .addedBy(userId)
                .commentsCount(0)
                .postId(parentComment.getPostId())
                .parentId(parentCommentId)
                .reactTypeVsUserIdsMap(new HashMap<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        commentMap.put(childComment.getId(), childComment);

        Set<Comment> parentComments = commentCommentsMap.getOrDefault(parentCommentId, new TreeSet<>(Comparator.comparing(Entity::getCreatedAt, Collections.reverseOrder())));
        parentComments.add(childComment);
        commentCommentsMap.put(parentCommentId, parentComments);

        parentComment.setCommentsCount(parentComment.getCommentsCount() + 1);

        return EntityResponseDto.getEntityResponseDtoFromComment(childComment);
    }

    private void validateComment(String commentId) {
        if (!commentMap.containsKey(commentId)) {
            throw new CommentNotFoundException(String.format("Comment with commentId %s is not present.", commentId));
        }
    }

    @Override
    public EntityResponseDto getCommentDetails(String commentId) {
        Comment comment = getComment(commentId);
        return EntityResponseDto.getEntityResponseDtoFromComment(comment);
    }

    public Comment getComment(String commentId) {
        validateComment(commentId);
        return commentMap.get(commentId);
    }

    @Override
    public List<EntityResponseDto> getCommentNextLevelReplies(String commentId, int pageNo, int pageSize) throws CommentNotFoundException {
        validateComment(commentId);
        Set<Comment> allReplies = commentCommentsMap.getOrDefault(commentId, new HashSet<>());

        int start = pageNo * pageSize;
        int end = Math.min(start + pageSize, allReplies.size());

        if (CollectionUtils.isEmpty(allReplies) || start > allReplies.size()) {
            return new ArrayList<>();
        }

        List<Comment> repliesList = new ArrayList<>(allReplies);

        return repliesList.subList(start, end).stream()
                .map(EntityResponseDto::getEntityResponseDtoFromComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> getAllCommentsWrtPostId(String postId, int pageNo, int pageSize) throws CommentNotFoundException {
        Set<Comment> allComments = postCommentMap.getOrDefault(postId, new TreeSet<>(Comparator.comparing(Entity::getCreatedAt, Collections.reverseOrder())));
        int start = pageNo * pageSize;
        int end = Math.min(start + pageSize, allComments.size());
        if (CollectionUtils.isEmpty(allComments) || start > allComments.size()) {
            return new ArrayList<>();
        }

        List<Comment> commentList = new ArrayList<>(allComments);

        return commentList.subList(start, end);
    }

    @Override
    public Set<String> getUsersWrtReactType(String commentId, String reactTypeString) throws CommentNotFoundException, InvalidReactTypeException {
        validateComment(commentId);
        ReactType.validateReactType(reactTypeString);

        ReactType reactType = ReactType.valueOf(reactTypeString.toUpperCase());

        return getComment(commentId).getReactTypeVsUserIdsMap().get(reactType) == null ? Set.of() : getComment(commentId).getReactTypeVsUserIdsMap().get(reactType);
    }
}
