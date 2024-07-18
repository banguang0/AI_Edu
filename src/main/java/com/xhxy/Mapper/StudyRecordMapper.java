package com.xhxy.Mapper;

import com.xhxy.entity.StudyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudyRecordMapper {
    List<StudyRecord> findStudyRecordsByStudentId(Long studentId);

    List<StudyRecord> findStudyRecordsByCourseId(Long courseId);

}
