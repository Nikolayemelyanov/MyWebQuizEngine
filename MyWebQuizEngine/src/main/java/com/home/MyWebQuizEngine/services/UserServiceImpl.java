package com.home.MyWebQuizEngine.services;

import com.home.MyWebQuizEngine.domain.User;
import com.home.MyWebQuizEngine.repositories.UserRepository;
import com.home.MyWebQuizEngine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository  = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setQuizList(new ArrayList<>());
        user.setSuccessQuizList(new ArrayList<>());
        user.setRole("ADMIN");
        return userRepository.save(user);
    }
}
