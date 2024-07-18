package com.xhxy.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StuCourseMapper {
    Integer insertStuCourse(@Param("stuId") Long stuId, @Param("courseId") Long courseId);

    Integer updateIntegral(@Param("stuId") Long stuId, @Param("courseId") Long courseId, @Param("integral") Integer integral);

    Integer selectIntegral(@Param("courseId") Long courseId,@Param("stuId") Long stuId);

    Integer updateCompletionNumber(@Param("stuId") Long stuId, @Param("courseId") Long courseId, @Param("integral") Integer completionNumber);

    Integer selectCompletionNumber(@Param("courseId") Long courseId,@Param("stuId") Long stuId);

    Integer updateCorrectionNumber(@Param("stuId") Long stuId, @Param("courseId") Long courseId, @Param("integral") Integer correctionNumber);

    Integer selectCorrectionNumber(@Param("courseId") Long courseId,@Param("stuId") Long stuId);
}
