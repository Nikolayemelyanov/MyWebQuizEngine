package com.home.MyWebQuizEngine.domain;
import com.home.MyWebQuizEngine.domain.Quiz;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionid")
    private long id;

    @Column(name="options")
    private String options;

    @ManyToOne(targetEntity = Quiz.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Options(){}
    public Options (String options, Quiz quiz){
        this.options = options;
        this.quiz = quiz;
    }

    public String getOption() {
        return options;
    }

    public void setOption(String option) {
        this.options = option;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Options)) return false;
        Options options1 = (Options) o;
        return getId() == options1.getId() &&
                Objects.equals(options, options1.options) &&
                Objects.equals(getQuiz(), options1.getQuiz());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), options, getQuiz());
    }
}

