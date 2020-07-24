package com.home.MyWebQuizEngine.domain;
import com.home.MyWebQuizEngine.controller.model.QuizJson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private String title;


    private  String text;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> optionsList;

    public Quiz(){}

    public Quiz (String title, String text, List<Option> optionsList, long id) {
        this.optionsList = optionsList;
        this.text = text;
        this.title = title;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public List<Option> getOptions() {

        return optionsList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOptions(List<Option> optionsList) {

        this.optionsList = optionsList;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuizJson convertToQuizJson() {
        QuizJson quizJson = new QuizJson();
        quizJson.setTitle(this.title);
        quizJson.setText(this.text);
        quizJson.setId(this.id);
        String[] options  = new String[optionsList.size()];
        List<Integer> answers = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            options[i] = optionsList.get(i).getOption();
            if (optionsList.get(i).getCorrect()) {
                answers.add(i);
            }
        }
        quizJson.setOptions(options);
        int[] answersArray =  new int[answers.size()];
        for (int i = 0; i < answers.size(); i++) {
            answersArray[i] = answers.get(i);
        }
        quizJson.setAnswer(answersArray);
        return quizJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getId() == quiz.getId() &&
                getTitle().equals(quiz.getTitle()) &&
                getText().equals(quiz.getText()) &&
                optionsList.equals(quiz.optionsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getText(), optionsList);
    }
}

