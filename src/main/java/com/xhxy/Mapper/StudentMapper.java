package com.xhxy.Mapper;

import com.xhxy.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentMapper {
    void insertStudent(Student student);

    Student getByUserId(Long userId);

    Student getByStudentId(Long studentId);
}
