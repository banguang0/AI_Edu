package com.xhxy.service.impl;

import com.xhxy.Mapper.ChapterMapper;
import com.xhxy.Mapper.CourseMapper;
import com.xhxy.Mapper.StudyRecordMapper;
import com.xhxy.entity.Chapter;
import com.xhxy.entity.Course;
import com.xhxy.entity.Section;
import com.xhxy.entity.StudyRecord;
import com.xhxy.service.CourseService;
import com.xhxy.service.StudyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudyRecordServiceImpl implements StudyRecordService {
    @Autowired
    StudyRecordMapper studyRecordMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public List<StudyRecord> findByStudentId(Long studentId) {
        List<StudyRecord> studyRecordList = studyRecordMapper.findStudyRecordsByStudentId(studentId);
        return studyRecordList;
    }

    @Override
    public List<StudyRecord> findByCourseId(Long courseId) {
        List<StudyRecord> studyRecordList = studyRecordMapper.findStudyRecordsByCourseId(courseId);
        return studyRecordList;
    }
}
