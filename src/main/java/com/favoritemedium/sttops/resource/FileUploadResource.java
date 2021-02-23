package com.favoritemedium.sttops.resource;

import com.favoritemedium.sttops.service.FileOpsService;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

/**
 *
 * Web resource specially contain the operations related to files[upload, download, delete, etc] and file system.
 *
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileUploadResource {

    @Autowired
    public FileOpsService fileOpsService;

    /**
     *This API is responsible for uploading audio file into Minio and also returning response from Speech-To-Text service
     * based on the audio file being uploaded.
     * This API is also responsible for then storing the analyzed result of audio  file into DB as per user( from
     * security context) who uploaded it.
     * @param file
     * @return
     */
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(@RequestPart(value = "file", required = false) MultipartFile file)  {
        try {
            //TODO: The user below is hardcoded rather it should have been fetched from security context.
            // We are using this user to create unique object in Minio e.g. <bucket>/<object> :: <object> = <username>/<filename>
            fileOpsService.upload("amitrighthere", file.getOriginalFilename(), file.getBytes());
        }catch (Exception ex){
            //TODO:Error handler while returning with error codes
            ex.printStackTrace();
            //TODO:Right now returning only one error code, but instead should have maintained the correct error codes
            //as per the specific exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ErrorCode: 101, Operation Failed");
        }
        //TODO:Should have used one generic standard response DTO class instead of returing map
        return new ResponseEntity(ok(ImmutableMap.of("status", "success")),HttpStatus.CREATED);
    }
}