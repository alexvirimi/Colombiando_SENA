package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GuideDto {

        @Schema(name = "GuideBase")
        public record Base(
                @NotNull(message = "El nombre del guía es obligatorio.")
                String name,

                @NotNull(message = "El apellido del guía es obligatorio.")
                String lastName,

                String bio,

                String phone,

                String profileImage
        ) {}

        @Schema(name = "GuideCreate")
        public record Create(
                Base data
        ) {}

        @Schema(name = "GuideUpdate")
        public record Update(
                Base data,
                GuideStatusEnum status
        ) {}

        @Schema(name = "GuidePublic")
        public record GuidePublic(
                UUID id,
                String name,
                String lastName,
                String bio,
                String profileImage,
                GuideStatusEnum status
        ) {}

        @Schema(name = "GuideResponse")
        public record Response(
                UUID id,
                Base data,
                GuideStatusEnum status,
                LocalDateTime createdAt
        ) {}
}
