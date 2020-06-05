package com.home.MyWebQuizEngine.domain;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "EMAIL")
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String email;

    @Column(name = "PASSWORD")
    @Length(min = 5)
    @NotEmpty()
    private String password;

    @OneToMany(targetEntity = Quiz.class)
    @JoinColumn(name="UserId")
    private List<Quiz> quizList;

    @OneToMany(targetEntity = SuccessQuiz.class)
    @JoinColumn(name="UserId")
    private List<SuccessQuiz> successQuizList;

    private String role;

    public User(){}

    public User(String email, String password, List<Quiz> quizList, List<SuccessQuiz> successQuizList ) {
        this.email = email;
        this.password = password;
        this.quizList = quizList;
        this.successQuizList =successQuizList;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public List<SuccessQuiz> getSuccessQuizList() {
        return successQuizList;
    }

    public void setSuccessQuizList(List<SuccessQuiz> successQuizList) {
        this.successQuizList = successQuizList;
    }
}