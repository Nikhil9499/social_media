package com.nikhil.social_media.models;


import com.nikhil.social_media.models.enums.ReactType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@SuperBuilder
@Getter
public abstract class Entity {
    private String id;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String addedBy;
    @Setter
    private long commentsCount;
    private Map<ReactType, Set<String>> reactTypeVsUserIdsMap; // use this for count
}
