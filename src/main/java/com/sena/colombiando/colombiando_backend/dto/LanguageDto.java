package com.sena.colombiando.colombiando_backend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface LanguageDto {

    public record Create(
            @NotNull(message = "Código del idioma obligatorio")
            String code,

            @NotNull(message = "Nombre del idioma obligatorio")
            String name,

            @NotNull(message = "Nombre nativo del idioma obligatorio")
            String nativeName
    ) {}

    public record Response(
            UUID id,
            Create data
    ) {}
}
