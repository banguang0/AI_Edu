package com.xhxy.entity;

import java.util.List;

public class MultipleChoiceExercise extends Exercise {
    private List<ExerciseOption> options;

    public MultipleChoiceExercise() {
    }

    // 构造函数、getter和setter

    public List<ExerciseOption> getOptions() {
        return options;
    }

    public void setOptions(List<ExerciseOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "MultipleChoiceExercise{" +
                "options=" + options +
                '}';
    }
}