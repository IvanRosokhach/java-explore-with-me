package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.request.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequestDto {

    private long id;

    private LocalDateTime created;

    private long event;

    private long requester;

    private RequestStatus status;

}
