package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public interface ScheduleInstanceDto {

    @Schema(name = "ScheduleInstanceBase")
    public record Base(
            @NotNull()
            LocalDate date,

            @Min(0)
            Integer maxCapacity
    ){}

    @Schema(name = "ScheduleInstanceCreate")
    public record Create(
            UUID scheduleId,
            Base data
    ) {}

    @Schema(name = "ScheduleInstanceUpdate")
    public record Update(
            Integer maxCapacity,
            ScheduleInstanceStateEnum state
    ) {}

    @Schema(name = "ScheduleInstanceResponse")
    public record Response(
            UUID id,
            ScheduleDto.Response schedule,
            Base data,
            int availableCapacity,
            ScheduleInstanceStateEnum state
    ) {}
}
