package com.home.MyWebQuizEngine.domain;

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
}
