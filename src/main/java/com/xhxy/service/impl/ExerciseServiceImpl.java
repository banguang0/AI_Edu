package com.xhxy.service.impl;

import com.xhxy.Mapper.ChapterMapper;
import com.xhxy.Mapper.CourseMapper;
import com.xhxy.Mapper.ExerciseMapper;
import com.xhxy.Mapper.SectionMapper;
import com.xhxy.entity.*;
import com.xhxy.service.ExerciseService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Resource
    ExerciseMapper exerciseMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Exercise createExercise(Exercise exercise) {
        Integer chapterId = sectionMapper.getChapterId(exercise.getSection().getId());
        Integer courseId = chapterMapper.getCourseId(Long.valueOf(chapterId));
        Integer exerciseNumber = exerciseMapper.selectExerciseNumber(Long.valueOf(courseId));
        if (exerciseNumber == null) exerciseNumber=0;
        if (exercise instanceof MultipleChoiceExercise) {
            MultipleChoiceExercise mcExercise = (MultipleChoiceExercise) exercise;
            exerciseMapper.insertMultipleChoiceExercise(mcExercise);
            for (ExerciseOption option : mcExercise.getOptions()) {
                option.setExerciseId(mcExercise.getId());
                exerciseMapper.insertExerciseOption(option);
            }
            exerciseNumber++;
        } else if (exercise instanceof ProgrammingExercise) {
            ProgrammingExercise programmingExercise = (ProgrammingExercise) exercise;
            exerciseMapper.insertProgrammingExercise(programmingExercise);
            exerciseMapper.insertProgrammingAnswer(programmingExercise);
            exerciseNumber++;
        }
        Integer i = exerciseMapper.updateExerciseNumber(Long.valueOf(courseId), exerciseNumber);
        System.out.println("exerciseNumber----------"+exerciseNumber);
        return exercise;
    }

    @Override
    public List<Exercise> createExerciseByAI(Long sectionId,List<Exercise> exerciseList) {
        for (Exercise exercise : exerciseList) {
            Section section = new Section();
            section.setId(sectionId);
            exercise.setSection(section);
            createExercise(exercise);
        }
        return exerciseList;
    }
//    @Override
//    public List<Exercise> createExerciseByAI(List<Exercise> exerciseList) {
////        Section section = sectionMapper.getSectionById(sectionId);
//
//        for (Exercise exercise : exerciseList) {
//            if (exercise instanceof MultipleChoiceExercise) {//如果是选择题
//                exerciseMapper.insertMultipleChoiceExercise((MultipleChoiceExercise) exercise);
//                for (ExerciseOption option : ((MultipleChoiceExercise) exercise).getOptions()) {
//                    option.setExerciseId(exercise.getId());
//                    exerciseMapper.insertExerciseOption(option);
//                }
//            } else if (exercise instanceof ProgrammingExercise) {//如果是程序设计题
//                ProgrammingExercise programmingExercise = (ProgrammingExercise) exercise;
//                exerciseMapper.insertProgrammingExercise(programmingExercise);
//                exerciseMapper.insertProgrammingAnswer(programmingExercise);
//            }
//        }
//        return exerciseList;
//    }

    @Override
    public Exercise updateExercise(Long exerciseId, Exercise exercise) {
        exercise.setId(exerciseId);
        if (exercise instanceof MultipleChoiceExercise) {
            MultipleChoiceExercise mcExercise = (MultipleChoiceExercise) exercise;
            exerciseMapper.updateMultipleChoiceExercise(mcExercise);
            exerciseMapper.deleteExerciseOptions(exerciseId);
            for (ExerciseOption option : mcExercise.getOptions()) {
                option.setExerciseId(exerciseId);
                exerciseMapper.insertExerciseOption(option);
            }
        } else if (exercise instanceof ProgrammingExercise) {
            ProgrammingExercise programmingExercise = (ProgrammingExercise) exercise;
            exerciseMapper.updateProgrammingExercise(programmingExercise);
            exerciseMapper.updateProgrammingAnswer(programmingExercise);
        }
        return exercise;
    }

    @Override
    public Integer deleteExercise(Long exerciseId) {
        exerciseMapper.deleteExerciseOptions(exerciseId);
        exerciseMapper.deleteProgrammingAnswer(exerciseId);
        return exerciseMapper.deleteExercise(exerciseId);
    }

    @Override
    public List<List> getExercisesBySectionId(Long sectionId) {
        List<Exercise> exercises = exerciseMapper.getExercisesBySectionId(sectionId);
        List<List> list = new ArrayList<>();
        List<MultipleChoiceExercise> exerciseList1 = new ArrayList<>();
        List<ProgrammingExercise> exerciseList2 = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (exercise instanceof MultipleChoiceExercise) {
                MultipleChoiceExercise mcExercise = (MultipleChoiceExercise) exercise;
                exerciseList1.add(mcExercise);
            }else if (exercise instanceof ProgrammingExercise) {
                ProgrammingExercise programmingExercise = (ProgrammingExercise) exercise;
                exerciseList2.add(programmingExercise);
            }
        }
        list.add(exerciseList1);
        list.add(exerciseList2);
        return list;
    }

    @Override
    public List<List> TwoExerciseList (List<Exercise> exercises){
        List<List> exerciseList = new ArrayList<>();
        List<Exercise> exerciseList1 = new ArrayList<>();
        List<Exercise> exerciseList2 = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (exercise instanceof MultipleChoiceExercise) {
                exerciseList1.add(exercise);
            }else if (exercise instanceof ProgrammingExercise) {
                exerciseList2.add(exercise);
            }
        }
        exerciseList.add(exerciseList1);
        exerciseList.add(exerciseList2);
        return exerciseList;
    }

    @Override
    public List<List> TwoExerciseRewordList (List<ExerciseRecord> exerciseRecords){
        List<List> exerciseRecordList = new ArrayList<>();
        List<ExerciseRecord> exerciseRecords1 = new ArrayList<>();
        List<ExerciseRecord> exerciseRecords2 = new ArrayList<>();

        for (ExerciseRecord exerciseRecord : exerciseRecords) {
            if (exerciseRecord.getExercise() instanceof MultipleChoiceExercise) {
                exerciseRecords1.add(exerciseRecord);
            }else if (exerciseRecord.getExercise() instanceof ProgrammingExercise) {
                exerciseRecords2.add(exerciseRecord);
            }
        }
        exerciseRecordList.add(exerciseRecords1);
        exerciseRecordList.add(exerciseRecords2);
        return exerciseRecordList;
    }
}
