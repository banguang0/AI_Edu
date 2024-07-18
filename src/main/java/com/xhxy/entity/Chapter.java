package com.xhxy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class Chapter {
    private Long id;
    private Course course;  // 关联Course对象
    private String name;
    private Integer orderNumber;
    private List<Section> sections;  // 章节小节列表

    public Chapter() {
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", course=" + course +
                ", name='" + name + '\'' +
                ", orderNumber=" + orderNumber +
                ", sections=" + sections +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JsonManagedReference
    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
