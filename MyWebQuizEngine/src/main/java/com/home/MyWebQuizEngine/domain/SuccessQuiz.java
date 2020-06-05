package com.home.MyWebQuizEngine.domain;

import javax.persistence.*;

@Entity
@Table(name = "SUCCESS_QUIZ")
public class SuccessQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sqid;

    private long id;

    private String completedAt;

    public SuccessQuiz() {}

    public SuccessQuiz(long quizid, String completedAt){
        this.id = quizid;
        this.completedAt = completedAt;
    }

    public long getId() {
        return id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setId(long quizid) {
        this.id = quizid;
    }

    public void setCompletedAt(String time) {
        this.completedAt = time;
    }
}
