package com.favoritemedium.sttops.model.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Data transfer object to send on the wire as response
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@ToString
public class FileMetaInfoDTO implements Serializable {

    private int id;
    private String fileName;
    private UserDTO user;
    private String fromFilePath;
}
