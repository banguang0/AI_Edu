package com.xhxy.service;

import com.xhxy.entity.Course;

import java.util.List;

public interface CourseService {
    //查询所有课程不包括章节
    List<Course> getAllCourses();
    //根据学生id查询所有课程详细信息
    List<Course> getAllCoursesByStu(Long studentId);
    //根据老师id查询所有课程详细信息
    List<Course> getAllCoursesByTeacher(Long teacherId);
    //老师创建课不包括章节
    Course createCourse(Long teacherId,Course course);
    //一次性创建课程以及所有章节
    Course createCourseByAI(Long teacherId,Course course);
    //老师修改课不包括章节
    Course updateCourse(Course course);
    //老师删除课以及其中的章节
    Integer deleteCourse(Long id);

    List<Course> getAllCoursesByTeacherSimple(Long teacherId);

    List<Course> getAllCoursesByStuSimple(Long studentId);
}
