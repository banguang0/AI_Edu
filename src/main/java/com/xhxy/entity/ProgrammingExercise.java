package com.xhxy.entity;

public class ProgrammingExercise extends Exercise {
    private String answer;  // 参考答案或解题思路

    // 构造函数、getter和setter

    public ProgrammingExercise() {
    }

    public ProgrammingExercise(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ProgrammingExercise{" +
                "answer='" + answer + '\'' +
                '}';
    }
}