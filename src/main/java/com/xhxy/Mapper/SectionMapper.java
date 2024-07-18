package com.xhxy.Mapper;

import com.xhxy.entity.Section;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SectionMapper {
    Section getSectionById(Integer id);

    Integer getChapterId(Long sectionId);

    Integer updateSection(Section section);

    Integer insertSection(Section section);

    Integer deleteSection(Long sectionId);

    void deleteSectionsByChapterId(Long chapterId);

    List<Section> selectByChapterId(Long chapterId);
}
