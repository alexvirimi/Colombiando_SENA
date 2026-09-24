package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.ScheduleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface ScheduleDto {

    @Schema(name = "ScheduleBase")
    record Base(
            @NotNull(message = "El tiempo de inicio es obligatorio.")
            LocalTime startTime,

            @NotNull(message = "El tiempo de finalización es obligatorio.")
            LocalTime endTime,

            @NotNull(message = "La fecha de inicio es obligatoria.")
            LocalDate startDate,

            @NotNull(message = "La fecha de finalización es obligatoria.")
            LocalDate endDate
    ) {}

    @Schema(name = "ScheduleCreate")
    record Create(
            @NotNull(message = "El ID del guía es obligatorio.")
            UUID guideId,

            @NotNull(message = "El ID del lugar es obligatorio.")
            UUID placeId,

            @NotNull()
            @Min(value = 1, message = "La capacidad máxima debe ser mayor a 1.")
            Integer maxCapacity,

            @NotNull()
            @Min(value = 1, message = "La capacidad máxima debe ser mayor a 1.")
            BigDecimal pricePerPerson,

            Base data
    ) {}

    @Schema(name = "ScheduleUpdate")
    record Update(
            Base data,

            @Min(value = 1, message = "La capacidad máxima debe ser mayor a 1.")
            Integer maxCapacity,

            @Min(value = 1, message = "La capacidad máxima debe ser mayor a 1.")
            BigDecimal pricePerPerson,

            ScheduleStatusEnum status
    ) {}

    @Schema(name = "ScheduleResponse")
    record Response(
            UUID id,
            GuideDto.GuidePublic guide,
            PlaceDto.PlacePublic place,
            Base data,
            Integer maxCapacity,
            BigDecimal pricePerPerson,
            ScheduleStatusEnum status
    ) {}

}
