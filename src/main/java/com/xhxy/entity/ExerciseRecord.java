package com.xhxy.entity;

public class ExerciseRecord {
    private Long id;
    private Exercise exercise;  // 关联Exercise对象
    private Student student;  // 关联Student对象
    private String answer;
    private Integer isCorrect;

    public ExerciseRecord() {
    }

    @Override
    public String toString() {
        return "ExerciseRecord{" +
                "id=" + id +
                ", exercise=" + exercise +
                ", student=" + student +
                ", answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
