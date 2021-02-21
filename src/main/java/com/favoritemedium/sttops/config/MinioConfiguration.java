package com.favoritemedium.sttops.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${minio.username}")
    String userName;
    @Value("${minio.password}")
    String password;
    @Value("${minio.host}")
    String host;
    @Value("${minio.port}")
    int port;
    @Value("${minio.tls.enabled}")
    Boolean isTlsEnabled;

    @Bean
    public MinioClient getMinioClient() {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(host,port,isTlsEnabled)
                            .credentials(userName, password)
                            .build();
            return minioClient;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
