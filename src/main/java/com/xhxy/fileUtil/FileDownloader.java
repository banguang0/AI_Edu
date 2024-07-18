package com.xhxy.fileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.net.URL;
import java.net.HttpURLConnection;

public class FileDownloader {

//    public static Path downloadFile(String fileUrl, String downloadDir) throws IOException {
//        URL url = new URL(fileUrl);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//        httpURLConnection.setRequestMethod("GET");
//        httpURLConnection.setConnectTimeout(5000);
//        httpURLConnection.setReadTimeout(5000);
//
//        // 获取文件名
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//        // 创建下载目录路径
//        Path downloadPath = Paths.get(downloadDir).toAbsolutePath().normalize();
//        Files.createDirectories(downloadPath);
//        Path filePath = downloadPath.resolve(fileName);
//
//        try (InputStream inputStream = httpURLConnection.getInputStream()) {
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//            return filePath;
//        }
//    }
public static InputStream downloadFile(String fileUrl) throws IOException {
    URL url = new URL(fileUrl);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setConnectTimeout(5000);
    httpURLConnection.setReadTimeout(5000);

    return httpURLConnection.getInputStream();
}
}
