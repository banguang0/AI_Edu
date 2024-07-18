package com.xhxy.service.impl;

import com.xhxy.Mapper.*;
import com.xhxy.entity.*;
import com.xhxy.fileUtil.FileConverter;
import com.xhxy.fileUtil.FileDownloader;
import com.xhxy.service.SectionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.xhxy.AIutil.videoUtil.Ifasrdemo.*;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ExerciseMapper exerciseMapper;
    @Autowired
    private StuCourseMapper stuCourseMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Resource
    private ExerciseRecordMapper exerciseRecordMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${server.url}")
    private String serverUrl;  // 服务器 URL


    /**
     * 文件上传，识别与下载
     * @param sectionId
     * @param fileUrl
     * @param fileType
     * @param fileName
     * @return
     * @throws IOException
     */
//    @Override
//    public FileEntity uploadFileFromUrl(Long sectionId, String fileUrl, String fileType,String fileName) throws IOException {
//        // 下载文件
//        Path downloadedFilePath = FileDownloader.downloadFile(fileUrl, uploadDir);
//        // 转换为MultipartFile
//        MultipartFile multipartFile = FileConverter.convertFileToMultipartFile(downloadedFilePath,fileName);
//        // 调用现有的文件上传方法
//        return uploadFileForSection(sectionId, multipartFile, fileType, fileUrl);
//    }
    @Override
    public FileEntity uploadFileFromUrl(Long sectionId, String fileUrl, String fileType, String fileName) throws IOException {
        // 下载文件并获取InputStream
        try (InputStream fileInputStream = FileDownloader.downloadFile(fileUrl)) {
            // 调用现有的文件上传方法
            return uploadFileForSection(sectionId, fileInputStream, fileName, fileType, fileUrl);
        }
    }

