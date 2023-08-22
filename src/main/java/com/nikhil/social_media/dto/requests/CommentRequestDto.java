package com.nikhil.social_media.dto.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CommentRequestDto {
    private String userId;
    private String postId;
    private String comment;
    private String parentCommentId;
}
