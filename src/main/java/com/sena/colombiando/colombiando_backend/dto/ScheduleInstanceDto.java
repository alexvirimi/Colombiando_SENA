package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;

import java.time.LocalDate;
import java.util.UUID;

public interface ScheduleInstanceDto {

    public record Base(
            LocalDate date,

            Integer maxCapacity
    ){}

    public record Create(
            UUID scheduleId,
            Base data
    ) {}

    public record Update(
            Integer availableCapacity,

            ScheduleInstanceStateEnum state
    ) {}

    public record Response(
            UUID id,
            ScheduleDto.Response schedule,
            Base data,
            Integer availableCapacity,
            ScheduleInstanceStateEnum state
    ) {}
}
