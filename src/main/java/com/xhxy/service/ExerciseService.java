package com.xhxy.service;

import com.xhxy.entity.Exercise;
import com.xhxy.entity.ExerciseRecord;

import java.util.List;

public interface ExerciseService {
    //创建题目
    Exercise createExercise(Exercise exercise);
    //一次性创建所有题目
    List<Exercise> createExerciseByAI(Long sectionId,List<Exercise> exerciseList);
    //修改题目
    Exercise updateExercise(Long exerciseId, Exercise exercise);
    //删除题目
    Integer deleteExercise(Long exerciseId);
    //查询所有题目（包括选择题和程序设计题）
    List<List> getExercisesBySectionId(Long sectionId);

    List<List> TwoExerciseRewordList (List<ExerciseRecord> exerciseRecords);

    List<List> TwoExerciseList (List<Exercise> exercises);
}
