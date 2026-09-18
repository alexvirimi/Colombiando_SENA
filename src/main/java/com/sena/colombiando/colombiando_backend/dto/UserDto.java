package com.sena.colombiando.colombiando_backend.dto;

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
            Base data
    ) {}

    public record ChangePassword(
            String oldPassword,
            String newPassword
    ) {}

    public record Response(
            UUID id,
            Base data,
            LocalDateTime createdAt
    ) {}
}
