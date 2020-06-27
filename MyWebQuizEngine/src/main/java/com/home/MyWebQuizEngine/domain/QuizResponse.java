package com.home.MyWebQuizEngine.domain;

public class QuizResponse {
    private String feedback;
    private boolean success;
    public QuizResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback =feedback;

    }
    public String getFeedback() {
        return feedback;
    }
    public boolean getSuccess() {
        return success;
    }
}
