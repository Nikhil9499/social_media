package com.nikhil.social_media.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nikhil.social_media.models.Comment;
import com.nikhil.social_media.models.Post;
import com.nikhil.social_media.models.enums.ReactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityResponseDto {
    private String id;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String userId;
    private long commentsCount;
    private long likesCount;
    private long dislikesCount;
    private String postId;

    public static EntityResponseDto getEntityResponseDtoFromPost(Post post) {
        return EntityResponseDto.builder()
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .description(post.getDescription())
                .userId(post.getAddedBy())
                .commentsCount(post.getCommentsCount())
                .likesCount(post.getReactTypeVsUserIdsMap().getOrDefault(ReactType.LIKE, new HashSet<>()).size())
                .dislikesCount(post.getReactTypeVsUserIdsMap().getOrDefault(ReactType.DISLIKE, new HashSet<>()).size())
                .build();
    }

    public static EntityResponseDto getEntityResponseDtoFromComment(Comment comment) {
        return EntityResponseDto.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .description(comment.getDescription())
                .userId(comment.getAddedBy())
                .commentsCount(comment.getCommentsCount())
                .likesCount(comment.getReactTypeVsUserIdsMap().getOrDefault(ReactType.LIKE, new HashSet<>()).size())
                .dislikesCount(comment.getReactTypeVsUserIdsMap().getOrDefault(ReactType.DISLIKE, new HashSet<>()).size())
                .build();
    }
}
