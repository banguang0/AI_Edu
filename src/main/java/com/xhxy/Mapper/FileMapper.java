package com.xhxy.Mapper;

import com.xhxy.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {

    void insertFile(FileEntity file);

    List<FileEntity> getFilesBySectionAndType(Long sectionId, String fileType1,String fileType2);


    FileEntity getFileById(Long fileId);

    void updateFile(FileEntity fileEntity);

    Integer deleteFileById(Long id);
}
