package com.favoritemedium.sttops.model.dto;

import com.favoritemedium.sttops.model.entity.FileMetaInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@ToString
public class STTResultDTO implements Serializable {

    private int id;
    private FileMetaInfo fileMetaInfo;
    private String fileName;
    private String sttResult;

}
