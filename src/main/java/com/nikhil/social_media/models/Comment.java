package com.nikhil.social_media.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Comment extends Entity {
    @Setter
    private String parentId;
    private String postId;

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + getId() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", user='" + getAddedBy() + '\'' +
                ", parentId='" + getParentId() + '\'' +
                ", postId='" + getPostId() + '\'' +
                ", commentsCount=" + getCommentsCount() +
                ", reactTypeVsUserIdsMap=" + getReactTypeVsUserIdsMap() +
                '}';
    }
}
