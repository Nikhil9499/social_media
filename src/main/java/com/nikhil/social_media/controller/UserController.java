package com.nikhil.social_media.controller;

import com.nikhil.social_media.dto.requests.CreateUserRequestDto;
import com.nikhil.social_media.dto.responses.ResponseDto;
import com.nikhil.social_media.models.User;
import com.nikhil.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseDto<User> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return new ResponseDto<>(userService.createUser(createUserRequestDto));
    }

    @PostMapping("/bulk-create")
    public ResponseDto<List<User>> bulkCreateUser() {
        return new ResponseDto<>(userService.bulkCreateUser());
    }
}
