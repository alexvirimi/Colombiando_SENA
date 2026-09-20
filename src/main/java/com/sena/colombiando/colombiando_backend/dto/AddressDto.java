package com.sena.colombiando.colombiando_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public interface AddressDto {

        public record Base(
                @NotNull(message = "La dirección es obligatoria.")
                String addressText,

                String countryCode,

                String municipality,

                @NotNull(message = "La coordenada de latitud es obligatoria.")
                BigDecimal latitude,

                @NotNull(message = "La coordenada de longitud es obligatoria.")
                BigDecimal longitude
        ) {
                public Base {
                        if (countryCode == null || countryCode.isEmpty()) {
                                countryCode = "CO";
                        }
                }
        }

        public record Create(
                Base data
        ) {}

        public record Update(
                Base data
        ) {}

        public record Response(
                UUID id,
                Base data
        ) {}

}
