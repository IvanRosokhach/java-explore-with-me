package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.category.model.Category;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
public class EventShortDto {

    private LocalDateTime eventDate;

    private String annotation;

    private Category category;

    private Long confirmedRequests;

    private Long id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;

}
