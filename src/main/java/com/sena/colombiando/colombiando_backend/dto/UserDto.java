package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserDto {

    @Schema(name = "UserBase")
    public record Base(
            String name,
            String lastName,
            String email,
            String phone
    ) {}

    @Schema(name = "UserPublic")
    public record UserPublic(
            UUID id,
            String name,
            String lastName,
            String email
    ) {}

    @Schema(name = "UserCreate")
    public record Create(
            Base data,
            String password
    ) {}

    @Schema(name = "UserUpdate")
    public record Update(
            Base data,
            UserStatusEnum status
    ) {}

    @Schema(name = "UserChangePassword")
    public record ChangePassword(
            String currentPassword,
            String newPassword
    ) {}

    @Schema(name = "UserResponse")
    public record Response(
            UUID id,
            Base data,
            UserStatusEnum status,
            LocalDateTime createdAt
    ) {}
}
