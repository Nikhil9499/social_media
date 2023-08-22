package com.nikhil.social_media.models;

import com.nikhil.social_media.models.enums.ReactType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
public class EntityReacts {
    private String entityId;
    private String userId;
    @Setter
    private ReactType reactType;
    private Date createdAt;
}
