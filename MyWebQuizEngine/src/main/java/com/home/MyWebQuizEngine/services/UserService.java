package com.home.MyWebQuizEngine.services;

import com.home.MyWebQuizEngine.domain.User;

public interface UserService {
    User findUserByUsername(String username);
    User saveUser(User user);
}
