package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GuideDto {

        public record Base(
                @NotNull(message = "El nombre del guía es obligatorio.")
                String name,

                @NotNull(message = "El apellido del guía es obligatorio.")
                String lastName,

                String bio,

                String phone,

                String profileImage
        ) {}

        public record Create(
                Base data
        ) {}

        public record Update(
                Base data,
                GuideStatusEnum status
        ) {}

        public record GuidePublic(
                UUID id,
                String name,
                String lastName,
                String bio,
                String profileImage,
                GuideStatusEnum status
        ) {}

        public record Response(
                UUID id,
                Base data,
                GuideStatusEnum status,
                LocalDateTime createdAt
        ) {}
}
