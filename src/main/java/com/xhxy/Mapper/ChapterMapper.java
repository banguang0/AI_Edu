package com.xhxy.Mapper;

import com.xhxy.entity.Chapter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChapterMapper {
    Integer getCourseId(Long chapterId);

    Integer deleteChapter(Long chapterId);

    Integer insertChapter(Chapter chapter);

    Integer updateChapter(Chapter chapter);

    List<Chapter> getChaptersByCourseId(Long courseId);
}
