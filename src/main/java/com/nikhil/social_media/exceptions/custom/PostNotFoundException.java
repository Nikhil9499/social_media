package com.nikhil.social_media.exceptions.custom;

import com.nikhil.social_media.exceptions.ParentSocialMediaException;

public class PostNotFoundException extends ParentSocialMediaException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
