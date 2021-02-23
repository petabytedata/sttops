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

/**
 * Service layer , containing operations specific to files and filesystem(Minio)
 */
@Service
@AllArgsConstructor
public class FileOpsService {

    @Autowired
    private final MinioClient minioClient;

    @Autowired
    final FileMetaInfoRepository fileMetaInfoRepository;

    @Autowired
    private final STTService sttService;

    /**
     *
     * @param minioClient
     * @param bucket
     */
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
     *This method upload the given file in Minio server[<bucket>/<object>], e.g., "sttops/user-1/voice.mp4".
     * This very same file is then passed to another service for converting the given audio into text.And the end result,
     * i.e, Speech-To-Text , is then stored back in the DB.
     *
     * Note: Here we have scope to break down this API into two separate operations/calls/services/bounded contexts.
     * 1> Only uploading file into Minio server.
     * 2> Calling STT service.
     *
     * Both the above operations could be linked via any messaging systems to decouple them and this way we will also be
     * able to have more resilient and scalable and light weight micro services, to handle this operation.
     *
     * @param userName
     * @param fileName
     * @param data
     */
    public void upload(String userName, String fileName, byte[] data) {
        String bucket = "sttops"; //TODO: bucket name should have been fetched from applications.yml
        //TODO: This should have been called only once in the lifetime of application, essentially when starting application
        createOrUseBucket(minioClient, bucket);

        File file = new File("/tmp/" + fileName);//TODO: Should remove hardcoded "/tmp/"
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(data);
            //TODO: Need to handle the case when user upload the same file multiple times
            ObjectWriteResponse response = minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucket).object(userName + "/" + fileName)
                    .filename(file.getAbsolutePath()).build());
            FileMetaInfo info = fileMetaInfoRepository.save((FileMetaInfo.builder().fileName(fileName)
                    .fromFilePath(bucket+"/"+userName+"/"+"fileName"))//Minio file path
                    .user(User.builder().id(1).build()).build());//TODO: hardcoded value
            Optional<String> text = sttService.callSTT(data);
            text.ifPresent(result -> sttService.createSTTResult(STTResult.builder()
                    .sttResult(result).fileMetaInfo(info).fileName(fileName).build()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
