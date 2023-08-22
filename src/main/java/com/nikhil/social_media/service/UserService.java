package com.nikhil.social_media.service;

import com.nikhil.social_media.dto.requests.CreateUserRequestDto;
import com.nikhil.social_media.exceptions.custom.UserAlreadyPresentException;
import com.nikhil.social_media.exceptions.custom.UserNotFoundException;
import com.nikhil.social_media.models.User;

import java.util.List;

public interface UserService {
    User createUser(CreateUserRequestDto createUserRequestDto) throws UserAlreadyPresentException;

    boolean deleteUser(String userId) throws UserNotFoundException;

    void validateUser(String userId) throws UserNotFoundException;

    List<User> bulkCreateUser();
}
