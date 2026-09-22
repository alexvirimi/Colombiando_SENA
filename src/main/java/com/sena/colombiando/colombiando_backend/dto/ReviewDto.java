package com.sena.colombiando.colombiando_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReviewDto {

    public record Base(
            @Size(min = 0, max = 5, message = "La calificación debe estar entre 0 y 5.")
            Integer rating,

            String review
    ) {}

    public record Create(
            @NotNull(message = "El ID del usuario es obligatorio.")
            UUID userId,

            @NotNull(message = "El ID de la reserva es obligatorio.")
            UUID bookingId,

            @NotNull()
            @Size(min = 0, max = 5, message = "La calificación debe estar entre 0 y 5.")
            Integer rating,

            String review
    ) {}

    public record Update(
            Base data
    ) {}

    public record Response(
            UUID id,
            UserDto.UserPublic user,
            BookingDto.BookingPublic booking,
            Base data,
            LocalDateTime createdAt
    ) {}
}
