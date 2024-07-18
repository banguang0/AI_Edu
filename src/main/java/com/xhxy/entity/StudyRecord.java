package com.xhxy.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class StudyRecord {
    private Student student;  // 关联Student对象
    private Course course;  // 关联Section对象
    private Integer exerciseNumber;
    private Integer completionNumber;
    private Integer correctionNumber;
    private Integer integral;

    public StudyRecord() {
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getExerciseNumber() {
        return exerciseNumber;
    }

    public void setExerciseNumber(Integer exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }

    public Integer getCompletionNumber() {
        return completionNumber;
    }

    public void setCompletionNumber(Integer completionNumber) {
        this.completionNumber = completionNumber;
    }

    public Integer getCorrectionNumber() {
        return correctionNumber;
    }

    public void setCorrectionNumber(Integer correctionNumber) {
        this.correctionNumber = correctionNumber;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
