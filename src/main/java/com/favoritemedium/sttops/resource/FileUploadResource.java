package com.favoritemedium.sttops.resource;

import com.favoritemedium.sttops.service.FileOpsService;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileUploadResource {

    @Autowired
    public FileOpsService fileOpsService;

    /**
     *
     * @param file
     * @return
     */
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}) //"video/mp4","audio/mp4",MediaType.MULTIPART_FORM_DATA_VALUE
    public ResponseEntity<?> upload(@RequestPart(value = "file", required = false) MultipartFile file)  {
        try {
            fileOpsService.upload("amitrighthere", file.getOriginalFilename(), file.getBytes());
        }catch (Exception ex){
            //TODO:Error handler while returning with error codes
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ErrorCode: 101, Operation Failed");
        }
        return new ResponseEntity(ok(ImmutableMap.of("status", "success")),HttpStatus.CREATED);
    }
}