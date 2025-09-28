package org.example.boardproject.api.image.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AwsS3Properties {

    @Value("${app.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${app.aws.s3.region}")
    private String region;
}
