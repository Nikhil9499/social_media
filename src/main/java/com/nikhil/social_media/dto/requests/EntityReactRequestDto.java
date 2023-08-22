package com.nikhil.social_media.dto.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EntityReactRequestDto {
    private String userId;
    private String reactType;
    private String entityId;
}
