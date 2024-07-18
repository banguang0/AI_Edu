user:
--

用户登录：    http://localhost:8080/users/register    POST    @RequestBody User user
用户注册：    http://localhost:8080/users/login    POST        @RequestBody User user

-----------------------------

course:
--

---------------stu:
查询所有课程:        http://localhost:8080/courses    GET        

student查询所有课程：    http://localhost:8080/courses/student      GET    @RequestParam Long studentId

---------------teacher:
teacher查询所有课程：    http://localhost:8080/courses/teacher      GET    @RequestParam Long teacherId

添加课程：     http://localhost:8080/courses      POST    @RequestParam Long teacherId，@RequestBody Course course
添加所有课程：     http://localhost:8080/courses/createByAI     POST    @RequestParam Long teacherId，@RequestBody Course course
修改课程：    http://localhost:8080/courses        PUT        @RequestParam Long teacherId，@RequestBody Course course
删除课程：    http://localhost:8080/courses/id        DELETE        @RequestParam Long teacherId，@PathVariable Long courseId

//获取某人某门课的学习进度：http://localhost:8080/courses/{courseId}/{userId}        GET
//获取某门课所有人的学习进度：http://localhost:8080/courses/allstus/{courseId}        GET

----------------------------

chapter:
--

增加章：    http://localhost:8080/chapters        POST    @RequestParam Long teacherId,@RequestParam Long courseId, @RequestBody Chapter chapter
修改章：    http://localhost:8080/chapters/chapterId        PUT        @RequestParam Long teacherId,@PathVariable Long chapterId, @RequestBody Chapter chapter
删除章：    http://localhost:8080/chapters/chapterId        DELETE        @RequestParam Long teacherId, @PathVariable Long chapterId

## section:

增加节：    http://localhost:8080/sections        POST    @RequestParam Long teacherId,@PathVariable Long chapterId, RequestBody Section section
修改节：    http://localhost:8080/sections/sectionId        PUT        @RequestParam Long teacherId,@PathVariable Long sectionId, @RequestBody Section section
删除节：    http://localhost:8080/sections/sectionId        DELETE        @RequestParam Long teacherId,@PathVariable Long sectionId

#### teacher:

查询所有题目：        http://localhost:8080/sections/{sectionId}/exercises        GET        @PathVariable Long sectionId

//插入题目：    http://localhost:8080/sections/exercises        POST    @PathVariable Long sectionId，@RequestBody Exercise exercise
插入所有题目：        http://localhost:8080/sections/exercises/createByAI        POST    @PathVariable Long sectionId，@RequestBody List<Exercise> exerciseList

修改节中的题目：    http://localhost:8080/sections/exercises/{exerciseId}        PUT        @PathVariable Long sectionId，@PathVariable Long exerciseId, @RequestBody Exercise exercise
删除节中的题目：    http://localhost:8080/sections/exercises/{exerciseId}        DELETE        @PathVariable Long sectionId，@PathVariable Long exerciseId

向节添加文件：    http://localhost:8080/sections/{sectionId}/upload        POST    @PathVariable Integer sectionId,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestParam("fileType") String fileType

#### stu:

获取文件：    http://localhost:8080/sections/{sectionId}/download            GET        @PathVariable Integer sectionId,
                                             @RequestParam("fileType") String fileType

获取所有做题情况：    http://localhost:8080/sections/{sectionId}/records        GET         @PathVariable Long sectionId, @RequestParam Long studentId
添加做题情况：    http://localhost:8080/sections/{sectionId}/records        POST    @PathVariable Long sectionId, @RequestParam Long studentId , @RequestBody List<ExerciseRecord> exerciseRecordList
修改做题情况：    http://localhost:8080/sections/{sectionId}/records        PUT        @PathVariable Long sectionId, @RequestParam Long studentId , @RequestBody List<ExerciseRecord> exerciseRecordList

获取某个视频的总结：http://localhost:8080/sections/{sectionId}/{url}        GET        

获取某人某节的总结：http://localhost:8080/sections/{sectionId}/{userId}        GET        


