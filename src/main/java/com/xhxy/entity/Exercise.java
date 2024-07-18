package com.xhxy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceExercise.class, name = "选择题"),
        @JsonSubTypes.Type(value = ProgrammingExercise.class, name = "编程题")
})

public class Exercise {
    private Long id;
    private Section section;  // 关联Section对象
//    private String type;  // 选择题或编程题
    private String content;
    private String correctAnswer;  // 对于选择题
    private Integer difficultyLevel;
    private LocalDateTime  createAt;
    private LocalDateTime  updateAt;


    public Exercise() {
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", section=" + section +
                ", content='" + content + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @JsonBackReference
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
