package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.UserDocumentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public interface BookingEscortDto {

    @Schema(name = "BookingEscortBase")
    record Base(
            @NotBlank(message = "Los nombres del acompañante es obligatorio.")
            String name,

            @NotBlank(message = "Los apellidos del acompañante es obligatorio.")
            String lastName,

            @NotNull(message = "El tipo de documento es obligatorio.")
            UserDocumentTypeEnum documentType,

            @NotBlank(message = "El número de documento es obligatorio.")
            String idNumber
    ){}

    @Schema(name = "BookingEscortCreate")
    record Create(
            @NotNull(message = "El ID de la reserva es obligatorio.")
            UUID bookingId,

            @NotNull(message = "La fecha de nacimiento es obligatoria.")
            @Past(message = "La fecha de nacimiento debe ser una fecha pasada.")
            LocalDate birthDate,

            Base data
    ){}

    @Schema(name = "BookingEscortUpdate")
    record Update(
            Base data,

            @Past(message = "La fecha de nacimiento debe ser una fecha pasada.")
            LocalDate birthDate
    ){}

    @Schema(name = "BookingEscortResponse")
    record Response(
            UUID id,
            Base data,
            LocalDate birthDate,
            BookingDto.BookingPublic booking
    ) {}

}
