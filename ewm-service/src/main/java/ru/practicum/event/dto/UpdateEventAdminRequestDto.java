package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.EventStateAction;

import javax.validation.constraints.Size;

@Data
@Builder
public class UpdateEventAdminRequestDto {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private EventStateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;


}
