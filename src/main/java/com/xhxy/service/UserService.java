package com.xhxy.service;

import com.xhxy.entity.Student;
import com.xhxy.entity.Teacher;
import com.xhxy.entity.User;

public interface UserService {
    //用户注册 --> 学生/老师注册
    void register(User user);
    //登录根据号码，用户名，密码
    User login(User user);
    //学生选课
    Integer selectCourse(Long studentId, Long courseId);
    //老师注册
    void registerTeacher(Teacher teacher);
    //学生注册
    void registerStu(Student student);
    //根据userid查找学生
    Student selectStudent(Long userId);
    //根据userid查找老师
    Teacher selectTeacher(Long userId);
}
