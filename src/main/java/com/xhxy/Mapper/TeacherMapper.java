package com.xhxy.Mapper;

import com.xhxy.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeacherMapper {
    void insertTeacher(Teacher teacher);

    Teacher getByUserId(Long userId);

    Teacher getByTeacherId(Long teacherId);

}
