package com.sena.colombiando.colombiando_backend.dto;

import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserDto {

    public record Base(
            String name,
            String lastName,
            String email,
            String phone
    ) {}

    public record UserPublic(
            UUID id,
            String name,
            String lastName,
            String email
    ) {}

    public record Create(
            Base data,
            String password
    ) {}

    public record Update(
            Base data,
            UserStatusEnum status
    ) {}

    public record ChangePassword(
            String currentPassword,
            String newPassword
    ) {}

    public record Response(
            UUID id,
            Base data,
            UserStatusEnum status,
            LocalDateTime createdAt
    ) {}
}
