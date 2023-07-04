package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.request.dao.RequestRepository;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.Constant.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto save(long userId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EVENT, eventId)));
        if (event.getParticipantLimit() != 0
                && event.getParticipantLimit() == requestRepository.countAllByEventId(eventId)) {
            throw new ValidationException("The limit has been reached for the event.");
        }
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ValidationException("You cannot add a repeat request.");
        }
        if (event.getInitiator().getId() == userId) {
            throw new ValidationException("The initiator of the event cannot " +
                    "add a request to participate in his event.");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("You cannot participate in an unpublished event.");
        }
        long countRequestByEvent = requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
        if (event.getParticipantLimit() != 0 && countRequestByEvent > event.getParticipantLimit()) {
            throw new ValidationException("The limit of requests for participation in the event has been reached.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_USER, userId)));
        ParticipationRequest requestByEvent = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .build();
        if (!event.isRequestModeration()) {
            requestByEvent.setStatus(RequestStatus.CONFIRMED);
        } else {
            requestByEvent.setStatus(RequestStatus.PENDING);
        }
        if (event.getParticipantLimit() == 0) {
            requestByEvent.setStatus(RequestStatus.CONFIRMED);
        }
        ParticipationRequest saved = requestRepository.save(requestByEvent);
        return RequestMapper.toParticipationRequestDto(saved);
    }

    @Override
    public List<ParticipationRequestDto> getRequestByUser(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_USER, userId)));
        List<ParticipationRequest> allByRequesterId = requestRepository.findAllByRequesterId(userId);
        return RequestMapper.listToParticipationRequestDto(allByRequesterId);
    }

    @Override
    public ParticipationRequestDto cancelRequestByUser(long userId, long requestId) {
        ParticipationRequest participationRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_REQUEST, requestId)));
        participationRequest.setStatus(RequestStatus.CANCELED);
        ParticipationRequest saved = requestRepository.save(participationRequest);
        return RequestMapper.toParticipationRequestDto(saved);
    }

}
