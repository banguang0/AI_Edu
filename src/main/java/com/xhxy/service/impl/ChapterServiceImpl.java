package com.xhxy.service.impl;

import com.xhxy.Mapper.ChapterMapper;
import com.xhxy.Mapper.SectionMapper;
import com.xhxy.entity.Chapter;
import com.xhxy.entity.Course;
import com.xhxy.service.ChapterService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Resource
    ChapterMapper chapterMapper;

    @Resource
    SectionMapper sectionMapper;

    @Override
    public Integer deleteChapter(Long chapterId) {
        // 首先删除章节下的所有小节
        sectionMapper.deleteSectionsByChapterId(chapterId);
        // 然后删除章节
        return chapterMapper.deleteChapter(chapterId);
    }

    @Override
    public Integer createChapter(Long courseId, Chapter chapter) {
        Course course = new Course();
        course.setId(courseId);
        chapter.setCourse(course);
        return chapterMapper.insertChapter(chapter);
    }

    @Override
    public Integer updateChapter(Long chapterId, Chapter chapter) {
        chapter.setId(chapterId);
        return chapterMapper.updateChapter(chapter);
    }
}
