package com.home.MyWebQuizEngine.controller.model;

import java.util.Arrays;

public class AnswersJson {

    private int[] answers;
    public AnswersJson(){}
    public AnswersJson(int[] answers) {
        this.answers = answers;
    }

    public int[] getAnswersArray() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswersJson)) return false;
        AnswersJson that = (AnswersJson) o;
        return Arrays.equals(getAnswersArray(), that.getAnswersArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getAnswersArray());
    }
}
