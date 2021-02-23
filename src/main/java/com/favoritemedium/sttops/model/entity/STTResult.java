package com.favoritemedium.sttops.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Speech-To-Text result entity for persisting in DB
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@ToString
@Entity(name = "stt_results")
public class STTResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileMetaInfo fileMetaInfo;
    private String fileName;
    private String sttResult;

}
