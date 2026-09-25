package com.sena.colombiando.colombiando_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public interface AddressDto {

        @Schema(name = "AddressBase")
        record Base(
                @NotBlank(message = "La dirección es obligatoria.")
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

        @Schema(name = "AddressCreate")
        record Create(
                @Valid
                Base data
        ) {}

        @Schema(name = "AddressUpdate")
        record Update(
                Base data
        ) {}

        @Schema(name = "AddressResponse")
        record Response(
                UUID id,
                Base data
        ) {}

}
