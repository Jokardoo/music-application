package ru.jokardo.music_service.exception.advice;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class AdviceControllerGrpc {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(io.grpc.StatusRuntimeException.class)
    public ErrorResponse handleNotFound(StatusRuntimeException e) {

        HttpStatus status = parseStatus(e);

            return ErrorResponse
                    .builder()
                    .message(e.getMessage())
                    .status(status)
                    .timestamp(LocalDateTime.now(ZoneId.systemDefault()))
                    .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException e) {
        return ErrorResponse
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now(ZoneId.systemDefault()))
                .build();
    }

    private HttpStatus parseStatus(StatusRuntimeException e) {
        if (e.getStatus().getCode() == Status.Code.NOT_FOUND)
            return HttpStatus.NOT_FOUND;
        else if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT)
            return HttpStatus.BAD_REQUEST;
        else
            return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
