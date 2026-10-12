package ru.jokardo.music_service.dto;

import lombok.Builder;
import ru.jokardo.music_service.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserProfile(
        UUID id,
        String username,
        String email,
        RoleEnum role,
        Boolean blocked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}