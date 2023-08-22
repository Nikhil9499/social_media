package com.nikhil.social_media.exceptions.custom;

import com.nikhil.social_media.exceptions.ParentSocialMediaException;

public class EntityNotFoundException extends ParentSocialMediaException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
