package com.xhxy.controller;

import com.xhxy.entity.Chapter;
import com.xhxy.entity.Course;
import com.xhxy.service.ChapterService;
import com.xhxy.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@CrossOrigin
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @Resource
    CourseService courseService;

    //删除
    @DeleteMapping("/{teacherId}/{chapterId}")
    public ResponseEntity<List<Course>> deleteChapter(@PathVariable Long teacherId, @PathVariable Long chapterId) {
        try {
            chapterService.deleteChapter(chapterId);
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 增加章
    @PostMapping("/{teacherId}/{courseId}")
    public ResponseEntity<List<Course>> createChapter(@PathVariable Long teacherId,@PathVariable Long courseId, @RequestBody Chapter chapter) {
        Integer i = chapterService.createChapter(courseId,chapter);
        if (i > 0) {
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // 更新章
    @PutMapping("/{teacherId}/{chapterId}")
    public ResponseEntity<List<Course>> updateChapter(@PathVariable Long teacherId,@PathVariable Long chapterId, @RequestBody Chapter chapter) {
        Integer i = chapterService.updateChapter(chapterId, chapter);
        if (i > 0) {
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

}
