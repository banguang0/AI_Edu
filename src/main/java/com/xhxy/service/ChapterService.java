package com.xhxy.service;

import com.xhxy.entity.Chapter;

public interface ChapterService {
    //删除章及其节
    Integer deleteChapter(Long chapterId);
    //创建章不包括节
    Integer createChapter(Long courseId, Chapter chapter);
    //更新章不包括节
    Integer updateChapter(Long chapterId, Chapter chapter);

}
