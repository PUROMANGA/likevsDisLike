package org.example.boardproject.api.image.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.image.entity.Image;
import org.example.boardproject.api.image.repository.ImageRepository;
import org.example.boardproject.api.image.util.ImageUploader;
import org.example.boardproject.api.image.util.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageUploader imageUploader;
    private final ImageRepository imageRepository;

    @Transactional
    public void uploadImageService(MultipartFile file, Long id) throws Exception {
        String imagePath = imageUploader.uploadFile(file);
        Image image = new Image(imagePath, id, file.getName());
        imageRepository.save(image);
    }

    @Transactional
    public void deleteImageService(Long id) throws Exception {
        Image image = imageRepository.findByTopicId(id);
        imageUploader.deleteFile(image.getFileName());
        imageRepository.delete(image);
    }
}
