package com.xhxy.model;

public class ChatResponse {
    private String answer;
    private String end;

    public ChatResponse() {

    }

    public ChatResponse(String answer, String end) {
        this.answer = answer;
        this.end = end;
    }

    // Getters and setters
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
