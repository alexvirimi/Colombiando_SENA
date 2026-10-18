package com.sena.colombiando.colombiando_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public interface LanguageDto {

    @Schema(name = "LanguageCreate")
    record Create(
            @NotBlank(message = "Código del idioma obligatorio")
            String code,

            @NotBlank(message = "Nombre del idioma obligatorio")
            String name,

            @NotBlank(message = "Nombre nativo del idioma obligatorio")
            String nativeName
    ) {}

    @Schema(name = "LanguageResponse")
    record Response(
            UUID id,
            Create data
    ) {}
}
