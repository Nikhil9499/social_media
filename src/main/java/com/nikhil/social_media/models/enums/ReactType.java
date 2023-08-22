package com.nikhil.social_media.models.enums;

import com.nikhil.social_media.exceptions.custom.InvalidReactTypeException;
import lombok.Getter;

@Getter
public enum ReactType {
    LIKE("like"), DISLIKE("dislike");

    final String val;

    ReactType(String val) {
        this.val = val;
    }

    public static void validateReactType(String reactType) {
        boolean isValid = false;
        for (ReactType value : ReactType.values()) {
            if (value.name().equals(reactType.toUpperCase())) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidReactTypeException(String.format("ReactType %s passed is invalid.", reactType));
        }
    }
}
