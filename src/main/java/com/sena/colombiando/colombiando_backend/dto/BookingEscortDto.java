package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.UserDocumentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public interface BookingEscortDto {

    @Schema(name = "BookingEscortBase")
    public record Base(
            @NotNull(message = "Los nombres del acompañante es obligatorio.")
            String name,

            @NotNull(message = "Los apellidos del acompañante es obligatorio.")
            String lastName,

            @NotNull(message = "El tipo de documento es obligatorio.")
            UserDocumentTypeEnum documentType,

            @NotNull(message = "El número de documento es obligatorio.")
            String idNumber,

            @NotNull(message = "La fecha de nacimiento es obligatoria.")
            LocalDate birthDate
    ){}

    @Schema(name = "BookingEscortCreate")
    public record Create(
            @NotNull(message = "El ID de la reserva es obligatorio.")
            UUID bookingId,

            Base data
    ){}

    @Schema(name = "BookingEscortUpdate")
    public record Update(
            Base data
    ){}

    @Schema(name = "BookingEscortResponse")
    public record Response(
            UUID id,
            Base data,
            BookingDto.BookingPublic booking
    ) {}

}
