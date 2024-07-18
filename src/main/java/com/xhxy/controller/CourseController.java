package com.xhxy.controller;

import com.xhxy.entity.Course;
import com.xhxy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;
    //老师查询所有课(不包括章节)
    @GetMapping("simple/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getAllCoursesByTeacherSimple(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getAllCoursesByTeacherSimple(teacherId);
        return ResponseEntity.ok(courses);
    }

    //学生查询所有课(不包括章节)
    @GetMapping("simple/student/{studentId}")
    public ResponseEntity<List<Course>> getAllCoursesByStuSimple(@PathVariable Long studentId) {
        List<Course> courses = courseService.getAllCoursesByStuSimple(studentId);
        return ResponseEntity.ok(courses);
    }

    //查询所有课程不包括章节
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    //学生查询所有课程stu(包括章节)
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getAllCoursesByStu(@PathVariable Long studentId) {
        List<Course> courses = courseService.getAllCoursesByStu(studentId);
        return ResponseEntity.ok(courses);
    }

    //老师查询所有课程teach(包括章节)
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getAllCoursesByTeacher(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
        return ResponseEntity.ok(courses);
    }

    //添加课不包括章节
    @PostMapping("/createCourse/{teacherId}")
    public ResponseEntity<List<Course>> createCourse(
            @PathVariable Long teacherId ,
            @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(teacherId,course);
        if (createdCourse != null) {
            List<Course> courses = courseService.getAllCoursesByTeacher(course.getTeacher().getId());
            return ResponseEntity.ok(courses);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }

    //添加课包括所有章节
    @PostMapping("/createCourses/{teacherId}")
    public ResponseEntity<List<Course>> createCourses(@PathVariable Long teacherId , @RequestBody Course course) {
        Course createdCourse = courseService.createCourseByAI(teacherId,course);
        if (createdCourse != null) {
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }


    //修改课程
    @PutMapping("/{teacherId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long teacherId,@RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course);
        if (updatedCourse != null) {
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    //删除课程
    @DeleteMapping("/{teacherId}/{courseId}")
    public ResponseEntity<List<Course>> deleteCourse(@PathVariable Long teacherId,@PathVariable Long courseId) {
        Integer i = courseService.deleteCourse(courseId);
        if (i>0) {
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
