package ru.practicum.request.service;

import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.ParticipationRequest;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {

    private RequestMapper() {
    }

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated())
                .event(participationRequest.getEvent().getId())
                .requester(participationRequest.getRequester().getId())
                .status(participationRequest.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> listToParticipationRequestDto(List<ParticipationRequest> participationRequests) {
        return participationRequests.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

}
