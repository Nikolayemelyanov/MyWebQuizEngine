package com.home.MyWebQuizEngine.services;

import com.home.MyWebQuizEngine.domain.User;

public interface UserService {
    User findUserByEmail(String email);
    User saveUser(User user);
}
