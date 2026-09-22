package com.sena.colombiando.colombiando_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PlaceDto {

    @Schema(name = "PaymentBase")
    public record Base(
            @NotNull(message = "El nombre del lugar es obligatorio.")
            String name,

            String description,

            String headerImg
    ) {}

    @Schema(name = "PlaceCreate")
    public record Create(
            Base data,

            @NotNull(message = "El ID de la dirección es obligatorio.")
            UUID addressID
    ) {}

    @Schema(name = "PlaceUpdate")
    public record Update(
            Base data
    ) {}

    @Schema(name = "PaymentPublic")
    public record PlacePublic(
            UUID id,
            Base data,
            AddressDto.Response address
    ) {}

    @Schema(name = "PaymentResponse")
    public record Response(
            UUID id,
            Base data,
            AddressDto.Response address,
            LocalDateTime createdAt
    ) {}
}
