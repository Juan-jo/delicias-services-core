package org.delicias.minio;


import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.minio.utils.MinioRS;
import org.delicias.minio.utils.MinioSize;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@ApplicationScoped
public class MinioStorageService {

    @ConfigProperty(name = "delicias.minio.endpoint")
    String endpoint;

    @ConfigProperty(name = "delicias.minio.access-key")
    String accessKey;

    @ConfigProperty(name = "delicias.minio.secret-key")
    String secretKey;

    @ConfigProperty(name = "delicias.minio.bucket")
    String bucket;

    @ConfigProperty(name = "delicias.imgproxy.url")
    String imgproxyUrl;

    public String upload(FileUpload fileUpload) {

        try {

            S3Client s3 = S3Client.builder()
                    .endpointOverride(URI.create(endpoint))
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(
                                            accessKey,
                                            secretKey
                                    )
                            )
                    )
                    .region(Region.US_EAST_1)
                    .forcePathStyle(true)
                    .build();

            String originalName = fileUpload.fileName();

            String extension = "";

            int index = originalName.lastIndexOf('.');

            if (index > 0) {
                extension = originalName.substring(index);
            }

            String fileName = UUID.randomUUID() + extension;

            Path path = fileUpload.filePath();//Path.of(fileUpload.fileName());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(fileUpload.contentType())
                    .build();

            s3.putObject(
                    request,
                    RequestBody.fromBytes(
                            Files.readAllBytes(path)
                    )
            );

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String pictureUrl(String fileName, MinioSize size, MinioRS rs, Short q) {

        int quality = (q != null) ? q : 80;

        return String.format(
                "%s/insecure/rs:%s:%d:%d/q:%d/g:%s/plain/http://minio:9000/%s/%s",
                imgproxyUrl,
                rs.getValue(),
                size.getWidth(),
                size.getHeight(),
                quality,
                size.getGravity(),
                bucket,
                fileName
        );
    }
}