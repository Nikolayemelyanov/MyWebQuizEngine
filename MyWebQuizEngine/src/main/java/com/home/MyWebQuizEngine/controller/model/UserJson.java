package com.home.MyWebQuizEngine.controller.model;

import com.home.MyWebQuizEngine.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserJson {
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String email;

    @Length(min = 5)
    @NotEmpty()
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public User convertToUser() {
        User user = new User();
        user.setPassword(this.password);
        user.setUsername(this.email);
        return user;
    }
}
