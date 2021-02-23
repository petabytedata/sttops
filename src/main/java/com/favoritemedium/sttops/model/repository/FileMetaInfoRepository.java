package com.favoritemedium.sttops.model.repository;

import com.favoritemedium.sttops.model.entity.FileMetaInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DB interface containing operations related uploaded file meta information in file_meta_info table
 */
public interface FileMetaInfoRepository extends JpaRepository<FileMetaInfo, Integer> {
}
