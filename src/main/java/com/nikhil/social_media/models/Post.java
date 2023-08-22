package com.nikhil.social_media.models;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Post extends Entity {
    @Override
    public String toString() {
        return "Post{" +
                "id='" + getId() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", user='" + getAddedBy() + '\'' +
                ", commentsCount=" + getCommentsCount() +
                ", reactTypeVsUserIdsMap=" + getReactTypeVsUserIdsMap() +
                '}';
    }
}
