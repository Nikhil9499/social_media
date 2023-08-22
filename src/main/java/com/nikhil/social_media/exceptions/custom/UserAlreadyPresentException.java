package com.nikhil.social_media.exceptions.custom;

import com.nikhil.social_media.exceptions.ParentSocialMediaException;

public class UserAlreadyPresentException extends ParentSocialMediaException {
    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
