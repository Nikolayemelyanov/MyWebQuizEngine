package com.home.MyWebQuizEngine.domain;

import java.util.Arrays;

public class InitialAnswer {
    private int[] answer;
    public InitialAnswer(){}
    public InitialAnswer(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InitialAnswer)) return false;
        InitialAnswer that = (InitialAnswer) o;
        return Arrays.equals(getAnswer(), that.getAnswer());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getAnswer());
    }
}