//    public FileEntity uploadFileForSection(Long sectionId, MultipartFile file, String fileType,String fileUrl) throws IOException {
//        // 获取上传目录的绝对路径
//        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//        // 生成唯一文件名
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
////        Path targetLocation = Paths.get(uploadDir, uniqueFileName);
//        Path targetLocation = uploadPath.resolve(uniqueFileName);
//        // 确保目录存在
//        Files.createDirectories(targetLocation.getParent());
//        // 保存文件
//        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//        // 打印文件保存路径
//        System.out.println("文件保存路径: " + targetLocation.toString());
//        // 创建File实体
//        FileEntity fileEntity = new FileEntity();
//        fileEntity.setFileName(file.getOriginalFilename());
//        fileEntity.setFileType(file.getContentType());
//        fileEntity.setFileSize(file.getSize());
//        fileEntity.setFilePath(uniqueFileName);
//        fileEntity.setUploadTime(LocalDateTime.now());
//        fileEntity.setSectionId(sectionId);
//        fileEntity.setFileUrl(fileUrl);
//        // 保存File实体到数据库
//        fileMapper.insertFile(fileEntity);
//        // 文件的 URL 地址
//        String filePath = serverUrl + "/files/" + uniqueFileName;
//        fileEntity.setFilePath(filePath);
//        return fileEntity;
//    }

    public FileEntity uploadFileForSection(Long sectionId, InputStream fileInputStream, String fileName, String fileType, String fileUrl) throws IOException {
        // 获取上传目录的绝对路径
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path targetLocation = uploadPath.resolve(uniqueFileName);
        // 确保目录存在
        Files.createDirectories(targetLocation.getParent());
        // 保存文件
        Files.copy(fileInputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        // 打印文件保存路径
        System.out.println("文件保存路径: " + targetLocation.toString());

        // 创建File实体
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileType(fileType);
        fileEntity.setFileSize(Files.size(targetLocation)); // 使用文件的实际大小
        fileEntity.setFilePath(uniqueFileName);  // 只保存文件名
        fileEntity.setUploadTime(LocalDateTime.now());
        fileEntity.setSectionId(sectionId);
        fileEntity.setFileUrl(fileUrl);

        // 保存File实体到数据库
        fileMapper.insertFile(fileEntity);

        // 文件的 URL 地址
        String filePath = serverUrl + "/files/" + uniqueFileName;
        fileEntity.setFilePath(filePath);
        return fileEntity;
    }


    public FileEntity uploadFileForSection(Long sectionId, MultipartFile file, String fileType) throws IOException {
        // 获取上传目录的绝对路径
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//        Path targetLocation = Paths.get(uploadDir, uniqueFileName);
        Path targetLocation = uploadPath.resolve(uniqueFileName);
        // 确保目录存在
        Files.createDirectories(targetLocation.getParent());
        // 保存文件
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        // 打印文件保存路径
        System.out.println("文件保存路径: " + targetLocation.toString());
        // 创建File实体
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setFilePath(uniqueFileName);
        fileEntity.setUploadTime(LocalDateTime.now());
        fileEntity.setSectionId(sectionId);
        fileEntity.getFileUrl();
        // 保存File实体到数据库
//        if ("video".equalsIgnoreCase(fileType)) {
//                System.out.println(targetLocation.toString());
//            try {
//                fileEntity.setFile_toText(getText(targetLocation.toString()));
//            } catch (SignatureException|InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        fileMapper.insertFile(fileEntity);
        // 文件的 URL 地址
        String fileUrl = serverUrl + "/files/" + uniqueFileName;
        fileEntity.setFilePath(fileUrl);
        return fileEntity;
    }
    //获取所有文件
    public List<FileEntity> getFileForSection(Long sectionId, String fileType)  {
        List<FileEntity> files;
        if ("ppt".equalsIgnoreCase(fileType)) {
            files = fileMapper.getFilesBySectionAndType(sectionId, "ppt","powerpoint");
        } else if ("video".equalsIgnoreCase(fileType)) {
            files = fileMapper.getFilesBySectionAndType(sectionId, "video","video");
        } else {
            throw new IllegalArgumentException("Invalid file type. Must be 'ppt' or 'video'.");
        }

        for (FileEntity file : files) {
            String fileUrl = serverUrl + "/files/" + file.getFilePath();
            file.setFilePath(fileUrl);
        }
        return files;
    }

    //视频识别
    @Override
    public String recognizeTextForFile(Long fileId) {
        FileEntity fileEntity = fileMapper.getFileById(fileId);
        if (fileEntity == null) {
            throw new IllegalArgumentException("File not found");
        }
        // 检查数据库中是否已存在文稿
        if (fileEntity.getFile_toText() != null && !fileEntity.getFile_toText().isEmpty()) {
            return fileEntity.getFile_toText();
        }
        // 获取文件路径
        Path filePath = Paths.get(uploadDir).resolve(fileEntity.getFilePath()).toAbsolutePath().normalize();
        // 调用AI接口进行识别
        String recognizedText = null;
        try {
            recognizedText = getText(filePath.toString());
        } catch (IOException|SignatureException|InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 保存识别结果到数据库
        fileEntity.setFile_toText(recognizedText);
        fileMapper.updateFile(fileEntity);

        return recognizedText;
    }

    @Override
    public Integer deleteFile(Long fileId) {
        // 获取文件路径
        FileEntity fileEntity = fileMapper.getFileById(fileId);
        Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileEntity.getFilePath()).normalize();
        try {
            // 检查文件是否存在
            if (Files.exists(filePath)) {
                // 删除文件
                Files.delete(filePath);
                System.out.println("文件删除成功: " + filePath.toString());
            } else {
                System.out.println("文件不存在: " + filePath.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("删除文件失败: " + filePath.toString(), e);
        }

        // 从数据库中删除文件记录
        return fileMapper.deleteFileById(fileEntity.getId());
    }


    @Override
    public Integer updateSection(Long sectionId, Section section) {
        section.setId(sectionId);
        return sectionMapper.updateSection(section);
    }

    @Override
    public Integer createSection(Long chapterId, Section section) {
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        section.setChapter(chapter);
        return sectionMapper.insertSection(section);
    }

    @Override
    public Integer deleteSection(Long sectionId) {
        return sectionMapper.deleteSection(sectionId);
    }

    /**
     * 做题情况
     */
    @Override
    public List<ExerciseRecord> getSectionExercisesRecords(Long sectionId, Long studentId) {
        List<ExerciseRecord> records = exerciseRecordMapper.getExerciseRecordsBySectionAndStudent(sectionId, studentId);
        if (records == null || records.isEmpty()) {
            List<Exercise> exerciseList = exerciseMapper.getExercisesBySectionId(sectionId);
            if (exerciseList != null && exerciseList.size() != 0) {
                for (Exercise exercise : exerciseList) {
                    exerciseRecordMapper.insertRecord(studentId,exercise.getId());
                }
            }
        }else
            return records;
        return exerciseRecordMapper.getExerciseRecordsBySectionAndStudent(sectionId, studentId);
    }

    @Override
    public Integer addSectionExercisesRecords(Long courseId,Long sectionId, Long studentId, List<ExerciseRecord> exerciseRecordList) {
        int insertedCount = 0;
        //查询分数
        Integer Integral = stuCourseMapper.selectIntegral(courseId,studentId);
        Integer completionNumber = stuCourseMapper.selectCompletionNumber(courseId,studentId);
        Integer correctionNumber = stuCourseMapper.selectCorrectionNumber(courseId,studentId);
        if (Integral == null) Integral = 0;
        if (completionNumber == null) completionNumber = 0;
        if (correctionNumber == null) correctionNumber = 0;

        for (ExerciseRecord record : exerciseRecordList) {
            //查询是否做过该题
            String answer = exerciseRecordMapper.getAnswerByStudentIdAndExerciseId(studentId,record.getExercise().getId());
            //如果未答过题
            if ((answer == null || answer.isEmpty()) && record.getAnswer() != null) {
                //插入本次作答情况
                Integer i =  exerciseRecordMapper.updateExerciseRecord(record);
                insertedCount += i;
                completionNumber ++;
                //第一次答题是否正确
                if (i>0 && record.getIsCorrect()==1){
                    Integral += record.getExercise().getDifficultyLevel();
                    correctionNumber ++;
                }
            }
        }
        stuCourseMapper.updateIntegral(studentId,courseId,Integral);
        stuCourseMapper.updateCompletionNumber(studentId,courseId,completionNumber);
        stuCourseMapper.updateCorrectionNumber(studentId,courseId,correctionNumber);
        return insertedCount;
    }
//    @Override
//    public Integer updateSectionExercisesRecords(Long sectionId, Long studentId, List<ExerciseRecord> exerciseRecordList) {
//        int updatedCount = 0;
//        for (ExerciseRecord record : exerciseRecordList) {
//            record.getStudent().setId(studentId);
//            updatedCount += exerciseRecordMapper.updateExerciseRecord(record);
//        }
//        return updatedCount;
//    }
}
