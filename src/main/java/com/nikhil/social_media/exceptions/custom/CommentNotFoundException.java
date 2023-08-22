package com.nikhil.social_media.exceptions.custom;

import com.nikhil.social_media.exceptions.ParentSocialMediaException;

public class CommentNotFoundException extends ParentSocialMediaException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
