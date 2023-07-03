package ru.practicum.event.service;


import ru.practicum.event.dto.*;
import ru.practicum.event.model.EventSort;
import ru.practicum.event.model.EventState;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventDto> searchEvent(List<Long> users, List<EventState> states, List<Long> categories,
                               String rangeStart, String rangeEnd, int from, int size);

    EventDto updateAdminByEvent(long eventId, UpdateEventAdminRequestDto eventDto);

    EventDto saveEvent(long userId, NewEventDto newEventDto);

    List<EventShortDto> getAllEventByInitiatorId(long userId, int from, int size);

    EventDto getEventByIdForUser(long userId, long eventId);

    EventDto updateEvent(long userId, long eventId, UpdateEventUserRequestDto updateEvent);

    List<ParticipationRequestDto> getRequestByEvent(long userId, long eventId);

    EventRequestStatusUpdateResultDto updateEventRequest(long userId, long eventId,
                                                         EventRequestStatusUpdateRequestDto requestsByEvent);

    List<EventDto> getEvent(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd,
                            Boolean onlyAvailable, EventSort sort, int from, int size, HttpServletRequest request);

    EventDto getEventById(long eventId, HttpServletRequest request);

}
