package com.home.MyWebQuizEngine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class QuizJson {

    public QuizJson(){}


    private long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private  String text;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @Size(min=2, max=6)
    private String[] options;

    public Quiz convertToQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle(this.title);
        quiz.setText(this.text);
        quiz.setId(this.id);
        List<Option> optionsList = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            boolean isCorrect = false;
            for (int val: answer
                 ) {
                if (val == i) {
                    isCorrect = true;
                    break;
                }
            }
            Option option = new Option(options[i], quiz, isCorrect);
            optionsList.add(option);
        }
        quiz.setOptions(optionsList);
        return quiz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}

