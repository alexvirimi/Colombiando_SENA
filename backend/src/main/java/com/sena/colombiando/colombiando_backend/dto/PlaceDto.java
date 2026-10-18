package com.sena.colombiando.colombiando_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PlaceDto {

    @Schema(name = "PlaceBase")
    record Base(
            @NotBlank(message = "El nombre del lugar es obligatorio.")
            String name,

            String description,

            String headerImg
    ) {}

    @Schema(name = "PlaceCreate")
    record Create(
            @Valid
            Base data,

            @NotNull(message = "El ID de la dirección es obligatorio.")
            UUID addressID
    ) {}

    @Schema(name = "PlaceUpdate")
    record Update(
            Base data
    ) {}

    @Schema(name = "PlacePublic")
    record PlacePublic(
            UUID id,
            Base data,
            AddressDto.Response address
    ) {}

    @Schema(name = "PlaceResponse")
    record Response(
            UUID id,
            Base data,
            AddressDto.Response address,
            LocalDateTime createdAt
    ) {}
}
