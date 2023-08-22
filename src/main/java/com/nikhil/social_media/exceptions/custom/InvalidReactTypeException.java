package com.nikhil.social_media.exceptions.custom;

import com.nikhil.social_media.exceptions.ParentSocialMediaException;

public class InvalidReactTypeException extends ParentSocialMediaException {
    public InvalidReactTypeException(String message) {
        super(message);
    }
}
