package com.xhxy.service.impl;

import com.xhxy.Mapper.StuCourseMapper;
import com.xhxy.Mapper.StudentMapper;
import com.xhxy.Mapper.TeacherMapper;
import com.xhxy.Mapper.UserMapper;
import com.xhxy.entity.Student;
import com.xhxy.entity.Teacher;
import com.xhxy.entity.User;
import com.xhxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StuCourseMapper stuCourseMapper;

    @Override
    public void register(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User login(User user) {
        User user1 = userMapper.findUserByCredentials(user.getNumber(), user.getUsername(), user.getPassword());
        return user1;
    }

    @Override
    public Integer selectCourse(Long studentId, Long courseId) {
        return stuCourseMapper.insertStuCourse(studentId,courseId);
    }

    @Override
    public void registerTeacher(Teacher teacher) {
        userMapper.insertUser(teacher.getUser());
//        teacher.setUserId(teacher.getUser().getId());
        teacherMapper.insertTeacher(teacher);

    }

    @Override
    public void registerStu(Student student) {
        userMapper.insertUser(student.getUser());
//        student.setUserId(student.getUser().getId());
        studentMapper.insertStudent(student);
    }

    @Override
    public Student selectStudent(Long userId) {
        return studentMapper.getByUserId(userId);
    }

    @Override
    public Teacher selectTeacher(Long userId) {
        return teacherMapper.getByUserId(userId);
    }
}
