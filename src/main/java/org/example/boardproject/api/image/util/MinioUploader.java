package org.example.boardproject.api.image.util;

import org.springframework.web.multipart.MultipartFile;

public interface MinioUploader {
    String uploadFile(MultipartFile file) throws Exception;
    void deleteFile(String fileName) throws Exception;
}
