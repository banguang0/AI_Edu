package com.xhxy.service;

import com.xhxy.entity.ExerciseRecord;
import com.xhxy.entity.FileEntity;
import com.xhxy.entity.Section;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SectionService {
    //文件上传返回文件对象
    FileEntity uploadFileForSection(Long sectionId, MultipartFile file, String fileType) throws IOException;
    //获得本节所有本类型(视频或者ppt)的文件对象
    List<FileEntity> getFileForSection(Long sectionId, String fileType);
    //节的基础增删改
    Integer updateSection(Long sectionId, Section section);
    Integer createSection(Long chapterId,Section section);
    Integer  deleteSection(Long sectionId);

    //查询所有做题情况
    List<ExerciseRecord> getSectionExercisesRecords(Long sectionId, Long studentId);
    //增加做题情况
    Integer addSectionExercisesRecords(Long courseId,Long sectionId, Long studentId, List<ExerciseRecord> exerciseRecordList);
    //修改做题情况
//    Integer updateSectionExercisesRecords(Long sectionId, Long studentId, List<ExerciseRecord> exerciseRecordList);

    FileEntity uploadFileFromUrl(Long sectionId, String url, String fileType,String fileName) throws IOException;

    String recognizeTextForFile(Long fileId);

    Integer deleteFile(Long fileId);
}
