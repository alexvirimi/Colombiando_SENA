package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingDto {

        public record Create(
                @NotNull(message = "El ID del usuario es obligatorio.")
                UUID userId,

                @NotNull(message = "El ID de la instancia es obligatorio.")
                UUID scheduleInstanceId,

                @NotNull(message = "El número de personas es obligatorio.")
                @Min(value = 1, message = "Mínimo 1 persona.")
                int numPeople
        ) {}

        public record Update(
                int numPeople,

                BookingStatusEnum status
        ) {}

        public record BookingPublic(
                UUID id,
                BookingStatusEnum status,
                LocalDateTime createdAt
        ) {}

        public record Response(
                UUID id,
                int numPeople,
                UserDto.UserPublic user,
                BigDecimal totalPrice,
                ScheduleInstanceDto.Response scheduleInstance,
                BookingStatusEnum status,
                LocalDateTime createdAt
        ) {}
}