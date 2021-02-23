package com.favoritemedium.sttops.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * File metadata infomatin entity for persisting in DB
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@ToString
@Entity(name = "file_meta_infos")
public class FileMetaInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String fromFilePath;

    @OneToMany(mappedBy = "fileMetaInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<STTResult> sttResults = new HashSet<>(0);
}
