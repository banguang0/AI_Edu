package com.xhxy.Mapper;

import com.xhxy.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {
    Course getCourseById(Long id);
    //查询所有课，不包含章节
    List<Course> getAllCourses();
    //根据学生id查询所有课程包括章节
    List<Course> getAllCoursesByStu(Long studentId);
    //根据老师id查询所有课程包括章节
    List<Course> getAllCoursesByTeacher(Long teacherId);

    //老师创建课程不包括章节
    Integer insertCourse(Course course);
    //老师更新课不包括章节
    Integer updateCourse(Course course);
    //老师删除课程包括删除章节
    Integer deleteCourse(Long id);

    List<Course> getAllCoursesByStuSimple(Long studentId);

    List<Course> getAllCoursesByTeacherSimple(Long teacherId);
}
