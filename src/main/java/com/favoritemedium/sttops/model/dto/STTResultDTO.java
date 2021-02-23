package com.favoritemedium.sttops.model.dto;

import com.favoritemedium.sttops.model.entity.FileMetaInfo;
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
public class STTResultDTO implements Serializable {

    private int id;
    private FileMetaInfo fileMetaInfo;
    private String fileName;
    private String sttResult;

}
