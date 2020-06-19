package com.home.MyWebQuizEngine.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answerList;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private  String text;

    @Size(min=2)
    @NotEmpty
    @NotNull
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> optionsList;
    public Quiz(){}
    public Quiz (String title, String text, String[] options, int[] answer) {
        List<Options> optionsList = new ArrayList<Options>();
        for (String el: options
        ) {
            optionsList.add(new Options(el, this));
        }this.optionsList = optionsList;
        this.text = text;
        this.title = title;
        List<Answer> answerList = new ArrayList<Answer>();
        for (Integer el: answer
        ) {
            answerList.add(new Answer(el, this));
        }

        this.answerList = answerList;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        String[] options  = new String[optionsList.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = optionsList.get(i).getOption();
        }
        return options;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int[] getAnswer() {
        int[] answer  = new int[answerList.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = answerList.get(i).getAnswers();
        }
        return answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAnswer(int[] answer) {
        List<Answer> answerList = new ArrayList<Answer>();
        for (Integer el: answer
        ) {
            answerList.add(new Answer(el, this));
        }
        this.answerList = answerList;
    }

    public void setOptions(String[] options) {
        List<Options> optionsList = new ArrayList<Options>();
        for (String el: options
        ) {
            optionsList.add(new Options(el, this));
        }

        this.optionsList = optionsList;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getId() == quiz.getId() &&
                answerList.equals(quiz.answerList) &&
                getTitle().equals(quiz.getTitle()) &&
                getText().equals(quiz.getText()) &&
                optionsList.equals(quiz.optionsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), answerList, getTitle(), getText(), optionsList);
    }
}

