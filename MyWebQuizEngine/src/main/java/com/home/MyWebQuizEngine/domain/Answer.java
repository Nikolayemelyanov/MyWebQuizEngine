package com.home.MyWebQuizEngine.domain;



import com.home.MyWebQuizEngine.domain.Quiz;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerid")
    private long id;
    private Integer answers;

    @ManyToOne(targetEntity = Quiz.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    public Answer(){}
    public Answer(Integer answer, Quiz quiz) {
        this.answers = answer;
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Integer getAnswers() {
        return answers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setAnswers(Integer answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getId() == answer.getId() &&
                getAnswers().equals(answer.getAnswers()) &&
                getQuiz().equals(answer.getQuiz());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAnswers(), getQuiz());
    }
}
