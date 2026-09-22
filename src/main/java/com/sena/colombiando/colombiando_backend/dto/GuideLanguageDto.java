package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguagesLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface GuideLanguageDto {

        @Schema(name = "GuideLanguageCreate")
        public record Create (
                @NotNull(message = "El ID del guía es obligatorio.")
                UUID guideId,

                @NotNull(message = "El ID del idioma es obligatiorio.")
                UUID languageId,

                @NotNull(message = "El nivel es obligatorio.")
                GuideLanguagesLevelEnum level
        ) {}

        @Schema(name = "GuideLanguageUpdate")
        public record Update(
                GuideLanguagesLevelEnum level
        ) {}

        @Schema(name = "GuideLanguageResponse")
        public record Response(
                GuideLanguageId id,
                GuideDto.GuidePublic guide,
                LanguageDto.Response language,
                GuideLanguagesLevelEnum level
        ){}

}
