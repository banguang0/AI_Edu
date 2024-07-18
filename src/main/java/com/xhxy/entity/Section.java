package com.xhxy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class Section {
    private Long id;
    private Chapter chapter;  // 关联Chapter对象
    private String name;
    private Integer orderNumber;
    private List<FileEntity> videos;
    private List<FileEntity> ppts;
    private List<Exercise> exercises;  // 小节习题列表

    public Section() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
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

    public List<FileEntity> getVideos() {
        return videos;
    }

    public void setVideos(List<FileEntity> videos) {
        this.videos = videos;
    }

    public List<FileEntity> getPpts() {
        return ppts;
    }

    public void setPpts(List<FileEntity> ppts) {
        this.ppts = ppts;
    }
//    @JsonManagedReference
    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", chapter=" + chapter +
                ", name='" + name + '\'' +
                ", orderNumber=" + orderNumber +
                ", videos=" + videos +
                ", ppts=" + ppts +
                ", exercises=" + exercises +
                '}';
    }
}
