package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.PaymentMethodEnum;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface PaymentDto {

    @Schema(name = "PaymentCreate")
    public record Create(
            @NotNull(message = "El ID de la reserva es obligatorio.")
            UUID bookingId,

            @NotNull(message = "El monto a pagar es obligatorio.")
            BigDecimal amountToPay,

            @NotNull(message = "El método de pago es obligatorio.")
            PaymentMethodEnum paymentMethod,

            @Size(min = 3, max = 3, message = "El código de moneda debe tener exactamente 3 caracteres.")
            String currency
    ) {
        public Create {
            if (currency == null || currency.isBlank()) {
                currency = "COP";
            }
        }
    }

    @Schema(name = "PaymentUpdate")
    public record Update(
            BigDecimal amountToPay,
            String currency,
            PaymentMethodEnum paymentMethod,
            PaymentStatusEnum status
    ) {}

    @Schema(name = "PaymentResponse")
    public record Response(
            UUID id,
            BookingDto.BookingPublic booking,
            BigDecimal amountToPay,
            PaymentMethodEnum paymentMethod,
            String currency,
            PaymentStatusEnum status,
            LocalDateTime createdAt
    ) {}
}
