package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public interface ScheduleInstanceDto {

    public record Base(
            @NotNull()
            LocalDate date,

            @Min(0)
            Integer maxCapacity
    ){}

    public record Create(
            UUID scheduleId,
            Base data
    ) {}

    public record Update(
            Integer maxCapacity,
            ScheduleInstanceStateEnum state
    ) {}

    public record Response(
            UUID id,
            ScheduleDto.Response schedule,
            Base data,
            int availableCapacity,
            ScheduleInstanceStateEnum state
    ) {}
}
