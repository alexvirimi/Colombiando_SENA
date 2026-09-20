package com.sena.colombiando.colombiando_backend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PlaceDto {

    public record Base(
            @NotNull(message = "El nombre del lugar es obligatorio.")
            String name,

            String description,

            String headerImg
    ) {}

    public record Create(
            Base data,

            @NotNull(message = "El ID de la dirección es obligatorio.")
            UUID addressID
    ) {}

    public record Update(
            Base data
    ) {}

    public record PlacePublic(
            UUID id,
            Base data,
            AddressDto.Response address
    ) {}

    public record Response(
            UUID id,
            Base data,
            AddressDto.Response address,
            LocalDateTime createdAt
    ) {}
}
