package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIncorrectParameterException(final ValidationException e) {
        return ApiError.builder()
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .message(e.getMessage())
                .time(LocalDateTime.now())
                .build();
    }

}
