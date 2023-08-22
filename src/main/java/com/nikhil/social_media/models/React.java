package com.nikhil.social_media.models;

import com.nikhil.social_media.models.enums.ReactType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class React {
    private String id;
    private ReactType reactType;
    private String userId;
}
