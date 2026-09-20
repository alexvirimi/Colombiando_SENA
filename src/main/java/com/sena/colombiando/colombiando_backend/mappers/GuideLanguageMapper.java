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

    public GuideLanguageEntity toEntity(GuideLanguageDto.Create request) {
        var entity  = new GuideLanguageEntity();

        LanguageEntity language = languageRepository.getReferenceById(request.languageId());
        GuideEntity guide = guideRepository.getReferenceById(request.guideId());

        entity.setLanguage(language);
        entity.setGuide(guide);
        entity.setLevel(request.level());

        return entity;
    }

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
