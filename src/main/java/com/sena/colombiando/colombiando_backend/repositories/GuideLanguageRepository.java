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
/** Consulta search.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
        List<GuideLanguageEntity> search(
                @Param("guideId") UUID guideId,
                @Param("languageId") UUID languageId
        );

/** Consulta find by guide id and language id.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
    GuideLanguageEntity findByGuideIdAndLanguageId(UUID guideId, UUID languageId);

/** Elimina by guide id and language id.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 */
    @Transactional
    void deleteByGuideIdAndLanguageId(UUID guideId, UUID languageId);
}
