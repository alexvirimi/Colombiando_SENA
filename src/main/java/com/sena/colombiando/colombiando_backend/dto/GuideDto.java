package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GuideDto {

        @Schema(name = "GuideBase")
        record Base(
                @NotBlank(message = "El nombre del guía es obligatorio.")
                String name,

                @NotBlank(message = "El apellido del guía es obligatorio.")
                String lastName,

                String bio,

                String phone,

                String profileImage
        ) {}

        @Schema(name = "GuideCreate")
        record Create(
                @Valid
                Base data
        ) {}

        @Schema(name = "GuideUpdate")
        record Update(
                Base data,
                GuideStatusEnum status
        ) {}

        @Schema(name = "GuidePublic")
        record GuidePublic(
                UUID id,
                String name,
                String lastName,
                String bio,
                String profileImage,
                GuideStatusEnum status
        ) {}

        @Schema(name = "GuideResponse")
        record Response(
                UUID id,
                Base data,
                GuideStatusEnum status,
                LocalDateTime createdAt
        ) {}
}
