package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.category.model.Category;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.Location;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventDto {

    private LocalDateTime createdOn;

    private String description;

    private String eventDate;

    private Location location;

    private Long participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventState state;

    private String annotation;

    private Category category;

    private Long confirmedRequests;

    private Long id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;

    private List<CommentDto> comments;

}
