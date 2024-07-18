package com.xhxy.service.impl;

import com.xhxy.Mapper.ChapterMapper;
import com.xhxy.Mapper.CourseMapper;
import com.xhxy.Mapper.SectionMapper;
import com.xhxy.entity.*;
import com.xhxy.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private SectionMapper  sectionMapper;


    public List<Course> getAllCourses() {
        return courseMapper.getAllCourses();
    }

    @Override
    public List<Course> getAllCoursesByStu(Long studentId) {
        List<Course> courses = courseMapper.getAllCoursesByStu(studentId);
//        for (Course course : courses) {
//            List<Chapter> chapters = chapterMapper.getChaptersByCourseId(course.getId());
//            for (Chapter chapter : chapters) {
//                List<Section> sections = sectionMapper.selectByChapterId(chapter.getId());
//                chapter.setSections(sections);
//            }
//            course.setChapters(chapters);
//        }
        return courses;
    }

    @Override
    public List<Course> getAllCoursesByTeacher(Long teacherId) {
        List<Course> courses = courseMapper.getAllCoursesByTeacher(teacherId);
//        for (Course course : courses) {
//            List<Chapter> chapters = chapterMapper.getChaptersByCourseId(course.getId());
//            for (Chapter chapter : chapters) {
//                List<Section> sections = sectionMapper.selectByChapterId(chapter.getId());
//                chapter.setSections(sections);
//            }
//            course.setChapters(chapters);
//        }
        return courses;
    }

    @Override
    public Course createCourseByAI (Long teacherId , Course course) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        course.setTeacher(teacher);
        courseMapper.insertCourse(course);

        if (course.getChapters() != null) {
            for (Chapter chapter : course.getChapters()) {
                chapter.setCourse(course);
                chapterMapper.insertChapter(chapter);

                if (chapter.getSections() != null) {
                    for (Section section : chapter.getSections()) {
                        section.setChapter(chapter);
                        sectionMapper.insertSection(section);
                    }
                }
            }
        }
        return course;
    }
    @Override
    public Course createCourse(Long teacherId, Course course) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        course.setTeacher(teacher);
        courseMapper.insertCourse(course);
        return course;
    }

    public Course updateCourse(Course course) {
        courseMapper.updateCourse(course);
        return course;
    }

    public Integer deleteCourse(Long id) {
        // 首先删除课程相关的章节和小节
        List<Chapter> chapters = chapterMapper.getChaptersByCourseId(id);
        if (chapters != null) {
            for (Chapter chapter : chapters) {
                if (chapter.getSections() != null) {
                    sectionMapper.deleteSectionsByChapterId(chapter.getId());
                }
                chapterMapper.deleteChapter(chapter.getId());
            }
        }
        // 然后删除课程
        return courseMapper.deleteCourse(id);
    }

    @Override
    public List<Course> getAllCoursesByTeacherSimple(Long teacherId) {
        List<Course> courses = courseMapper.getAllCoursesByTeacherSimple(teacherId);
        return courses;
    }

    @Override
    public List<Course> getAllCoursesByStuSimple(Long studentId) {
        List<Course> courses = courseMapper.getAllCoursesByStuSimple(studentId);
        return courses;
    }
}