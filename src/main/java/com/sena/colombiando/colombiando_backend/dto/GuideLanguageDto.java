package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguagesLevelEnum;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface GuideLanguageDto {

        public record Create (
                @NotNull(message = "El ID del guía es obligatorio.")
                UUID guideId,

                @NotNull(message = "El ID del idioma es obligatiorio.")
                UUID languageId,

                @NotNull(message = "El nivel es obligatorio.")
                GuideLanguagesLevelEnum level
        ) {}

        public record Update(
                GuideLanguagesLevelEnum level
        ) {}

        public record Response(
                GuideLanguageId id,
                GuideDto.GuidePublic guide,
                LanguageDto.Response language,
                GuideLanguagesLevelEnum level
        ){}

}
