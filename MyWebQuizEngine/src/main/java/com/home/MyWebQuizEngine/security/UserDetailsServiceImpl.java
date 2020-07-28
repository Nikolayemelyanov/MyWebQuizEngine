package com.home.MyWebQuizEngine.security;

import com.home.MyWebQuizEngine.domain.User;
import com.home.MyWebQuizEngine.repositories.UserRepository;
import com.home.MyWebQuizEngine.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setPassword(user.getPassword());
        userDetails.setUsername(user.getUsername());
        userDetails.setEnabled(true);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        userDetails.setAuthorities(authorities);
        return userDetails;
    }
}

