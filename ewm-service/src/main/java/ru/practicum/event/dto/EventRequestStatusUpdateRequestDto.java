package ru.practicum.event.dto;

import lombok.Data;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequestDto {

    private List<Long> requestIds;

    private RequestStatus status;

}
