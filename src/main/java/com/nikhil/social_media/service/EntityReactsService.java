package com.nikhil.social_media.service;

import com.nikhil.social_media.models.EntityReacts;
import com.nikhil.social_media.models.enums.ReactType;

public interface EntityReactsService {
    void react(String userId, String entityId, ReactType reactType);

    EntityReacts getEntityReacts(String userId, String entityId);
}
