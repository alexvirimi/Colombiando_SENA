package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.GuideLanguageEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuideLanguageRepository extends JpaRepository<GuideLanguageEntity, GuideLanguageId> {
    @Query("""
    SELECT gl FROM GuideLanguageEntity gl
    WHERE (:guideId IS NULL OR gl.guide.id = :guideId)
      AND (:languageId IS NULL OR gl.language.id = :languageId)
    """)
        List<GuideLanguageEntity> search(
                @Param("guideId") UUID guideId,
                @Param("languageId") UUID languageId
        );

    GuideLanguageEntity findByGuideIdAndLanguageId(UUID guideId, UUID languageId);

    @Transactional
    void deleteByGuideIdAndLanguageId(UUID guideId, UUID languageId);
}
