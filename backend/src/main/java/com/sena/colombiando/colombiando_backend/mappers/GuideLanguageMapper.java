package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.GuideLanguageDto;
import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageEntity;
import com.sena.colombiando.colombiando_backend.entities.LanguageEntity;
import com.sena.colombiando.colombiando_backend.repositories.GuideRepository;
import com.sena.colombiando.colombiando_backend.repositories.LanguageRepository;
import org.springframework.stereotype.Component;

@Component
public class GuideLanguageMapper {

    private final LanguageMapper languageMapper;
    private final GuideMapper guideMapper;

    private final LanguageRepository languageRepository;
    private final GuideRepository guideRepository;

/** Inicializa la instancia.
 * @param languageMapper parametro de entrada.
 * @param guideMapper parametro de entrada.
 * @param languageRepository parametro de entrada.
 * @param guideRepository parametro de entrada.
 */
    public GuideLanguageMapper(
            LanguageMapper languageMapper,
            GuideMapper guideMapper,
            LanguageRepository languageRepository,
            GuideRepository guideRepository) {
        this.languageMapper = languageMapper;
        this.guideMapper = guideMapper;
        this.languageRepository = languageRepository;
        this.guideRepository = guideRepository;
    }

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public GuideLanguageEntity toEntity(GuideLanguageDto.Create request) {
        var entity  = new GuideLanguageEntity();

        LanguageEntity language = languageRepository.getReferenceById(request.languageId());
        GuideEntity guide = guideRepository.getReferenceById(request.guideId());

        entity.setLanguage(language);
        entity.setGuide(guide);
        entity.setLevel(request.level());

        return entity;
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public GuideLanguageDto.Response toDto(GuideLanguageEntity entity) {
        if (entity == null) {
            return null;
        }

        return new GuideLanguageDto.Response(
                entity.getId(),
                guideMapper.toPublicDto(entity.getGuide()),
                languageMapper.toDto(entity.getLanguage()),
                entity.getLevel()
        );
    }

}
