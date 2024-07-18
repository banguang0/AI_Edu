package com.xhxy.controller;

import com.xhxy.entity.StudyRecord;
import com.xhxy.service.StudyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/studentPerformance")
@CrossOrigin
public class StudyRecordController {

    @Autowired
    private StudyRecordService studyRecordService;

    //查询学生每门课的学习情况
    @GetMapping("/AllCourseToStu/{studentId}")
    public ResponseEntity<?> getStudyRecordsByStudentId(@PathVariable Long studentId) {
        List<StudyRecord> studyRecordList = studyRecordService.findByStudentId(studentId);
        return ResponseEntity.ok(studyRecordList);
    }

    //查询某门课所有学生的学习情况
    @GetMapping("/AllStuToCourse/{courseId}")
    public ResponseEntity<?> getStudyRecordsByCourseId(@PathVariable Long courseId) {
        List<StudyRecord> studyRecordList = studyRecordService.findByCourseId(courseId);
        // 排序操作
        Collections.sort(studyRecordList, Comparator.comparing(StudyRecord::getIntegral).reversed());
        return ResponseEntity.ok(studyRecordList);
    }
}
