package ru.jokardo.music_service.exception.advice;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String uri,
        HttpStatus status,
        String message,
        LocalDateTime timestamp
) {
}
