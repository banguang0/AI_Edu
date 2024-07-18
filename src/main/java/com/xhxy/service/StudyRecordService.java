package com.xhxy.service;

import com.xhxy.entity.Exercise;
import com.xhxy.entity.StudyRecord;

import java.util.List;

public interface StudyRecordService {
    //某名学生的学习情况
    List<StudyRecord> findByStudentId(Long studentId);

    //某门课所有学生的学习情况
    List<StudyRecord> findByCourseId(Long courseId);
}
