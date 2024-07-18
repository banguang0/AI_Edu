package com.xhxy.model;

public class ChatRequest {
    private String userId;
    private String question;
    private String command; // 添加这个字段

    // 生成getter和setter方法
    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
