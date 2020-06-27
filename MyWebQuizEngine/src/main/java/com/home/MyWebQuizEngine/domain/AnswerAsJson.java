package com.home.MyWebQuizEngine.domain;

import java.util.Arrays;

public class AnswerAsJson {
    private int[] answer;
    public AnswerAsJson(){}
    public AnswerAsJson(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswersArray() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswerAsJson)) return false;
        AnswerAsJson that = (AnswerAsJson) o;
        return Arrays.equals(getAnswersArray(), that.getAnswersArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getAnswersArray());
    }
}
