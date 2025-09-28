package org.example.boardproject.api.image.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface S3Uploader {
    String upload(MultipartFile file, String dirName) throws IOException;
    void delete(String key);
}
