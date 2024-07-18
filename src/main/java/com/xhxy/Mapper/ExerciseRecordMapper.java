package com.xhxy.Mapper;

import com.xhxy.entity.ExerciseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExerciseRecordMapper {
    List<ExerciseRecord> getExerciseRecordsBySectionAndStudent(@Param("sectionId") Long sectionId, @Param("studentId") Long studentId);

    String getAnswerByStudentIdAndExerciseId(@Param("studentId") Long studentId , @Param("exercise") Long exercise);
//    ExerciseRecord getExerciseRecordByExerciseId(@Param("studentId") Long studentId , @Param("exercise") Long exercise);

    Integer insertExerciseRecord(ExerciseRecord record);
    Integer updateExerciseRecord(ExerciseRecord record);

    void insertRecord(Long studentId, Long exerciseId);

}
