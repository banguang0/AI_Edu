package com.xhxy.controller;

import com.xhxy.entity.Student;
import com.xhxy.entity.Teacher;
import com.xhxy.entity.User;
import com.xhxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }
    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        try {
            userService.registerStu(student);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }
    @PostMapping("/register/teacher")
    public ResponseEntity<?> registerTeacher(@RequestBody Teacher teacher) {
        try {
            userService.registerTeacher(teacher);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User user1 = userService.login(user);

            if(user1.getRole().equals("student")){
                Student student = userService.selectStudent(user1.getId());
                student.setUser(user1);
                return ResponseEntity.ok(student);
            }else if (user1.getRole().equals("teacher")){
                Teacher teacher = userService.selectTeacher(user1.getId());
                teacher.setUser(user1);
                return ResponseEntity.ok(teacher);
            }else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/stu_course/{studentId}/{courseId}")
    public ResponseEntity<?> selectCourse(@PathVariable Long studentId,@PathVariable Long courseId) {
            Integer i = userService.selectCourse(studentId,courseId);
            return ResponseEntity.ok(i==0 ? "Selection failed":"User selected successfully");
    }

}
