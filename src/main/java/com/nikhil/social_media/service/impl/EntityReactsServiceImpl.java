package com.nikhil.social_media.service.impl;

import com.nikhil.social_media.models.EntityReacts;
import com.nikhil.social_media.models.enums.ReactType;
import com.nikhil.social_media.service.EntityReactsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EntityReactsServiceImpl implements EntityReactsService {

    private final Map<String, EntityReacts> entityReactsMap = new HashMap<>();

    @Override
    public void react(String userId, String entityId, ReactType reactType) {
        String key = getKey(userId, entityId);
        if (entityReactsMap.containsKey(key)) {
            entityReactsMap.get(key).setReactType(reactType);
        } else {
            EntityReacts entityReacts = EntityReacts.builder()
                    .userId(userId)
                    .entityId(entityId)
                    .reactType(reactType)
                    .createdAt(new Date())
                    .build();
            entityReactsMap.put(key, entityReacts);
        }
    }

    @Override
    public EntityReacts getEntityReacts(String userId, String entityId) {
        return entityReactsMap.get(getKey(userId, entityId));
    }

    private String getKey(String userId, String entityId) {
        String sb = userId +
                "_" +
                entityId;
        return sb;
    }
}
