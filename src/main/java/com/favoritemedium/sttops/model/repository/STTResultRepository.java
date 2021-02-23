package com.favoritemedium.sttops.model.repository;

import com.favoritemedium.sttops.model.entity.STTResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DB interface containing operations related to Speech-To-Text results in stt_results table
 */
public interface STTResultRepository extends JpaRepository<STTResult, Integer> {
}
