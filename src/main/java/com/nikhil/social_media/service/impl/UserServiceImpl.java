package com.nikhil.social_media.service.impl;

import com.nikhil.social_media.dto.requests.CreateUserRequestDto;
import com.nikhil.social_media.exceptions.custom.UserAlreadyPresentException;
import com.nikhil.social_media.exceptions.custom.UserNotFoundException;
import com.nikhil.social_media.models.User;
import com.nikhil.social_media.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public User createUser(CreateUserRequestDto createUserRequestDto) throws UserAlreadyPresentException {
        String userName = createUserRequestDto.getUserName();
        String email = createUserRequestDto.getEmail();

        if (userMap.containsKey(email)) {
            throw new UserAlreadyPresentException(String.format("User with email %s is already present.", email));
        }
        User user = User.builder()
                .userName(userName)
                .userId(email)
                .emailId(email)
                .build();
        userMap.put(email, user);
        return user;
    }

    @Override
    public boolean deleteUser(String userId) throws UserNotFoundException {
        if (!userMap.containsKey(userId)) {
            throw new UserNotFoundException(String.format("User with userId %s is not present.", userId));
        }
        userMap.remove(userId);
        // TODO: also delete all posts and comments added by user here
        return true;
    }

    @Override
    public void validateUser(String userId) throws UserNotFoundException {
        if (!userMap.containsKey(userId)) {
            throw new UserNotFoundException(String.format("User with userId %s is not present.", userId));
        }
    }

    @Override
    public List<User> bulkCreateUser() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            users.add(createUser(CreateUserRequestDto.builder()
                    .email("nick" + i + "@gmail.com")
                    .userName("nick" + i)
                    .build()));
        }
        return users;
    }
}
