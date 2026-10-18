package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

/**
 * DTOs específicos del módulo de autenticación.
 * Se mantienen separados de UserDto porque el "contrato" de login/registro
 * no tiene que coincidir con el contrato de gestión de usuarios (por ejemplo,
 * aquí no se expone el password nunca, ni en el request de login se valida
 * formato de nombre/apellido).
 */
public interface AuthDto {

    @Schema(name = "LoginRequest")
    public record LoginRequest(
            @NotBlank(message = "El correo es obligatorio.")
            @Email(message = "El correo no tiene un formato válido.")
            String email,

            @NotBlank(message = "La contraseña es obligatoria.")
            String password
    ) {}

    @Schema(name = "RegisterRequest")
    public record RegisterRequest(
            @NotBlank(message = "El nombre es obligatorio.")
            String name,

            @NotBlank(message = "El apellido es obligatorio.")
            String lastName,

            @NotBlank(message = "El correo es obligatorio.")
            @Email(message = "El correo no tiene un formato válido.")
            String email,

            String phone,

            @NotBlank(message = "La contraseña es obligatoria.")
            String password
    ) {}

    @Schema(name = "AuthResponse")
    public record AuthResponse(
            UUID id,
            String name,
            String lastName,
            String email,
            UserStatusEnum status,
            String message
    ) {}
}
