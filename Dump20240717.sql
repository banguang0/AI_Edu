CREATE DATABASE  IF NOT EXISTS `edu_ai` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `edu_ai`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: edu_ai
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aianalysis`
--

DROP TABLE IF EXISTS `aianalysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aianalysis` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint DEFAULT NULL,
  `content` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aianalysis`
--

LOCK TABLES `aianalysis` WRITE;
/*!40000 ALTER TABLE `aianalysis` DISABLE KEYS */;
/*!40000 ALTER TABLE `aianalysis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_id` bigint DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `order_number` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES (1,1,'第一章 Java基础',1),(2,1,'第二章 面向对象编程',2),(3,1,'第三章 Java核心类库',3),(4,1,'第四章 Java图形用户界面',4),(5,1,'第五章 Java网络编程',5),(6,2,'第一章 C++基础',1),(7,2,'第二章 控制结构',2),(8,2,'第三章 函数与编译预处理',3),(9,2,'第四章 数组与指针',4),(10,2,'第五章 面向对象编程基础',5);
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `exerciseNumber` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Java','本课旨在为初学者提供Java编程语言的基础知识和实践技能',1,10),(2,'C++入门','本课程旨在为初学者提供C++编程语言的基础知识，通过理论与实践相结合的方式，使学生能够掌握C++的基本语法、数据结构、面向对象编程等核心概念。',2,NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `section_id` bigint DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `content` text,
  `correct_answer` varchar(10) DEFAULT NULL,
  `difficulty_level` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
INSERT INTO `exercise` VALUES (3,2,'选择题','以下关于Java方法重载的说法，正确的是？M','A',1,'2024-07-17 14:56:18','2024-07-18 05:16:31'),(4,2,'选择题','以下哪个关键字用于继承一个类？','A',1,'2024-07-17 14:56:18','2024-07-17 14:56:18'),(5,2,'选择题','以下哪个不是Java中的访问修饰符？','D',1,'2024-07-17 14:56:18','2024-07-17 14:56:18'),(6,2,'编程题','编写一个Java程序，定义一个方法来计算两个整数的和并返回结果。',NULL,1,'2024-07-17 14:56:18','2024-07-17 14:56:18'),(7,2,'编程题','编写一个Java程序，定义一个类Person，包含属性name和age，并定义一个方法displayInfo()来输出这些信息。',NULL,2,'2024-07-17 14:56:18','2024-07-17 14:56:18'),(8,2,'编程题','编写一个Java程序，定义一个方法来判断一个整数是否为素数。',NULL,2,'2024-07-17 14:56:18','2024-07-17 14:56:18'),(9,2,'编程题','编写一个Java程序，定义一个类Calculator，包含方法add、subtract、multiply和divide，实现基本的加减乘除运算。',NULL,3,'2024-07-17 14:56:18','2024-07-17 14:56:18'),(10,2,'选择题','下列关于Java基本数据类型的说法中，正确的是？mm','C',1,'2024-07-17 16:44:30','2024-07-17 16:44:30');
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_option`
--

DROP TABLE IF EXISTS `exercise_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_option` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `exercise_id` bigint DEFAULT NULL,
  `letter` varchar(10) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_option`
--

LOCK TABLES `exercise_option` WRITE;
/*!40000 ALTER TABLE `exercise_option` DISABLE KEYS */;
INSERT INTO `exercise_option` VALUES (13,4,'A','extends'),(14,4,'B','implements'),(15,4,'C','import'),(16,4,'D','export'),(17,5,'A','public'),(18,5,'B','private'),(19,5,'C','protected'),(20,5,'D','package'),(25,10,'A','Java中没有浮点类型'),(26,10,'B','Java中boolean类型占用4个字节'),(27,10,'C','Java中int类型是32位的'),(28,10,'D','Java中char类型是8位的'),(33,3,'A','方法重载要求方法名不同'),(34,3,'B','方法重载要求参数列表不同'),(35,3,'C','方法重载要求返回类型相同'),(36,3,'D','方法重载要求访问修饰符相同');
/*!40000 ALTER TABLE `exercise_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exerciserecord`
--

DROP TABLE IF EXISTS `exerciserecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exerciserecord` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint DEFAULT NULL,
  `exercise_id` bigint DEFAULT NULL,
  `answer` varchar(45) DEFAULT NULL,
  `is_correct` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exerciserecord`
--

LOCK TABLES `exerciserecord` WRITE;
/*!40000 ALTER TABLE `exerciserecord` DISABLE KEYS */;
INSERT INTO `exerciserecord` VALUES (1,1,3,'A',1),(2,1,4,'A',1),(3,1,5,NULL,NULL),(4,1,6,NULL,NULL),(5,1,7,NULL,NULL),(6,1,8,NULL,NULL),(7,1,9,NULL,NULL),(8,1,10,NULL,NULL),(9,2,3,'A',1),(10,2,4,'A',1),(11,2,5,'C',0),(12,2,6,'xxx',1),(13,2,7,NULL,NULL),(14,2,8,NULL,NULL),(15,2,9,NULL,NULL),(16,2,10,'C',1);
/*!40000 ALTER TABLE `exerciserecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `file_type` varchar(100) NOT NULL,
  `file_size` bigint NOT NULL,
  `file_path` varchar(1000) NOT NULL,
  `upload_time` datetime NOT NULL,
  `file_toText` mediumtext,
  `section_id` bigint DEFAULT NULL,
  `file_url` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES (2,'难受.mp4','video/mp4',3423225,'b3ee664b-3a01-48b6-b106-e30bba9d75c4_难受.mp4','2024-07-17 22:37:16','唉呀我中毒啦难受难受真难受，中毒中毒好难受，啊我晕。啦',1,NULL),(3,'数据结构中的图.pptx','ppt',1036559,'77ca624c-842e-4511-8394-40630f4f615d_数据结构中的图.pptx','2024-07-17 22:42:32',NULL,1,'https://bjcdn.openstorage.cn/xinghuo-privatedata/%2Ftmp/apiTempFile263217383fa640b89254c8b5f19702197232364585321162739/AI%E8%BE%85%E5%8A%A9%E6%95%99%E5%AD%A6%E5%BA%94%E7%94%A8%E4%BB%8B%E7%BB%8D.pptx'),(5,'AI辅助教学.pptx','ppt',288134,'8f07ca6a-f903-4ed0-8533-71e0c0ccdb98_AI辅助教学.pptx','2024-07-18 00:02:29',NULL,1,'https://bjcdn.openstorage.cn/xinghuo-privatedata/%2Ftmp/apiTempFileeff06c6123b6492b87116280e64781596010468844390824122/AI%E8%BE%85%E5%8A%A9%E6%95%99%E5%AD%A6%E5%BA%94%E7%94%A8%E4%BB%8B%E7%BB%8D.pptx');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programming_answer`
--

DROP TABLE IF EXISTS `programming_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programming_answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `exercise_id` bigint DEFAULT NULL,
  `answer` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programming_answer`
--

LOCK TABLES `programming_answer` WRITE;
/*!40000 ALTER TABLE `programming_answer` DISABLE KEYS */;
INSERT INTO `programming_answer` VALUES (1,6,'public int sum(int a, int b) {\n    return a + b;\n}'),(2,7,'class Person {\n    String name;\n    int age;\n\n    Person(String name, int age) {\n        this.name = name;\n        this.age = age;\n    }\n\n    void displayInfo() {\n        System.out.println(\"Name: \" + name + \", Age: \" + age);\n    }\n}'),(3,8,'public boolean isPrime(int num) {\n    if (num <= 1) return false;\n    for (int i = 2; i <= Math.sqrt(num); i++) {\n        if (num % i == 0) return false;\n    }\n    return true;\n}'),(4,9,'class Calculator {\n    public int add(int a, int b) {\n        return a + b;\n    }\n\n    public int subtract(int a, int b) {\n        return a - b;\n    }\n\n    public int multiply(int a, int b) {\n        return a * b;\n    }\n\n    public double divide(int a, int b) {\n        if (b == 0) throw new ArithmeticException(\"Division by zero\");\n        return (double) a / b;\n    }\n}');
/*!40000 ALTER TABLE `programming_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `chapter_id` bigint DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `order_number` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,1,'Java简介与环境搭建',1),(2,1,'Java基本语法',2),(3,1,'Java数据类型与变量',3),(4,1,'Java运算符与表达式',4),(5,1,'Java控制结构',5),(6,2,'类与对象的基本概念',1),(7,2,'构造方法与重载',2),(8,2,'继承与多态',3),(9,2,'接口与抽象类',4),(10,2,'封装与JavaBean',5),(11,3,'字符串处理',1),(12,3,'集合框架',2),(13,3,'异常处理',3),(14,3,'输入输出流',4),(15,3,'多线程编程',5),(16,4,'Swing基础',1),(17,4,'事件处理',2),(18,4,'常用Swing组件',3),(19,4,'布局管理器',4),(20,4,'图形绘制',5),(21,5,'网络编程基础',1),(22,5,'Socket编程',2),(23,5,'URL与URLConnection',3),(24,5,'HTTP请求与响应',4),(25,5,'简易Web服务器实现',5),(26,6,'C++简介',1),(27,6,'安装C++开发环境',2),(28,6,'第一个C++程序',3),(29,6,'基本数据类型和变量',4),(30,6,'简单的输入输出',5),(31,7,'条件语句',1),(32,7,'循环语句',2),(33,7,'跳转语句',3),(34,7,'嵌套控制结构',4),(35,7,'控制结构综合应用',5),(36,8,'函数的定义与声明',1),(37,8,'函数参数传递',2),(38,8,'返回值的使用',3),(39,8,'内联函数与宏定义',4),(40,8,'编译预处理命令',5),(41,9,'一维数组与多维数组',1),(42,9,'数组的应用',2),(43,9,'指针的概念与使用',3),(44,9,'指针与数组',4),(45,9,'指针与函数',5),(46,10,'类与对象',1),(47,10,'构造函数与析构函数',2),(48,10,'复制构造函数与赋值运算符',3),(49,10,'this指针与静态成员',4),(50,10,'继承与派生',5);
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stu_course`
--

DROP TABLE IF EXISTS `stu_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stu_course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `stu_id` bigint DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `integral` int DEFAULT NULL,
  `completionNumber` int DEFAULT NULL,
  `correctionNumber` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_course`
--

LOCK TABLES `stu_course` WRITE;
/*!40000 ALTER TABLE `stu_course` DISABLE KEYS */;
INSERT INTO `stu_course` VALUES (1,1,1,3,4,3),(3,1,2,NULL,NULL,NULL),(4,2,1,4,5,4);
/*!40000 ALTER TABLE `stu_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `enrollment_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,1,'李四','2023-09-01'),(2,3,'张三','2023-09-01');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,2,'张xx'),(2,4,'李xx');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL,
  `number` bigint DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'student',1000,'小李','1234','2546851525@qq.com'),(2,'teacher',1100,'张老师','12345','2545635469@qq.com'),(3,'student',1001,'小张','123','2546851526@qq.com'),(4,'teacher',1101,'李老师','1234','2545635469@qq.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-18 14:21:00
