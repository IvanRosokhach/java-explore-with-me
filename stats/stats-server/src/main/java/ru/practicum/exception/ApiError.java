package ru.practicum.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {

    private String status;
    private String message;
    private String reason;
    private LocalDateTime time;

}
