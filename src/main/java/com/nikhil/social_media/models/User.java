package com.nikhil.social_media.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private String userId;
    private String userName;
    private String emailId;
    // other fields like password, etc
}
