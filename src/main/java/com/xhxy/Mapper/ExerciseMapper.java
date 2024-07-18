package com.xhxy.Mapper;

import com.xhxy.entity.Exercise;
import com.xhxy.entity.ExerciseOption;
import com.xhxy.entity.MultipleChoiceExercise;
import com.xhxy.entity.ProgrammingExercise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExerciseMapper {
    void insertMultipleChoiceExercise(MultipleChoiceExercise exercise);
    void insertExerciseOption(ExerciseOption option);
    void insertProgrammingExercise(ProgrammingExercise exercise);
    void insertProgrammingAnswer(ProgrammingExercise exercise);

    void updateMultipleChoiceExercise(MultipleChoiceExercise exercise);
    void updateProgrammingExercise(ProgrammingExercise exercise);
    void updateProgrammingAnswer(ProgrammingExercise exercise);

    void deleteExerciseOptions(Long exerciseId);
    void deleteProgrammingAnswer(Long exerciseId);
    Integer deleteExercise(Long exerciseId);

    List<Exercise> getExercisesBySectionId(Long sectionId);
    List<ExerciseOption> getExerciseOptions(Long exerciseId);

    String getProgrammingAnswer(Long exerciseId);

    Exercise getExerciseById(Long id);

    Integer selectExerciseNumber(Long courseId);

    Integer updateExerciseNumber(@Param("courseId") Long courseId, @Param("exerciseNumber") Integer exerciseNumber);

}
