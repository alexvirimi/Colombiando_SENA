package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;

import java.time.LocalDate;
import java.util.UUID;

public interface ScheduleInstanceDto {

    public record Base(
            LocalDate date,

            int maxCapacity
    ){}

    public record Create(
            UUID scheduleId,
            Base data
    ) {}

    public record Update(
            int maxCapacity,
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
