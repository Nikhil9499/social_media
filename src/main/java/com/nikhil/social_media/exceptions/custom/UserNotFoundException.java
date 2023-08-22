package com.nikhil.social_media.exceptions.custom;

import com.nikhil.social_media.exceptions.ParentSocialMediaException;

public class UserNotFoundException extends ParentSocialMediaException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
