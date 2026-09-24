package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserDto {

    @Schema(name = "UserBase")
    record Base(
            String name,
            String lastName,
            @Email()
            String email,
            String phone
    ) {}

    @Schema(name = "UserPublic")
    record UserPublic(
            UUID id,
            String name,
            String lastName,
            String email
    ) {}

    @Schema(name = "UserCreate")
    record Create(
            Base data,
            String password
    ) {}

    @Schema(name = "UserUpdate")
    record Update(
            Base data,
            UserStatusEnum status
    ) {}

    @Schema(name = "UserChangePassword")
    record ChangePassword(
            String currentPassword,
            String newPassword
    ) {}

    @Schema(name = "UserResponse")
    record Response(
            UUID id,
            Base data,
            UserStatusEnum status,
            LocalDateTime createdAt
    ) {}
}
