package com.nikhil.social_media.dto.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreatePostRequestDto {
    private String userId;
    private String content;
}
