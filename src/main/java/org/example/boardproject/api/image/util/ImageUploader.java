package org.example.boardproject.api.image.util;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUploader implements MinioUploader, S3Uploader {

    private final MinioClient minioClient;
    private final MinioProperties properties;
    private final S3Client s3Client;
    private final AwsS3Properties props;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(fileName)
                        .stream(file.getInputStream(), -1, 10 * 1024 * 1024) // 사이즈 모를 때
                        .contentType(file.getContentType())
                        .build()
        );

        return properties.getUrl() + "/" + properties.getBucketName() + "/" + fileName;
    }

    @Override
    public void deleteFile(String fileName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(fileName)
                        .build()
        );
    }

    @Override
    public String upload(MultipartFile file, String dirName) throws IOException {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(props.getBucketName())
                        .key(fileName)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                props.getBucketName(),
                props.getRegion(),
                fileName);
    }

    @Override
    public void delete(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(props.getBucketName())
                .key(key)
                .build());
    }
}
