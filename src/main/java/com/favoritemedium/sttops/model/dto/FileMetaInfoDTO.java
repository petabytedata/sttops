package com.favoritemedium.sttops.model.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
