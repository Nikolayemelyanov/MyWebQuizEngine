package com.home.MyWebQuizEngine.domain;
import com.home.MyWebQuizEngine.domain.Quiz;

import javax.persistence.*;

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

}

