package com.home.MyWebQuizEngine.controller;

import com.home.MyWebQuizEngine.services.UserService;
import com.home.MyWebQuizEngine.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RestController
public class UserController {
    public UserController() {
    }

    @Autowired
    UserService userService;

    @PostMapping(value = "/api/register", consumes = "application/json")
    public void createNewUser(@Valid @RequestBody User user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            userService.saveUser(user);
        }
    }
}

