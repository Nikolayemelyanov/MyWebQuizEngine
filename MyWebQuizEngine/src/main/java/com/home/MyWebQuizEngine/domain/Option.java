package com.home.MyWebQuizEngine.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionid")
    private long id;

    @Column(name="options")
    private String option;

    @ManyToOne(targetEntity = Quiz.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private boolean isCorrect;

    public Option(){}
    public Option(String option,
                  Quiz quiz,
                  boolean isCorrect){
        this.option = option;
        this.quiz = quiz;
        this.isCorrect = isCorrect;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean getCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        Option option1 = (Option) o;
        return getId() == option1.getId() &&
                Objects.equals(option, option1.option) &&
                Objects.equals(getQuiz(), option1.getQuiz());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), option, getQuiz());
    }
}

