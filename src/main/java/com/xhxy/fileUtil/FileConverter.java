package com.xhxy.fileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileConverter {

    public static MultipartFile convertFileToMultipartFile(Path filePath, String fileName) throws IOException {
        byte[] content = Files.readAllBytes(filePath);
        String contentType = Files.probeContentType(filePath);
        return new CustomMultipartFile(content, fileName, contentType);
    }
}
