package com.sena.colombiando.colombiando_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReviewDto {

    @Schema(name = "ReviewBase")
    record Base(
            @Size(min = 0, max = 5, message = "La calificación debe estar entre 0 y 5.")
            Integer rating,

            String review
    ) {}

    @Schema(name = "ReviewCreate")
    record Create(
            @NotNull(message = "El ID del usuario es obligatorio.")
            UUID userId,

            @NotNull(message = "El ID de la reserva es obligatorio.")
            UUID bookingId,

            @NotNull(message = "La calificación es obligatoria.")
            @Range(min = 1, max = 5, message = "La calificación debe estar entre 0 y 5.")
            Integer rating,

            String review
    ) {}

    @Schema(name = "ReviewUpdate")
    record Update(
            Base data
    ) {}

    @Schema(name = "ReviewResponse")
    record Response(
            UUID id,
            UserDto.UserPublic user,
            BookingDto.BookingPublic booking,
            Base data,
            LocalDateTime createdAt
    ) {}
}
