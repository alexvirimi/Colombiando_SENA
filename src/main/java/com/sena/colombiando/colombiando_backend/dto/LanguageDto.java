package com.sena.colombiando.colombiando_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface LanguageDto {

    @Schema(name = "LanguageCreate")
    public record Create(
            @NotNull(message = "Código del idioma obligatorio")
            String code,

            @NotNull(message = "Nombre del idioma obligatorio")
            String name,

            @NotNull(message = "Nombre nativo del idioma obligatorio")
            String nativeName
    ) {}

    @Schema(name = "LanguageResponse")
    public record Response(
            UUID id,
            Create data
    ) {}
}
