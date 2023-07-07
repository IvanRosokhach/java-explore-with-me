package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private long id;

    private String text;

    private long eventId;

    private long userId;

    private LocalDateTime created;

}
