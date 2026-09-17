package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.GuideLanguageEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GuideLanguageRepository extends JpaRepository<GuideLanguageEntity, GuideLanguageId> {
    public List<GuideLanguageEntity> findByGuideId(UUID guideId);
    public List<GuideLanguageEntity> findByLanguageId(UUID languageId);
    public List<GuideLanguageEntity> findByGuideIdAndLanguageId(UUID guideId, UUID languageId);

    @Transactional
    void deleteByGuideIdAndLanguageId(UUID guideId, UUID languageId);
}
