package com.favoritemedium.sttops.service;

import com.favoritemedium.sttops.model.entity.FileMetaInfo;
import com.favoritemedium.sttops.model.entity.STTResult;
import com.favoritemedium.sttops.model.entity.User;
import com.favoritemedium.sttops.model.repository.FileMetaInfoRepository;
import io.minio.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileOpsService {

    @Autowired
    private final MinioClient minioClient;

    @Autowired
    final FileMetaInfoRepository fileMetaInfoRepository;

    @Autowired
    private final STTService sttService;

    private void createOrUseBucket(MinioClient minioClient, String bucket) {
        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @param userName
     * @param fileName
     * @param data
     */
    public void upload(String userName, String fileName, byte[] data) {
        String bucket = "sttops";
        createOrUseBucket(minioClient, bucket);
        File file = new File("/tmp/" + fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(data);
            //TODO: Need to handle the case when user upload the same file multiple times
            ObjectWriteResponse response = minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucket).object(userName + "/" + fileName)
                    .filename(file.getAbsolutePath()).build());
            FileMetaInfo info = fileMetaInfoRepository.save((FileMetaInfo.builder().fileName(fileName)
                    .fromFilePath(bucket+"/"+userName+"/"+"fileName"))//Minio file path
                    .user(User.builder().id(1).build()).build());//TODO: harcoded value
            Optional<String> text = sttService.callSTT(data);
            text.ifPresent(result -> sttService.createSTTResult(STTResult.builder()
                    .sttResult(result).fileMetaInfo(info).fileName(fileName).build()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
