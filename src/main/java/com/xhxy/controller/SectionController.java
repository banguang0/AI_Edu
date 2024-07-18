package com.xhxy.controller;

import com.xhxy.entity.*;
import com.xhxy.service.CourseService;
import com.xhxy.service.ExerciseService;
import com.xhxy.service.SectionService;

import com.xhxy.AIutil.pptUtil.PptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sections")
@CrossOrigin
public class SectionController {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExerciseService exerciseService;


    // 增加小节
    @PostMapping("/{teacherId}/{chapterId}")
    public ResponseEntity<List<Course>> createSection(@PathVariable Long teacherId,@PathVariable Long chapterId, @RequestBody Section section) {
        Integer i = sectionService.createSection(chapterId,section);
        if (i>0){
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // 更新小节
    @PutMapping("/{teacherId}/{sectionId}")
    public ResponseEntity<List<Course>> updateSection(@PathVariable Long teacherId,@PathVariable Long sectionId, @RequestBody Section section) {
        Integer i = sectionService.updateSection(sectionId, section);
        if (i>0){
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    //删除section
    @DeleteMapping("/{teacherId}/{sectionId}")
    public ResponseEntity<List<Course>> deleteSection(@PathVariable Long teacherId,@PathVariable Long sectionId) {
        try {
            Integer i = sectionService.deleteSection(sectionId);
            List<Course> courses = courseService.getAllCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //------------teacher:
    //查询某节所有题目
    @GetMapping("/{sectionId}/exercises")
    public ResponseEntity<?> getExercisesBySectionId(@PathVariable Long sectionId) {
        List<List> exercises = exerciseService.getExercisesBySectionId(sectionId);
//        for (Exercise exercise : exercises) {
//            System.out.println(exercise.getType());
//        }
        return ResponseEntity.ok(exercises);
    }
    //插入题目
    @PostMapping("/exercise/{sectionId}")
        public ResponseEntity<?> createExercise(@PathVariable Long sectionId,@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        if (createdExercise != null){
            List<List> exercises = exerciseService.getExercisesBySectionId(sectionId);
            return ResponseEntity.ok(exercises);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create exercise");
    }
    //插入所有题目
    @PostMapping("/exercises/{sectionId}")
    public ResponseEntity<?> createExerciseByAI(@PathVariable Long sectionId,@RequestBody List<Exercise> exerciseList) {
        List<Exercise> createdExercise = exerciseService.createExerciseByAI(sectionId,exerciseList);
        if (createdExercise != null){
            List<List> exercises = exerciseService.getExercisesBySectionId(sectionId);
            return ResponseEntity.ok(exercises);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create exercise by AI");
    }
    //删除题目
    @DeleteMapping("/exercises/{sectionId}/{exerciseId}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long sectionId,@PathVariable Long exerciseId) {
        try {
            exerciseService.deleteExercise(exerciseId);
            List<List> exercises = exerciseService.getExercisesBySectionId(sectionId);
            return ResponseEntity.ok(exercises);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete exercise");
        }
    }

    //更新题目
    @PostMapping("/exercises/{sectionId}/{exerciseId}")
    public ResponseEntity<?> updateExercise(@PathVariable Long sectionId,@PathVariable Long exerciseId, @RequestBody Exercise exercise) {

        Exercise updatedExercise = exerciseService.updateExercise(exerciseId, exercise);
        if (updatedExercise != null){
            List<List> exercises = exerciseService.getExercisesBySectionId(sectionId);
            return ResponseEntity.status(HttpStatus.OK).body(exercises);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update exercise");
    }

    //-----------stu:
    //查询某节所有题目及做题情况
    @GetMapping("/{sectionId}/records/{studentId}")
    public ResponseEntity<?> getSectionExercisesRecords(
            @PathVariable Long sectionId, @PathVariable Long studentId) {
        List<ExerciseRecord> exerciseRecords = sectionService.getSectionExercisesRecords(sectionId, studentId);
        return ResponseEntity.ok(exerciseService.TwoExerciseRewordList(exerciseRecords));
    }

    //添加做题情况
    @PostMapping("/{courseId}/{sectionId}/records/{studentId}")
    public ResponseEntity<?> addSectionExercisesRecords(
            @PathVariable Long courseId,@PathVariable Long sectionId, @PathVariable Long studentId , @RequestBody List<ExerciseRecord> exerciseRecordList) {
         sectionService.addSectionExercisesRecords(courseId,sectionId,studentId,exerciseRecordList);
         List<ExerciseRecord> exerciseRecords = sectionService.getSectionExercisesRecords(sectionId, studentId);
         return ResponseEntity.ok(exerciseService.TwoExerciseRewordList(exerciseRecords));
    }
    //修改做题情况
//    @PutMapping("/{sectionId}/records")
//    public ResponseEntity<List<ExerciseRecord>> updateSectionExercisesRecords(
//            @PathVariable Long sectionId,@RequestParam Long studentId , @RequestBody List<ExerciseRecord> exerciseRecordList) {
//        sectionService.updateSectionExercisesRecords(sectionId,studentId,exerciseRecordList);
//        List<ExerciseRecord> exerciseRecords = sectionService.getSectionExercisesRecords(sectionId, studentId);
//        return ResponseEntity.ok(exerciseRecords);
//    }

    //删除文件
    @DeleteMapping("/{sectionId}/{fileId}/{fileType}")
    public ResponseEntity<?> deleteFile(@PathVariable Long sectionId,@PathVariable String fileType,
                                        @PathVariable Long fileId) throws IOException {
        Integer i = sectionService.deleteFile(fileId);
        if (i > 0) {
            List<FileEntity> fileEntities = sectionService.getFileForSection(sectionId,fileType);
            return ResponseEntity.ok(fileEntities);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
    }

    //文件上传
    @PostMapping("/{sectionId}/upload/{fileType}")
    public ResponseEntity<?> uploadFile(@PathVariable Long sectionId,
                                                 @RequestParam("file") MultipartFile file,
                                                 @PathVariable String fileType) throws IOException {
        FileEntity fileEntity = sectionService.uploadFileForSection(sectionId, file, fileType);
        if (fileEntity != null) {
            List<FileEntity> fileEntities = sectionService.getFileForSection(sectionId,fileType);
            return ResponseEntity.ok(fileEntities);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
    }

    // AI识别文字接口
    @PostMapping("/recognizeText/{fileId}")
    public ResponseEntity<?> recognizeText(@PathVariable Long fileId) {
        try {
            String text = sectionService.recognizeTextForFile(fileId);
            return ResponseEntity.ok(text);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recognizing text: " + e.getMessage());
        }
    }

    //文件下载
    @GetMapping("/{sectionId}/download/{fileType}")
    public ResponseEntity<List<FileEntity>> downloadFile(@PathVariable Long sectionId,
                                             @PathVariable String fileType) throws FileNotFoundException {
        List<FileEntity> files = sectionService.getFileForSection(sectionId, fileType);
        return ResponseEntity.ok(files);
    }

    //生成PPT大纲
    @GetMapping("/generateOutline/{question}")
    public String generateOutline(@PathVariable String question) {
        try {
            return PptUtil.getOutlineByQuestion(question);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error generating outline: " + e.getMessage();
        }
    }

    //大纲生成PPT
    @PostMapping("/generatePPT/{sectionId}/{fileName}/{theme}")
    public ResponseEntity<?> generatePPT(@PathVariable Long sectionId,@PathVariable String fileName,@PathVariable String theme,@RequestBody String outline) {
        try {
                //获取url
                String url = PptUtil.getUrl(theme,outline);
                // 如果URL不为空，则进行保存操作
                if (url != null && !url.isEmpty()) {
                // 调用文件上传服务保存文件
                String fileType = "ppt";
//                FileEntity fileEntity = sectionService.uploadFileForSection(sectionId, file, fileType);

                FileEntity fileEntity = sectionService.uploadFileFromUrl(sectionId, url, fileType,fileName+".pptx");

                List<FileEntity> fileEntities = sectionService.getFileForSection(sectionId,fileType);
                return ResponseEntity.ok(fileEntities);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("PPT URL is empty");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PPT: " + e.getMessage());
        }
    }
}


